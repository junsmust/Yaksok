package yaksok.dodream.com.yaksok_refactoring.view.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.presenter.login_presenter.Presenter_Login;
import yaksok.dodream.com.yaksok_refactoring.view.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

import com.kakao.auth.ISessionCallback;
import com.kakao.util.exception.KakaoException;

public class Login_activity extends AppCompatActivity implements IPresenterToView , View.OnClickListener{

    private EditText id_edt, pw_edt;
    public Button signUp_btn, login_btn;
    private User_Info_Model user_info_model;
    private Presenter_Login presenter_login;
    UserService userService;
    Retrofit retrofit;
    OAuthLoginHandler moAuthLoginHandler;
    String tocken;

    //kakao

    LoginButton main_kakao_btn;

    ISessionCallback callback;




    //네이버 로그인

    private static OAuthLogin oAuthLogin;
    private static Context mContext;

    private static String OAUTH_CLIENT_ID = "UoO6ax8Kj1TRjRwr8vHf";
    private static String OAUTH_CLIENT_SECRET = "mueA5IlnkV";
    private static String OAUTH_CLIENT_NAME = "gam7325";
    private OAuthLoginButton login_naver_btn;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);


        id_edt = (EditText) findViewById(R.id.main_id_edt);
        pw_edt = (EditText) findViewById(R.id.main_pw_edt);
        signUp_btn = (Button) findViewById(R.id.login_sign_up_btn);
        login_btn = (Button) findViewById(R.id.login_normal_btn);
        Button btn = (Button) findViewById(R.id.btn);

        presenter_login = new Presenter_Login(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
            }
        });

        //kakao
        //KakaoSDK.init(new KakaoSdkAdapter());
        callback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(callback);

        //일반로그인
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_info_model = new User_Info_Model(id_edt.getText().toString(), pw_edt.getText().toString(), "G");
                Toast.makeText(getApplicationContext(), "눌림", Toast.LENGTH_LONG).show();


                presenter_login.Login(user_info_model);
            }
        });


        //네이버 연동 로그인-----------------------------------------------------------------------------
        @SuppressLint("HandlerLeak") OAuthLoginHandler oAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {


                    new Thread() {
                        @Override
                        public void run() {
                            final String accessToken = oAuthLogin.getAccessToken(mContext);
                            String data = oAuthLogin.requestApi(mContext, accessToken, "https://openapi.naver.com/v1/nid/me");

                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                presenter_login.NaverOAuthHandler(jsonObject);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }.start();

                }
            }
        };
        //------------------------------------------------------------------------------------------



        mContext = this;
        init();
        login_naver_btn = (OAuthLoginButton) findViewById(R.id.login_naver_btn);
        login_naver_btn.setOAuthLoginHandler(oAuthLoginHandler);







        main_kakao_btn = (LoginButton) findViewById(R.id.login_kakao_btn);
        main_kakao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.kakao.auth.Session session = com.kakao.auth.Session.getCurrentSession();
                session.addCallback(callback);
                session.open(AuthType.KAKAO_LOGIN_ALL, Login_activity.this);


//                LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);
//                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = LoginActivity.loginInformation.edit();



            }
        });

    }
    public void init() {
        oAuthLogin = OAuthLogin.getInstance();
        oAuthLogin.showDevelopersLog(true);
        oAuthLogin.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }

    //네이버 연동 로그인 -------------------------------------------------------------------------------



    //응답-------------------------------------------------------------------------------------------
    @Override
    public void onLoginResponse(boolean loginResponse) {
        if (loginResponse) {
            startActivity(new Intent(getApplicationContext(), MainPage_activity.class));
        }
    }
    //----------------------------------------------------------------------------------------------


    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void isLoggedIn(boolean isLoggedIn) {

    }

    @Override
    public void onMakeToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void kakaoLogin() {

    }

    @Override
    public void returnToLoginActivity() {
        final Intent intent = new Intent(this,MainPage_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.fake_sign_up:
                signUp_btn.performClick();
                break;


        }

    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
           presenter_login.kakaoLoginMethod();

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {

            if (exception != null) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
            //setContentView(R.layout.activity_main_page_activity);

        }


    }
    /*protected void redirectSignUp() {

        final Intent intent = new Intent(this, KakaoSignUp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {

            return;
        }


        super.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);

    }

}



