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
import yaksok.dodream.com.yaksok_refactoring.model.chat.User_info;
import yaksok.dodream.com.yaksok_refactoring.model.user.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
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
    public void validateId(final String id) {
        if(id.length()<6){
            presenterSignUp.nonValidatedIl();
            presenterSignUp.makeToastMessage("6글자 이상으로 \n아이디를 입력해주세요.");

        }
        else{

            Log.e("validateId: ",id );
            retrofit = new Retrofit.Builder()
                    .baseUrl(userService.API_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            userService = retrofit.create(UserService.class);

            Call<BodyVO> call = userService.getOverlapId(id,"id","confirmOverlapId");
            call.enqueue(new Callback<BodyVO>() {
                @Override
                public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                    BodyVO bodyVO = response.body();
//200 : OK (존재하지 않음으로 회원가입가능한 아이디)
//400 : 잘못된 요청
//403 : 중복되는 아이디 존재
//500 : Server Error
                    if(bodyVO.getStatus().equals("200")){
                        presenterSignUp.isValidedId(true);
                        presenterSignUp.makeToastMessage("사용 가능한 아이디 입니다.");
                        user_info_model.setId(id);
                        presenterSignUp.isValidedId(true);
                        Log.e("onResponse: ",user_info_model.getId());
                    }else if(bodyVO.getStatus().equals("400")){
                        presenterSignUp.isValidedId(false);
                        presenterSignUp.makeToastMessage("질못된 아이디 입니다.");
                    }else if(bodyVO.getStatus().equals("403")){
                        presenterSignUp.isValidedId(false);
                        presenterSignUp.makeToastMessage("중복된 아이디 입니다.");
                    }else if(bodyVO.getStatus().equals("500")){
                        presenterSignUp.isValidedId(false);
                        presenterSignUp.makeToastMessage("서버 오류 입니다. ");
                    }
                }

                @Override
                public void onFailure(Call<BodyVO> call, Throwable t) {

                }
            });


        }
    }

    @Override
    public void validatePw(String pw1) {
            user_info_model.setPw(pw1);
        Log.e("validatePw: ",user_info_model.getPw());

        }


    @Override
    public void validateEmail(String email1) {

        //presenterSignUp.makeToastMessage("사용할 수 있는 이메일 입니다.");
        user_info_model.setEmail(email1);
        if(!email1.equals("")){
            Log.e("validateEmail: ", "ddddd"+user_info_model.getEmail());

        }

        }




    @Override
    public void completeSignUp() {
       // Log.e( "completeSignUp: ","id  "+isValidateID+"  pw   "+isValidatePW+"  email "+isValidateEmial );
       // presenterSignUp.isValidatedUser(isValidateID,isValidatePW,isValidateEmial);
        Log.d("sssssss1",
                "\n"+"id : "+user_info_model.getId()+
                        "\n"+"type : "+user_info_model.getUserType()+
                        "\n"+"pw : "+user_info_model.getPw()+"" +
                        "\n"+"nickName : "+user_info_model.getNickname()+
                        "\n"+"getPhoneNum : "+user_info_model.getPhoneNumber()+
                        "\n"+"email : "+user_info_model.getEmail()+
                        "\n"+"age : "+user_info_model.getAgeRange() );

        onSignUp(user_info_model);
       /* if(isValidateID&&isValidatePW&&isValidateEmial){

        }else{
            presenterSignUp.makeToastMessage("확인 버튼을 눌러주세요");
        }
*/

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
        Log.e( "validateId: id" ,user_info_model.getBirthday());
    }

    @Override
    public void onSignupResponse(boolean SignUpResponse) {

    }

    @Override
    public void onError(String errorMsg) {

    }



    @Override
    public void onSignUp(final User_Info_Model user_info_model) {


        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<BodyVO> call = userService.postSignUp(user_info_model);

        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                Log.d("server","server");
                Log.d("server_before","sssssssss");
                Log.d("sssssss",
                        "\n"+"id : "+user_info_model.getId()+
                        "\n"+"type : "+user_info_model.getUserType()+
                        "\n"+"pw : "+user_info_model.getPw()+"" +
                        "\n"+"nickName : "+user_info_model.getNickname()+
                        "\n"+"getPhoneNum : "+user_info_model.getPhoneNumber()+
                        "\n"+"email : "+user_info_model.getEmail());


                if(bodyVO.getStatus().equals("201")){
                    User_Id.setUser_Id(user_info_model.getId());
                    presenterSignUp.onSignupResponse(true);
                   // presenterSignUp.makeToastMessage("가입 성공 되었습니다.");


                    User_Id.setUser_Id(user_info_model.getId());
                    User_Id.setE_mail(user_info_model.getNickname());
                    User_Id.setE_mail(user_info_model.getEmail());
                    User_Id.setPhone_No(user_info_model.getPhoneNumber());
                    User_Id.setType(user_info_model.getUserType());
                    //performLoginOperation(user_info_model);
                    pushToken();



                }
                else if (bodyVO.getStatus().equals("400")) {
                    presenterSignUp.makeToastMessage( "잘못된 요청");
                }
                else if (bodyVO.getStatus().equals("403")) {
                    presenterSignUp.makeToastMessage( "아이디 중복");
                    presenterSignUp.isValidedId(false);
                }else if (bodyVO.getStatus().equals("409")) {
                    presenterSignUp.makeToastMessage("입력된 핸드폰 계정 아이디 존재");
                    presenterSignUp.isValidedPn(false);

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
    public void isvalidatePhone(final String pn) {
        Log.d("Sssss",pn);

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<BodyVO> call = userService.GetOvelapPhoneNumber(pn,"phoneNumber","confirmOverlapPhoneNum");
        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO vo = response.body();

                //200 : OK (존재하지 않음으로 회원가입가능한 핸드폰번호)
                //400 : 잘못된 요청
                //403 : 중복되는 핸드폰번호 존재
                //500 : Server Error
                if(vo.getStatus().equals("200")){
                    user_info_model.setPhoneNumber(pn);
                    presenterSignUp.makeToastMessage("사용가능한 전화번호입니다.");
                    presenterSignUp.isValidedPn(true);
                }else if(vo.getStatus().equals("400")){
                    presenterSignUp.makeToastMessage("잘못된 전화번호 입니다.");
                    presenterSignUp.isValidedPn(false);
                }else if(vo.getStatus().equals("403")){
                    presenterSignUp.makeToastMessage("중복되는 전화번호가 존재합니다.");
                    presenterSignUp.isValidedPn(false);
                }else if(vo.getStatus().equals("500")){
                    presenterSignUp.makeToastMessage("서버 오류 입니다.");
                    presenterSignUp.isValidedPn(false);
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });



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
    public void setPn(String pn, String usertype) {
            LoginModel.user_info_model.setPhoneNumber(pn);
            onSignUp(LoginModel.user_info_model);
    }

    @Override
    public void setSignUp(final User_Info_Model user_info_model) {

        this.user_info_model = user_info_model;
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<BodyVO> call = userService.postSignUp(user_info_model);

        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                Log.d("server","server");
                Log.d("server_before","sssssssss");


                if(bodyVO.getStatus().equals("201")){

                    presenterSignUp.onSignupResponse(true);
                    // presenterSignUp.makeToastMessage("가입 성공 되었습니다.");

                    /*User_Id.setUser_Id(user_info_model.getId());
                    User_Id.setUser_Id(user_info_model.getId());
                    User_Id.setE_mail(user_info_model.getNickname());
                    User_Id.setE_mail(user_info_model.getEmail());
                    User_Id.setPhone_No(user_info_model.getPhoneNumber());
                    User_Id.setType(user_info_model.getUserType());*/
                    performLoginOperation(user_info_model);
                    User_Id.setUser_Id(user_info_model.getId());
                    User_Id.setE_mail(user_info_model.getNickname());
                    User_Id.setE_mail(user_info_model.getEmail());
                    User_Id.setPhone_No(user_info_model.getPhoneNumber());
                    User_Id.setType(user_info_model.getUserType());
                    pushToken();

                }
                else if (bodyVO.getStatus().equals("400")) {
                    presenterSignUp.makeToastMessage( "잘못된 요청");
                }
                else if (bodyVO.getStatus().equals("403")) {
                    presenterSignUp.makeToastMessage( "아이디 중복");
                    presenterSignUp.isValidedId(false);
                }else if (bodyVO.getStatus().equals("409")) {
                    presenterSignUp.makeToastMessage("입력된 핸드폰 계정 아이디 존재");
                    presenterSignUp.isValidedPn(false);

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
    public void setPn(String pn) {
        LoginModel.user_info_model.setPhoneNumber(pn);
       // onSignUp();
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
        Log.e( "validateId: age" ,user_info_model.getAgeRange());

    }
    public void pushToken(){
        final FcmTokenVO fcmTokenVO = new FcmTokenVO();
        fcmTokenVO.setId(user_info_model.getId());
        fcmTokenVO.setFcmToken(FirebaseInstanceId.getInstance().getToken());

        Call<BodyVO> bodyVOCall = userService.putToken(fcmTokenVO);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(@NonNull Call<BodyVO> call, @NonNull Response<BodyVO> response) {

                BodyVO bodyVO = response.body();
                Log.e( "onResponse:4113 ",bodyVO.getStatus().toString());
                if(bodyVO.equals("201")){
                    if(user_info_model.getUserType().equals("G")){
                        user_info_model.setFcmToken(fcmTokenVO.getFcmToken());
                    }else{
                        Log.e( "onResponse:4113 ",bodyVO.getStatus().toString());
                        LoginModel.user_info_model.setFcmToken(fcmTokenVO.getFcmToken());
                    }

                }

            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }

    void performLoginOperation(final User_Info_Model user_info_model) {

        Log.d("maybe_perform","maybe_perform");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Log.d("ddd",user_info_model.getId()+"userinfo"+user_info_model.getUserType());


        //Log.d("check",user_info_model+" ");
        Call<BodyVO> bodyVOCall = userService.postGneralLogin(user_info_model);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {

                BodyVO bodyVO = response.body();

                assert bodyVO != null;
                Log.d("body",""+ bodyVO.getStatus());

                if(bodyVO.getStatus().equals("200")){

                    presenterSignUp.onSignupResponse(true);
                   // presenterSignUp.makeToastMessage("가입 성공 되었습니다.");


                    User_Id.setUser_Id(user_info_model.getId());
                    User_Id.setNickname(user_info_model.getNickname());
                    User_Id.setE_mail(user_info_model.getEmail());
                    User_Id.setPhone_No(user_info_model.getPhoneNumber());
                    User_Id.setType(user_info_model.getUserType());


                    Log.e( "onResponse: name ",User_Id.getNickname() );

                    Log.d("tag_a",""+User_Id.getUser_Id());



                }
                else if (bodyVO.getStatus().equals("024")) {

                    if(user_info_model.getUserType().equals("N")||user_info_model.getUserType().equals("K")){


                    }

                }
                else if (bodyVO.getStatus().equals("400")) {


                }

                else if (bodyVO.getStatus().equals("500")) {

                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                Log.d("test",t.getMessage());

            }
        });

    }
}
