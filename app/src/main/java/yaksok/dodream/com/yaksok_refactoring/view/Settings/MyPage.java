package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kakao.usermgmt.response.model.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Settings.Presenter_MyPage;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;

public class MyPage extends AppCompatActivity implements MyPage_PresenterToView {
    public Retrofit retrofit;
    private Presenter_MyPage presenter_myPage;
    TextView id,nickname,email,phone;
    Button bt_secOUT;
    ToggleButton auto_cancel;
    Button bt_changePW;
    public android.app.AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mypage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setTitle("개인 정보");
        dialog = new android.app.AlertDialog.Builder(this);

        presenter_myPage = new Presenter_MyPage(this);

        id = (TextView) findViewById(R.id.tv_setting_id);
        nickname = (TextView) findViewById(R.id.tv_setting_nickname);
        email = (TextView) findViewById(R.id.tv_setting_email);
        phone = (TextView) findViewById(R.id.tv_setting_phone);
        bt_secOUT = (Button)findViewById(R.id.bt_secessionOUT);
        bt_changePW = (Button)findViewById(R.id.bt_changePW);


        id.setText("아이디 : " + User_Id.getUser_Id());
        nickname.setText("닉네임 : " + User_Id.getNickname());
        email.setText("이메일 : " + User_Id.getE_mail());
        phone.setText("전화번호 : " + User_Id.getPhone_No());

        bt_secOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        bt_changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Id.getType().equals("G")) {
                    startActivity(new Intent(getApplicationContext(), ChangePW.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "SNS 회원은 변경 불가", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void showDialog(){

        dialog.setTitle("알림");
        dialog.setMessage("회원 탈퇴를 하시면 모든 정보가 삭제 됩니다.");
        dialog.setCancelable(false);



        dialog.setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter_myPage.onSecOut();
            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }

    @Override
    public void onSecOutResponse(boolean response) {
        if(response){
            ActivityCompat.finishAffinity(this);
            startActivity(new Intent(this, Login_activity.class));
        }
    }
}
