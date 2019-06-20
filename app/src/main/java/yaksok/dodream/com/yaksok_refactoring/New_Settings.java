package yaksok.dodream.com.yaksok_refactoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kakao.usermgmt.response.model.User;

import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.MyPage;

public class New_Settings extends ApplicationBase{

    TextView tv_name;
    Button bt_mypage;
    RelativeLayout rb_develop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_settings);

        tv_name = (TextView)findViewById(R.id.tv_settings_name);
        bt_mypage = (Button)findViewById(R.id.bt_settings_mypage);
        rb_develop = (RelativeLayout)findViewById(R.id.rb_de_info);

        tv_name.setText(User_Id.getNickname() + " (" + User_Id.getUser_Id() + ") 님");

        bt_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPage.class));
            }
        });

    }
}
