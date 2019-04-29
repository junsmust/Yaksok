package yaksok.dodream.com.yaksok_refactoring.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Retrofit;
import yaksok.dodream.com.yaksok_refactoring.view.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.presenter.Presenter_Login;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;
import yaksok.dodream.com.yaksok_refactoring.view.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.presenter.Presenter_Login;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;
import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.presenter.Presenter_Login;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Login_activity extends AppCompatActivity implements IPresenterToView,View.OnClickListener {

    private EditText id_edt, pw_edt;
    public Button signUp_btn, login_btn;
    private User_Info_Model user_info_model;
    private Presenter_Login presenter_login;
    UserService userService;
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);



        id_edt = (EditText) findViewById(R.id.main_id_edt);
        pw_edt = (EditText) findViewById(R.id.main_pw_edt);
        signUp_btn = (Button) findViewById(R.id.login_sign_up_btn);
        login_btn = (Button) findViewById(R.id.login_normal_btn);
        Button btn = (Button)findViewById(R.id.btn);

        presenter_login = new Presenter_Login(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_info_model = new User_Info_Model(id_edt.getText().toString(),pw_edt.getText().toString(),"G");
                Toast.makeText(getApplicationContext(),"눌림",Toast.LENGTH_LONG).show();



                presenter_login.Login(user_info_model);
            }
        });



    }


    @Override
    public void onLoginResponse(boolean loginResponse) {
        if (loginResponse) {
            startActivity(new Intent(getApplicationContext(), MainPage_activity.class));
        }
    }

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fake_sign_up:
                signUp_btn.performClick();
                break;


        }
    }
}


