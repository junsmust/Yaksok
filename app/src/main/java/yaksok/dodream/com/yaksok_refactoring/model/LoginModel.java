package yaksok.dodream.com.yaksok_refactoring.model;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.presenter.IPresennterToModel;
import yaksok.dodream.com.yaksok_refactoring.presenter.Presenter_Login;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;
import yaksok.dodream.com.yaksok_refactoring.presenter.Presenter_Login;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;
import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;

public class LoginModel implements IPresennterToModel {

   User_Info_Model user_info_model = new User_Info_Model();

   private static UserService userService;
   Presenter_Login presenter_login;

    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,10}$"); // 4자리 ~ 10자리까지 가능



    public LoginModel(Presenter_Login presenter_login) {
        this.presenter_login = presenter_login;
    }



    boolean validatePw(String pw) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pw);
           return matcher.matches();

    }



    @Override
    public void Login(User_Info_Model user_info_model) {

//        if(validatePw(user_info_model.getPw())){
//            try {
                performLoginOperation(user_info_model);
                Log.d("maybe","maybe");
//            }catch (Exception e){
//                    e.printStackTrace();
//            }
//        }else{
           // presenter_login.OnErrorMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
//        }
    }
    void performLoginOperation(final User_Info_Model user_info_model) {

        Log.d("maybe_perform","maybe_perform");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);



        Log.d("check",user_info_model+" ");
        Call<BodyVO> bodyVOCall = userService.postGneralLogin(user_info_model);
        Log.d("bodycheck","" +user_info_model);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {

                BodyVO bodyVO = response.body();

                assert bodyVO != null;
                Log.d("body",""+ bodyVO.getStatus());

                if(bodyVO.getStatus().equals("200")){
                    presenter_login.OnLoginResponse(true);
                    presenter_login.MakeToastMessage("로그인 성공입니다.!");
                    User_Id.setUser_Id(user_info_model.getId());
                }
                else if (bodyVO.getStatus().equals("024")) {
                    presenter_login.OnLoginResponse(false);
                    presenter_login.MakeToastMessage("잘못된 요청");
                }
                else if (bodyVO.getStatus().equals("400")) {
                    presenter_login.OnLoginResponse(false);
                    presenter_login.MakeToastMessage("아이디 중복");

                }

                else if (bodyVO.getStatus().equals("500")) {
                    presenter_login.OnLoginResponse(false);
                    presenter_login.MakeToastMessage("서버 오류");
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                Log.d("test",t.getMessage());
            }
        });

    }

    @Override
    public void IsLoggedin() {

    }






}
