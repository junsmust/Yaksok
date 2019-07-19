package yaksok.dodream.com.yaksok_refactoring.view.find_pw;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import yaksok.dodream.com.yaksok_refactoring.CustomDialog;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.find_pw.Find_pw_presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class FindUserPassword extends ApplicationBase implements I_find_pw,View.OnClickListener {

    Find_pw_presenter pw_presenter;
    String id, email;
    EditText id_edt, email_edt;
    Button find_btn;
    TextView tv_acton_name;
    FrameLayout fb;
    UserService userService;
    C_Dialog dialog;
    ProgressDialog progressDialog;
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

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.action_bar_develop, null);

        tv_acton_name = view.findViewById(R.id.back_layout_name_delvel);
        fb = view.findViewById(R.id.fb_back_layout_back_devel);

        tv_acton_name.setText("비밀번호 찾기");


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view, layoutParams);




        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels/2+300; //디바이스 화면 너비
        int height = dm.heightPixels/3+100; //디바이스 화면 높이





        dialog = new C_Dialog(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("임시 비밀번호 전송중입니다.");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
       wm.copyFrom(dialog.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미

        wm.width = width;  //화면 너비의 절반
        wm.height = height;  //화면 높이의 절반


        pw_presenter = new Find_pw_presenter(this);
        id_edt = (EditText) findViewById(R.id.find_pw_id_edt);
        email_edt = (EditText) findViewById(R.id.find_pw_email_edt);
        find_btn = (Button) findViewById(R.id.find_pw_btn);

        pw_presenter.getContext(FindUserPassword.this);


        id = id_edt.getText().toString();
        email = email_edt.getText().toString();

        find_btn.setOnClickListener(this);

       /* find_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e( "btn: ", "눌림2222");
            }
                if(id_edt.getText().toString().equals("")){
                    D_error("아이디를");
                }
                if(email_edt.getText().toString().equals("")){
                    D_error("이메일을");
                }
                else {
                    Log.e("onClick: ",id_edt.getText().toString()+" "+id_edt.getText().toString() );
                    pw_presenter.findPW(id_edt.getText().toString(), email_edt.getText().toString());
                }
            }
        });
*/


    }


    @Override
    public void onResponse(boolean onSuccess, String message) {
        progressDialog.dismiss();
        if (onSuccess) {
            D_ok(message);
        } else {
            D_no(message);
        }

    }

    private void D_ok(String message) {
        dialog.text_tv.setText(message);

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void D_no(String message) {
        dialog.text_tv.setText(message);

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void D_error(String text) {
        dialog.text_tv.setText(text);

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_pw_btn:
                if (id_edt.getText().toString().equals("") && !email_edt.getText().toString().equals("")) {
                    D_error("아이디를 확인해주세요");
                    id_edt.setFocusable(true);
                }else if(id_edt.getText().toString().equals("")){
                    D_error("아이디를 확인해주세요");
                } else if (email_edt.getText().toString().equals("") && !id_edt.getText().toString().equals("")) {
                    D_error("이메일을 확인해주새요");
                    email_edt.setFocusable(true);
                }else if (email_edt.getText().toString().equals("")) {
                    D_error("이메일을 확인해주새요");
                    email_edt.setFocusable(true);
                } else if(email_edt.getText().toString().equals("") && id_edt.getText().toString().equals("")){
                    D_error("아이디 및 이메일을 입력해주세");
                    id_edt.setFocusable(true);
                }
                else {
                    Log.e("onClick: ", id_edt.getText().toString() + " " + id_edt.getText().toString());
                    pw_presenter.findPW(id_edt.getText().toString(), email_edt.getText().toString());}

                    break;

        }
        progressDialog.show();
    }
}
