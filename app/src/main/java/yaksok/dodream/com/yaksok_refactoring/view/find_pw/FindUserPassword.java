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
import yaksok.dodream.com.yaksok_refactoring.C_Dialog;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.find_pw.Find_pw_presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class FindUserPassword extends ApplicationBase implements I_find_pw{

    Find_pw_presenter pw_presenter;
    String id,email;
    EditText id_edt,email_edt;
    Button find_btn;
    TextView tv_acton_name;
    FrameLayout fb;
    UserService userService;
    C_Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user_password);



        //actionBarSetting
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_under_line));

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.action_bar_develop, null);

        tv_acton_name = view.findViewById(R.id.back_layout_name_delvel);
        fb = view.findViewById(R.id.fb_back_layout_back_devel);

        tv_acton_name.setText("비밀번호 재설정");

        dialog = new C_Dialog(this);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
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
                if(id_edt.getText().toString().equals("")){
                    D_error("아이디를");
                }
                else if(email_edt.getText().toString().equals("")){
                    D_error("이메일을");
                }
                else {
                    pw_presenter.findPW(id_edt.getText().toString(), email_edt.getText().toString());
                }
            }
        });



    }


    @Override
    public void onResponse(boolean onSuccess) {

        if(onSuccess){
            //Toast.makeText(getApplicationContext(),"임시 비밀번호가 전송 되었습니다.",Toast.LENGTH_SHORT).show();
            D_ok();
        }else{
            //Toast.makeText(getApplicationContext(),"아이디 및 이메일을 확인해주세요",Toast.LENGTH_SHORT).show();
            D_no();

        }
    }

    private void D_ok() {
        dialog.text_tv.setText("임시 비밀번호가"+"\n"+"전송 되었습니다.");

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void D_no() {
        dialog.text_tv.setText("아이디 및 이메일을"+"\n"+"확인해주세요");

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void D_error(String text) {
        dialog.text_tv.setText(text+" 입력해주세요");

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }





}
