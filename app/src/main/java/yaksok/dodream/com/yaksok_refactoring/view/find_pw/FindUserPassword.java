package yaksok.dodream.com.yaksok_refactoring.view.find_pw;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.find_pw.Find_pw_presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class FindUserPassword extends ApplicationBase implements I_find_pw{

    Find_pw_presenter pw_presenter;
    String id,email;
    EditText id_edt,email_edt;
    Button find_btn;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user_password);



        //actionBarSetting
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = LayoutInflater.from(this).inflate(R.layout.chattingactionbar,null);
        ImageView imageView = view.findViewById(R.id.back_layout_imv);
        TextView textView = view.findViewById(R.id.title_txt);

        FrameLayout frameLayout = view.findViewById(R.id.frame_layout);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent resultIntent = new Intent();
        setResult(4000,resultIntent);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        textView.setText("비밀번호 찾기");
        textView.setGravity(Gravity.CENTER);
//        textView.setGravity(Gravity.CENTER);
        actionBar.setTitle(textView.getText().toString());



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        actionBar.setCustomView(view,layoutParams);


        pw_presenter = new Find_pw_presenter(this);
        id_edt = (EditText)findViewById(R.id.find_pw_id_edt);
        email_edt = (EditText)findViewById(R.id.find_pw_email_edt);
        find_btn = (Button)findViewById(R.id.find_pw_btn);


        id = id_edt.getText().toString();
        email = email_edt.getText().toString();

        find_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pw_presenter.findPW(id_edt.getText().toString(),email_edt.getText().toString());
            }
        });



    }


    @Override
    public void onResponse(boolean onSuccess) {

        if(onSuccess){
            Toast.makeText(getApplicationContext(),"임시 비밀번호가 전송 되었습니다.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"아이디 및 이메일을 확인해주세요",Toast.LENGTH_SHORT).show();

        }
    }


}
