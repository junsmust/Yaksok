package yaksok.dodream.com.yaksok_refactoring.model.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.signup.SignUpModel;
import yaksok.dodream.com.yaksok_refactoring.presenter.login_presenter.Presenter_Login;
import yaksok.dodream.com.yaksok_refactoring.presenter.Main.Presenter_Main;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class LoginModel implements IPresennterToModel {

  public static User_Info_Model user_info_model = new User_Info_Model();



    private static UserService userService;

    Presenter_Login presenter_login;
    Presenter_Main presenter_main;

    private static OAuthLogin oAuthLogin;
    private static Context mContext;
    private String tocken,data;
    String id,pw;
    Gender user_gender;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean auto;

    private SignUpModel signUpModel = new SignUpModel();

    String id_ex,type_ex;





    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,10}$"); // 4자리 ~ 10자리까지 가능



    public LoginModel(Presenter_Login presenter_login) {
        this.presenter_login = presenter_login;
    }

    public LoginModel(Presenter_Main presenter_main) {
        this.presenter_main = presenter_main;
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
                    presenter_login.OnLoginResponse(true);

                    User_Id.setUser_Id(user_info_model.getId());

                    Log.d("tag_a",""+User_Id.getUser_Id());


                    presenter_login.MakeToastMessage("ee"+auto);
                }
                else if (bodyVO.getStatus().equals("024")) {
                    presenter_login.OnLoginResponse(false);
                    if(user_info_model.getUserType().equals("N")||user_info_model.getUserType().equals("K")){
                        presenter_login.onSnsSignUp(user_info_model.getUserType());

                    }
                    presenter_login.MakeToastMessage("로그인 실패");
                }
                else if (bodyVO.getStatus().equals("400")) {
                    presenter_login.OnLoginResponse(false);
                    presenter_login.MakeToastMessage("잘못된요청(userType)");

                }

                else if (bodyVO.getStatus().equals("500")) {
                    presenter_login.OnLoginResponse(false);
                    presenter_login.MakeToastMessage("서버 오류");
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                Log.d("test",t.getMessage());

                presenter_login.redirectLoginActivity();
            }
        });

    }

    @Override
    public void IsLoggedin() {

    }


    @SuppressLint("HandlerLeak")
    @Override
    public void NaverOAuthHandler(JSONObject jsonObject) throws JSONException {



        id = jsonObject.getJSONObject("response").getString("id");
        String name = jsonObject.getJSONObject("response").getString("name");
        String profile_path = jsonObject.getJSONObject("response").getString("profile_image");
        String email = jsonObject.getJSONObject("response").getString("email");
        String birthday = jsonObject.getJSONObject("response").getString("birthday");
        String age_range = jsonObject.getJSONObject("response").getString("age");

       user_info_model.setId(id);
       user_info_model.setUserType("N");
       user_info_model.setNickname(name);
       user_info_model.setProfileImagePath(profile_path);
       user_info_model.setEmail(email);
       user_info_model.setBirthday(birthday);
       user_info_model.setAgeRange(age_range);

       User_Id.setUser_Id(user_info_model.getId());

        if(auto){
            editor.putString("id",user_info_model.getId());
            editor.putString("pw",user_info_model.getPw());
            editor.putString("userType",user_info_model.getUserType());
            editor.apply();

            Log.d("auto333","실행됨"+user_info_model.getId());
        }


        performLoginOperation(user_info_model);







    }

    @Override
    public void kakaoLoginMethod() {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");
        keys.add("kakao_account.birthday");
        keys.add("kakao_account.gender");
        keys.add("kakao_account.gender");
        keys.add("kakao_account.age_range");



        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onSuccess(MeV2Response result) {


                OptionalBoolean gender = result.getKakaoAccount().hasGender();
                if(gender == OptionalBoolean.FALSE){
                    handleScopeError(result.getKakaoAccount());

                    user_info_model.setId(String.valueOf(result.getId()));
                    user_info_model.setUserType("K");
                    user_info_model.setNickname(result.getNickname());
                    user_info_model.setProfileImagePath(result.getProfileImagePath());
                    user_info_model.setEmail(result.getKakaoAccount().getEmail());
                    user_info_model.setBirthday(result.getKakaoAccount().getBirthday());
                    user_info_model.setAgeRange(String.valueOf(result.getKakaoAccount().getAgeRange()));

                    User_Id.setUser_Id(user_info_model.getId());

                    Log.i("nickname",result.getNickname());
                    Log.i("profile_img",result.getProfileImagePath());
                    Log.i("email",result.getKakaoAccount().getEmail());
                   // Log.i("birthday",kakaoUser_info.getBirth());
                    Log.i("id",""+result.getId());
                   /* Log.i("arange",""+kakaoUser_info.getK_age());
                    Log.i("userType",""+kakaoUser_info.getUser_type());
*/
                if(auto){
                    editor.putString("id",user_info_model.getId());
                    editor.putString("pw",user_info_model.getPw());
                    editor.putString("userType",user_info_model.getUserType());
                    editor.apply();

                    Log.d("auto333",""+User_Id.getUser_Id());
                }


                    performLoginOperation(user_info_model);

                    //presenter_login.redirectLoginActivity();

                }




            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
            }


            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }



        });
    }

    @Override
    public void handleScopeError(UserAccount userAccount) {
        List<String> neededScopes = new ArrayList<>();


    }

    @Override
    public void checkBox(SharedPreferences sharedPreferences, SharedPreferences.Editor editor,boolean auto) {
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
        this.auto = auto;

        if(auto){

            Log.d("sser","sss");
            editor.putString("id",user_info_model.getId());
            editor.putString("pw",user_info_model.getPw());
            editor.putString("userType",user_info_model.getUserType());
            editor.apply();

            presenter_login.MakeToastMessage(sharedPreferences.getString("id","")+sharedPreferences.getString("pw","")+sharedPreferences.getBoolean("auto",true)+sharedPreferences.getString("userType",""));

        }


    }

    @Override
    public void autoLogin(String id, String pw, String userType,boolean auto) {
        user_info_model.setId(id);
        user_info_model.setPw(pw);
        user_info_model.setUserType(userType);

        this.auto = auto;





        Log.d("exxxx",id+" "+pw+" "+userType);

        performLoginOperation(user_info_model);
    }

    @Override
    public void autoLogin(String id, String userType,boolean auto) {
//        user_info_model.setId(id);
//        user_info_model.setId(userType);


        user_info_model = new User_Info_Model(id,userType);

        Log.d("autoSns2",userType);
        Log.d("autoSns3",user_info_model.getId()+user_info_model.getUserType());

        this.auto  = auto;
        performLoginOperation(user_info_model);


    }



    @Override
    public void getEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }


}
