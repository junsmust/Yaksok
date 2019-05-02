package yaksok.dodream.com.yaksok_refactoring.model.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.user.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.presenter.signup_presenter.PresenterSignUp;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FcmTokenVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class SignUpModel implements IPresenterToSignUpModel {
    private PresenterSignUp presenterSignUp;
    private boolean isValidateID,isValidatePW,isValidateEmial;
    private String email,nowDate;
    int age;
    private String birthday;
    private User_Info_Model user_info_model = new User_Info_Model();
    private Retrofit retrofit;
    private UserService userService;


    public SignUpModel() {
    }

    public SignUpModel(PresenterSignUp presenterSignUp) {
        this.presenterSignUp = presenterSignUp;
    }

    @Override
    public void validateId(String id) {
        if(id.length()<=6){
            presenterSignUp.nonValidatedIl();
            presenterSignUp.makeToastMessage("6글자 이상으로 아이디를 입력해주세요.");

        }
        else{
            isValidateID = true;
            presenterSignUp.makeToastMessage("아이디가 적합합니다");
            user_info_model.setId(id);
        }
    }

    @Override
    public void validatePw(String pw1,String pw2) {

        if(pw1.length()<=6){
            presenterSignUp.nonValidatedPW();
            presenterSignUp.makeToastMessage("6글자 이상으로 비밀번호를 입력해주세요.");
        }
        else if(!hasSpecialCharacter(pw1)){
            presenterSignUp.nonValidatedPW();
            presenterSignUp.makeToastMessage("!,#,* 와 같은 특수 문자를 추가해주세요");

        }
        else if (!pw1.equals(pw2)){
            presenterSignUp.nonValidatedPW();
            presenterSignUp.makeToastMessage("비밀번호가 일치하지 않습니다.");
        }
        else{
            presenterSignUp.makeToastMessage("비밀번호가 적절합니다.");
            isValidatePW = true;
            user_info_model.setPw(pw1);
        }
    }

    @Override
    public void validateEmail(String email1,String email2) {

        email = email1 + "@" + email2;
        if(checkEmail(email)){
           presenterSignUp.makeToastMessage("사용할 수 있는 이메일 입니다.");
            isValidateEmial = true;
            user_info_model.setEmail(email);
        }
        else{
            if(email1.equals("")){
               presenterSignUp.makeToastMessage("메일을 입력하세요.");
               presenterSignUp.nonValidatedEmail();

            }
            else if(email2.equals("")) {
               presenterSignUp.nonValidatedEmail();
               presenterSignUp.makeToastMessage("주소를 입력하세요.");
            }
            else {
                presenterSignUp.nonValidatedEmail();
                presenterSignUp.makeToastMessage("메일 형식에 맞지 않습니다.");

            }
        }
    }



    @Override
    public void completeSignUp() {
        presenterSignUp.isValidatedUser(isValidateID,isValidatePW,isValidateEmial);

    }

    @Override
    public void guessAge(String currentYear, String bornYear) {
        int cy = Integer.parseInt(currentYear.substring(0,4));
        int by = Integer.parseInt(bornYear.substring(0,4));


        checkAge(cy-by+1);
        //user_info_model.setAgeRange("1-9");

    }

    @Override
    public void setBirth(String month, String day) {
        birthday = String.valueOf(month)+"-"+String.valueOf(day);
        user_info_model.setBirthday(birthday);
    }

    @Override
    public void onSignupResponse(boolean SignUpResponse) {

    }

    @Override
    public void onError(String errorMsg) {

    }



    @Override
    public void onSignUp() {


        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

       user_info_model = LoginModel.user_info_model;



        Call<BodyVO> call = userService.postSignUp(user_info_model);

        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                Log.d("server","server");
                Log.d("server_before","sssssssss");
                Log.d("sssssss",user_info_model.getId()+"\n"+user_info_model.getUserType()+"\n"+user_info_model.getAgeRange()+"" +
                        "\n"+user_info_model.getBirthday()+user_info_model.getPhoneNumber());

                if(bodyVO.getStatus().equals("201")){
                    presenterSignUp.onSignupResponse(true);
                    presenterSignUp.makeToastMessage("가입 성공 되었습니다.");
                    pushToken();


                }
                else if (bodyVO.getStatus().equals("400")) {
                    presenterSignUp.makeToastMessage( "잘못된 요청");
                }
                else if (bodyVO.getStatus().equals("403")) {
                    presenterSignUp.makeToastMessage( "아이디 중복");
                }else if (bodyVO.getStatus().equals("409")) {
                    presenterSignUp.makeToastMessage("입력된 핸드폰 계정 아이디 존재");

                } else if (bodyVO.getStatus().equals("500")) {
                    presenterSignUp.makeToastMessage("서버 오류");
                }


            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                presenterSignUp.makeToastMessage("이상있음");
                Log.d("ttttttttt",t.getMessage());
            }
        });

    }

    @Override
    public void setName(String name) {
        user_info_model.setNickname(name);
    }

    @Override
    public void isvalidatePhone(String pn) {
        user_info_model.setPhoneNumber(pn);
        presenterSignUp.makeToastMessage("인증되었습니다.");
    }

    @Override
    public void setUserType(String userType) {
        user_info_model.setUserType(userType);
    }

    @Override
    public void setGeneralUserType() {
        user_info_model.setUserType("G");
    }


    @Override
    public void setPn(String pn) {
        LoginModel.user_info_model.setPhoneNumber(pn);
        onSignUp();
    }



    public static boolean hasSpecialCharacter(String string){
        if(TextUtils.isEmpty(string)){
            return false;
        }
        else{
            for(int i = 0; i < string.length(); i++){
                if(!Character.isLetterOrDigit(string.charAt(i))){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean checkEmail(String email){
        boolean returnValue = false;

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) return true;
        else return false;

    }
 /*  */
    private void checkAge(int age){

        if(0<= age && age <10){

            user_info_model.setAgeRange("1-9");


        }
        else if(10 <= age && age < 20){

            user_info_model.setAgeRange("10-19");
        }
        else if(20 <= age && age < 30){


            user_info_model.setAgeRange("20-29");
        }
        else if(30 <= age && age < 40){
            user_info_model.setAgeRange("30-39");

        }
        else if(40 <= age && age < 49){
            user_info_model.setAgeRange("40-49");

        }
        else if(50 <= age && age < 59){
            user_info_model.setAgeRange("50-59");

        }
        else if(60 <= age && age < 69){
            user_info_model.setAgeRange("60-69");

        }
        else if(70 <= age && age < 79){
            user_info_model.setAgeRange("70-79");

        }
        else if(80 <= age && age < 89){
            user_info_model.setAgeRange("80-89");

        }
        else if(90 <= age && age < 99){
            user_info_model.setAgeRange("90-99");
        }
    }
    public void pushToken(){
        FcmTokenVO fcmTokenVO = new FcmTokenVO();
        fcmTokenVO.setId(user_info_model.getId());
        fcmTokenVO.setFcmToken(FirebaseInstanceId.getInstance().getToken());

        Call<BodyVO> bodyVOCall = userService.putToken(fcmTokenVO);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(@NonNull Call<BodyVO> call, @NonNull Response<BodyVO> response) {
                Log.d("user_token",response.message());
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }
}
