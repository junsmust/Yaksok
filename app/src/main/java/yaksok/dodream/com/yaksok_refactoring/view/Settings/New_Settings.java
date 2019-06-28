package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kakao.usermgmt.response.model.User;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.MyPage;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;

public class New_Settings extends ApplicationBase {

    TextView tv_name,tv_acton_name;
    ImageView imageView;
    Button bt_mypage;
    FrameLayout fb;
    RelativeLayout rb_develop;
    Switch sw;
    LoginModel loginModel;

    public SharedPreferences loginInformation;
    public  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_settings);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_white));

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.action_bar_st, null);

        imageView = view.findViewById(R.id.back_layout_back);
        tv_acton_name = view.findViewById(R.id.back_layout_name);
        fb = view.findViewById(R.id.fb_back_layout_back);

        tv_acton_name.setText("설 정");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view,layoutParams);

        tv_name = (TextView)findViewById(R.id.tv_settings_name);
        bt_mypage = (Button)findViewById(R.id.bt_settings_mypage);
        rb_develop = (RelativeLayout)findViewById(R.id.rb_de_info);
        sw = (Switch)findViewById(R.id.sw_autoLog);
        loginModel = new LoginModel();

        tv_name.setText(User_Id.getNickname() + " (" + User_Id.getUser_Id() + ") 님");

        loginInformation = getSharedPreferences("user",MODE_PRIVATE);
        editor = loginInformation.edit();

        if(loginInformation.getBoolean("auto",true)){
            sw.setChecked(true);
        }

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    editor.putString("id","");
                    editor.putString("pw","");
                    editor.putString("userType","");
                    editor.apply();
                    Login_activity.checkBox.setChecked(false);
                }
                if(isChecked){
                    if(User_Id.getType().equals("G")) {
                        editor.putString("id", User_Id.getUser_Id());
                        editor.putString("pw", User_Id.getPass());
                        editor.putString("userType", "G");
                        editor.apply();
                        //loginModel.autoLogin(User_Id.getUser_Id(),User_Id.getPass(),"G",true);
                        Login_activity.checkBox.setChecked(true);
                    }
                    else if(User_Id.getType().equals("K")){
                        editor.putString("id", User_Id.getUser_Id());
                        editor.putString("pw", "");
                        editor.putString("userType", "K");
                        editor.apply();
                        Login_activity.checkBox.setChecked(true);
                    }
                    else if(User_Id.getType().equals("N")){
                        editor.putString("id", User_Id.getUser_Id());
                        editor.putString("pw", "");
                        editor.putString("userType", "N");
                        editor.apply();
                        Login_activity.checkBox.setChecked(true);
                    }
                }
            }
        });

        bt_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPage.class));
            }
        });

        rb_develop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Developer.class));
            }
        });

    }
}
