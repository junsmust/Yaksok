package yaksok.dodream.com.yaksok_refactoring.view.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import yaksok.dodream.com.yaksok_refactoring.view.WetherChooseFamilyOrNot;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.signup_presenter.PresenterSignUp;

public class Signup_activity extends AppCompatActivity implements IPresenter_To_SignUp_View , View.OnClickListener{

    boolean isvalidatedId,isIsvalidatedPW,isIsvalidatedEmail;
    PresenterSignUp presenterSignUp;


    public  EditText sign_up_name_edt,sign_up_id_edt,sign_up_pw_edt,sign_up_re_pw_edt,sign_up_phone_number_edt,sign_authorization_number,sign_up_email_edt,sign_up_yourself_email,sign_up_yourself_address_email;
    private Spinner sign_up_year_spin,sign_up_month_spin,sign_up_day_spin,sign_up_phone_conpany_spin,sign_up_phone_first_spin,sign_up_email_spin;
    private Button sign_up_check_id_btn,sign_up_check_pw_btn,sign_up_check_authorization_num_btn,sign_up_compelte_btn,confirm_email_btn;
    String nowDate,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        presenterSignUp = new PresenterSignUp(this);


        presenterSignUp.setGeneralUserType();


        //INIT

        sign_up_yourself_address_email = (EditText)findViewById(R.id.sign_email_yourself_address_edt);
        sign_up_name_edt = (EditText)findViewById(R.id.sign_name_edt);
        sign_up_id_edt = (EditText) findViewById(R.id.sign_id_edt);
        sign_up_yourself_email = (EditText)findViewById(R.id.sign_email_yourself_edt);
        sign_up_email_spin = (Spinner)findViewById(R.id.sign_email_spin);
        //email


        //회원 가입 완료 버튼
        sign_up_compelte_btn = (Button) findViewById(R.id.sign_up_complete_btn);
        sign_up_phone_number_edt = (EditText)findViewById(R.id.sign_phone_number_edt);//전화번호 보내줘야 함
        sign_up_pw_edt = (EditText)findViewById(R.id.sign_pw_edt);
        sign_up_re_pw_edt = (EditText)findViewById(R.id.sign_re_pw_edt);
        sign_up_check_authorization_num_btn = (Button)findViewById(R.id.sign_up_pn_register_btn);


        sign_up_check_id_btn = (Button)findViewById(R.id.sign_check_id_btn);



        sign_up_year_spin = (Spinner)findViewById(R.id.sign_year_spin);
        String[] year_spin = getResources().getStringArray(R.array.year);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,year_spin);
        sign_up_year_spin.setAdapter(arrayAdapter);

        sign_up_month_spin = (Spinner)findViewById(R.id.sign_month_spin);
        String[] month_spin = getResources().getStringArray(R.array.month);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,month_spin);
        sign_up_month_spin.setAdapter(monthAdapter);

        sign_up_day_spin = (Spinner)findViewById(R.id.sign_day_spin);
        String[] day_spin = getResources().getStringArray(R.array.days);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,day_spin);
        sign_up_day_spin.setAdapter(dayAdapter);


        String[] email = getResources().getStringArray(R.array.email_example);
        ArrayAdapter<String> emailAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,email);
        sign_up_email_spin.setAdapter(emailAdapter);




        final long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowDate = simpleDateFormat.format(date);


      sign_up_year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              presenterSignUp.guessAge(nowDate,String.valueOf(sign_up_year_spin.getItemAtPosition(position)));
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });



        sign_up_month_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = sign_up_month_spin.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sign_up_day_spin.setAdapter(dayAdapter);
        sign_up_day_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day =  sign_up_day_spin.getItemAtPosition(position).toString();
                presenterSignUp.setBirth(month,day);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       sign_up_check_id_btn.setOnClickListener(this);
       sign_up_compelte_btn.setOnClickListener(this);
       sign_up_check_authorization_num_btn.setOnClickListener(this);







    }

    @Override
    protected void onStart() {
        super.onStart();

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
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


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setText("회원가입");
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setGravity(Gravity.CENTER);
        actionBar.setTitle(textView.getText().toString());



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        actionBar.setCustomView(view,layoutParams);
    }

    @Override
    public void isValidatedUser(boolean isValidatedID, boolean isValidatedPW, boolean isValidatedEmail) {
        Log.d("tag"," "+isValidatedID+" "+isValidatedPW+" "+isValidatedEmail);
        /*if(isValidatedID&&isValidatedPW&&isValidatedEmail){
            presenterSignUp.onSignUp();
        }
        else if(!isvalidatedId && !isIsvalidatedPW && isIsvalidatedEmail){
            makeToastMessage("아이디와 비밀번호 확인 버튼을 눌러주세요 ");
        }else if(!isvalidatedId && isIsvalidatedPW && !isIsvalidatedEmail){
            makeToastMessage("아이디와 이메일 확인 버튼을 눌러주세요 ");
        }else if(isvalidatedId && !isIsvalidatedPW && !isIsvalidatedEmail){
            makeToastMessage(" 비밀번호와 이메일 확인 버튼을 눌러주세요 ");
        }else if(isvalidatedId && isIsvalidatedPW && !isIsvalidatedEmail){
            makeToastMessage("이메일 확인 버튼을 눌러주세요 ");
        }else if(isvalidatedId && !isIsvalidatedPW && isIsvalidatedEmail){
            makeToastMessage("비밀번호 확인 버튼을 눌러주세요 ");
        }else if(!isvalidatedId && isIsvalidatedPW && isIsvalidatedEmail){
            makeToastMessage("아이디 확인 버튼을 눌러주세요 ");
        }*/

    }

    @Override
    public void makeToastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void nonValidatedIl() {
        sign_up_id_edt.setText("");
    }

    @Override
    public void nonValidatedPW() {
        sign_up_pw_edt.setText("");
    }

    @Override
    public void nonValidatedEmail() {
        sign_up_yourself_email.setText("");
        sign_up_yourself_address_email.setText("");
    }



    @Override
    public void onSignUoResponse(boolean loginResponse) {
            if(loginResponse){
                startActivity(new Intent(getApplicationContext(), WetherChooseFamilyOrNot.class));
                finish();
            }
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void isValidedId(boolean isId) {

        if(isId){
            sign_up_check_id_btn.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark2));
        }else{
            sign_up_check_id_btn.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.main_color));
            sign_up_id_edt.setText("");
        }
    }

    @Override
    public void isValidedPW(boolean isPw) {
        if(isPw){
            sign_up_check_pw_btn.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark2));
        }
    }

    @Override
    public void isValidedEmail(boolean isEmail) {
        if(isEmail){
            confirm_email_btn.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark2));
        }
    }

    @Override
    public void isValidedPn(boolean isPn) {
        if(isPn){
            sign_up_check_authorization_num_btn.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark2));
        }else{
            sign_up_check_authorization_num_btn.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.main_color));
            sign_up_phone_number_edt.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_check_id_btn:
                presenterSignUp.validateId(sign_up_id_edt.getText().toString());
                break;

            case R.id.sign_up_complete_btn:
                presenterSignUp.setName(sign_up_name_edt.getText().toString());
                presenterSignUp.isValdiatedUser();
                break;
            case R.id.sign_up_pn_register_btn:
                presenterSignUp.isvalidatePhone(sign_up_phone_number_edt.getText().toString());
                break;

        }
    }

}
