package yaksok.dodream.com.yaksok_refactoring.view;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Login_activity.class));
                finish();

            }
        },3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
