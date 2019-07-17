package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kakao.usermgmt.response.model.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.C_Dialog;
import yaksok.dodream.com.yaksok_refactoring.CustomDialog;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Settings.Presenter_MyPage;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;

public class MyPage extends ApplicationBase implements MyPage_PresenterToView {
    public Retrofit retrofit;
    private Presenter_MyPage presenter_myPage;
    FrameLayout fb;
    TextView id,nickname,email,phone,tv_acton_name;
    ImageView imageView;
    Button bt_secOUT,bt_logOut;
    ToggleButton auto_cancel;
    Button bt_changePW;
    public android.app.AlertDialog.Builder dialog;
    public android.app.AlertDialog.Builder dialog2;
    private TextInputLayout et_oldpw,et_newpw,et_newpw_con;
    String oldpass, newpass, phoneNum;
    public SharedPreferences loginInformation;
    public  SharedPreferences.Editor editor;
    CustomDialog customDialog,customDialog2;
    C_Dialog pw_Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mypage);

        customDialog = new CustomDialog(this);
        customDialog2 = new CustomDialog(this);
        pw_Dialog = new C_Dialog(this);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_under_line));

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.action_bar_develop, null);

        imageView = view.findViewById(R.id.back_layout_back_devel);
        tv_acton_name = view.findViewById(R.id.back_layout_name_delvel);
        fb = view.findViewById(R.id.fb_back_layout_back_devel);

        tv_acton_name.setText("내 정보 관리");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view,layoutParams);


        dialog = new android.app.AlertDialog.Builder(this);
        dialog2 = new android.app.AlertDialog.Builder(this);


        presenter_myPage = new Presenter_MyPage(this);

        presenter_myPage.getMyContext(MyPage.this);

        id = (TextView) findViewById(R.id.tv_setting_id);
        nickname = (TextView) findViewById(R.id.tv_setting_nickname);
        phone = (TextView) findViewById(R.id.tv_setting_phone);
        bt_secOUT = (Button)findViewById(R.id.bt_secessionOUT);
        bt_changePW = (Button)findViewById(R.id.bt_changePW);
        bt_logOut = (Button)findViewById(R.id.bt_logOUT);

        et_oldpw = findViewById(R.id.et_setting_oldpw);
        et_oldpw.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_oldpw.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_newpw = findViewById(R.id.et_setting_newpw);
        et_newpw.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_newpw.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_newpw_con = findViewById(R.id.et_setting_newpw_con);
        et_newpw_con.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_newpw_con.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());


        id.setText(User_Id.getUser_Id());
        nickname.setText(User_Id.getNickname());
        phone.setText(User_Id.getPhone_No().substring(0,3) + "-" + User_Id.getPhone_No().substring(3,7)
                + "-" + User_Id.getPhone_No().substring(7) );

        loginInformation = getSharedPreferences("user",MODE_PRIVATE);
        editor = loginInformation.edit();

        et_newpw.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    et_newpw.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            newpass = et_newpw.getEditText().getText().toString().trim();
                            if(et_newpw.getEditText().getText().toString().length()<=6){
                                et_newpw.setError(" ");
                            }
                            else if(!hasSpecialCharacter(et_newpw.getEditText().getText().toString())){
                                et_newpw.setError(" ");
                            }
                            else if(et_oldpw.getEditText().getText().toString().trim().equals(newpass)){
                                et_newpw.setError("기존 비밀번호와 일치합니다!");
                            }
                            else{
                                et_newpw.setError(null);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }

            }
        });

        et_newpw_con.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    et_newpw_con.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if(et_newpw_con.getEditText().getText().toString().equals(newpass)){
                                et_newpw_con.setError(null);
                            }
                            else if(et_newpw_con.getEditText().getText().toString().equals("")){
                                et_newpw_con.setError(" ");
                            }
                            else{
                                et_newpw_con.setError(" ");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

            }
        });


        bt_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog_log();
            }
        });


        bt_secOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog_sec();
            }
        });

        bt_changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_newpw.getEditText().getText().toString().equals("")||et_oldpw.getEditText().getText().toString().equals("")){
                    pwNull();
                }
                else if(!et_newpw.getEditText().getText().toString().equals(et_newpw_con.getEditText().getText().toString())){
                    pwError("비밀번호가"+"\n"+"일치하지 않습니다.");
                }
                else if(User_Id.getType().equals("G")) {
                    presenter_myPage.onChangePW(User_Id.getUser_Id(),et_oldpw.getEditText().getText().toString(),et_newpw.getEditText().getText().toString());
                }
                else{
                   pwError("SNS회원은 변경 불가합니다.");
                }
            }
        });

    }

    private void makeDialog_sec() {
        customDialog2.title_tv.setText("알림");
        customDialog2.message_tv.setText("\n"+"회원 탈퇴를 하시면"+"\n"+"모든 정보가 삭제 됩니다.");

        customDialog2.show();
        customDialog2.no_btn.setElevation(0);
        customDialog2.ok_btn.setText("탈퇴하기");


        customDialog2.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter_myPage.onSecOut();
                customDialog2.dismiss();

            }
        });

        customDialog2.no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog2.dismiss();
            }
        });
    }

    private void makeDialog_log() {
        customDialog.message_tv.setText("\n"+"로그아웃을 하시겠습니까?");

        customDialog.show();
        customDialog.no_btn.setElevation(0);
        customDialog.ok_btn.setText("로그아웃");


        customDialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("id","");
                editor.putString("pw","");
                editor.putString("userType","");
                editor.apply();
                Login_activity.checkBox.setChecked(false);
                Intent i = new Intent(getApplicationContext()/*현재 액티비티 위치*/ , Login_activity.class/*이동 액티비티 위치*/);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                customDialog.dismiss();

            }
        });

        customDialog.no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }


    @Override
    public void onSecOutResponse(boolean response) {
        if(response){
            ActivityCompat.finishAffinity(this);
            startActivity(new Intent(this, Login_activity.class));
        }
    }

    @Override
    public void onChangeResponse(boolean response, int status) {
        if(response){
            if(status==1){
                pwOk();
                //Toast.makeText(getApplicationContext(), "변경 완료", Toast.LENGTH_LONG).show();
            }
            if(status==2){
                pwNo();
                //Toast.makeText(getApplicationContext(), "기존 비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void pwNo() {
        pw_Dialog.text_tv.setText("기존 비밀번호가"+"\n"+"일치하지 않습니다.");

        pw_Dialog.show();


        pw_Dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_Dialog.dismiss();
            }
        });
    }

    private void pwOk() {
        pw_Dialog.text_tv.setText("\n"+"비밀번호가 변경되었습니다.");

        pw_Dialog.show();


        pw_Dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_Dialog.dismiss();
                finish();
            }
        });
    }

    private void pwNull(){
        pw_Dialog.text_tv.setText("\n"+"비밀번호를 입력하세요");

        pw_Dialog.show();


        pw_Dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_Dialog.dismiss();
            }
        });
    }

    private void pwError(String text){
        pw_Dialog.text_tv.setText(text);

        pw_Dialog.show();


        pw_Dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_Dialog.dismiss();
            }
        });
    }

    public boolean hasSpecialCharacter(String string){
        if(TextUtils.isEmpty(string)){
            return false;
        }
        else{
            for(int i = 0; i < string.length(); i++){
                if(!Character.isLetterOrDigit(string.charAt(i))){
                    return true;
                }
            }
        }
        return false;
    }
}
