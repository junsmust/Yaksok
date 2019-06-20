package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kakao.usermgmt.response.model.User;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.MyPage;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;

public class New_Settings extends ApplicationBase {

    TextView tv_name;
    Button bt_mypage;
    RelativeLayout rb_develop;
    Switch sw;

    public SharedPreferences loginInformation;
    public  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_settings);

        tv_name = (TextView)findViewById(R.id.tv_settings_name);
        bt_mypage = (Button)findViewById(R.id.bt_settings_mypage);
        rb_develop = (RelativeLayout)findViewById(R.id.rb_de_info);
        sw = (Switch)findViewById(R.id.sw_autoLog);

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
            }
        });

        bt_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPage.class));
            }
        });

    }
}
