package yaksok.dodream.com.yaksok_refactoring.view;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import yaksok.dodream.com.yaksok_refactoring.C_Dialog;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Splash.Presenter_Splash;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;


public class Splash extends AppCompatActivity implements Splash_PresenterToView {

    public Presenter_Splash presenter_splash;
    C_Dialog log_D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        startLoading();

        presenter_splash = new Presenter_Splash(this);
        log_D = new C_Dialog(this);
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*startActivity(new Intent(getApplicationContext(),Login_activity.class));
                finish();*/
                presenter_splash.getServerStatus();
            }
        },3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onServerStatus(boolean status, String isFixing) {
        if(status){
            if(isFixing.equals("N")){
                startActivity(new Intent(getApplicationContext(),Login_activity.class));
                finish();
            }
            else if(isFixing.equals("Y")){
                serverFix();
            }
        }
    }

    private void serverFix(){
        log_D.text_tv.setText("서버 점검중 입니다.");

        log_D.show();

        log_D.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_D.dismiss();
                finish();
            }
        });
    }
}
