package yaksok.dodream.com.yaksok_refactoring.view.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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

import com.kakao.usermgmt.response.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.C_Dialog;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.view.WetherChooseFamilyOrNot;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.signup_presenter.PresenterSignUp;

public class Signup_activity extends ApplicationBase implements IPresenter_To_SignUp_View , View.OnClickListener{

    boolean isvalidatedId,isIsvalidatedPW,isIsvalidatedEmail;
    PresenterSignUp presenterSignUp;


    public  EditText sign_up_name_edt,sign_up_id_edt,sign_up_phone_number_edt,sign_authorization_number,sign_up_email_edt,sign_up_yourself_email,sign_up_yourself_address_email;
    public  EditText sign_up_phone_number_edt_2,sign_up_phone_number_edt_3;
    private Spinner sign_up_year_spin,sign_up_month_spin,sign_up_day_spin,sign_up_phone_conpany_spin,sign_up_phone_first_spin,sign_up_email_spin;
    private Button sign_up_check_id_btn,sign_up_check_pw_btn,sign_up_check_authorization_num_btn,sign_up_compelte_btn,confirm_email_btn,btn;
    String nowDate,month,day,email_final,birth;
    FrameLayout fb;
    TextView tv_acton_name;
    C_Dialog log_D;
    boolean self_email = false;

    String year;

    User_Info_Model user_info_model;

    private TextInputLayout sign_up_pw_edt,sign_up_re_pw_edt;

    private boolean availableId,avaliablePn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        presenterSignUp = new PresenterSignUp(this);

        log_D = new C_Dialog(this);


        presenterSignUp.setGeneralUserType();
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

        tv_acton_name.setText("회원가입");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view,layoutParams);

        user_info_model = new User_Info_Model();

        email_final = "";
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
        sign_up_phone_number_edt_2 = (EditText)findViewById(R.id.sign_phone_number_edt_2);
        sign_up_phone_number_edt_3 = (EditText)findViewById(R.id.sign_phone_number_edt_3);

        sign_up_pw_edt = findViewById(R.id.sign_pw_edt);
        sign_up_pw_edt.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        sign_up_pw_edt.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        sign_up_re_pw_edt =(TextInputLayout)findViewById(R.id.sign_re_pw_edt);
        sign_up_re_pw_edt.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        sign_up_re_pw_edt.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        sign_up_check_authorization_num_btn = (Button)findViewById(R.id.sign_up_pn_register_btn);



        sign_up_check_id_btn = (Button)findViewById(R.id.sign_check_id_btn);

        sign_up_check_id_btn.setElevation(0);
        sign_up_check_authorization_num_btn.setElevation(0);


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


        final String[] email = getResources().getStringArray(R.array.email_example);
        ArrayAdapter<String> emailAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,email);
        sign_up_email_spin.setAdapter(emailAdapter);



        sign_up_email_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if(String.valueOf(sign_up_email_spin.getItemAtPosition(position)).equals("직접입력")){
                    sign_up_yourself_address_email.setVisibility(View.VISIBLE);
                    self_email = true;
                }
                else{
                    sign_up_yourself_address_email.setVisibility(View.GONE);
                    sign_up_yourself_email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            self_email = false;
                            email_final = sign_up_yourself_email.getText().toString()+"@"+String.valueOf(sign_up_email_spin.getItemAtPosition(position));
                            Log.e("onItemSelected: ", email_final);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });


                    //presenterSignUp.validateEmail(email_final);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sign_up_yourself_address_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




                email_final = sign_up_yourself_email.getText().toString()+"@"+sign_up_yourself_address_email.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });



        final long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowDate = simpleDateFormat.format(date);

        year = "년도";
        month = "월";
        day = "일";


      sign_up_year_spin.setSelection(0);

      sign_up_year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(!String.valueOf(sign_up_year_spin.getItemAtPosition(position)).equals("년도")){
                  presenterSignUp.guessAge(nowDate,String.valueOf(sign_up_year_spin.getItemAtPosition(position)));
                  year = String.valueOf(sign_up_year_spin.getItemAtPosition(position));
              }

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });



        sign_up_month_spin.setSelection(0);
        sign_up_month_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!String.valueOf(sign_up_month_spin.getItemAtPosition(position)).equals("월")){
                    month = sign_up_month_spin.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sign_up_day_spin.setSelection(0);
        sign_up_day_spin.setAdapter(dayAdapter);
        sign_up_day_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!String.valueOf(sign_up_day_spin.getItemAtPosition(position)).equals("일")){
                    day =  sign_up_day_spin.getItemAtPosition(position).toString();
                    presenterSignUp.setBirth(month,day);
                    birth = String.valueOf(month)+"-"+String.valueOf(day);
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sign_up_pw_edt.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    sign_up_re_pw_edt.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                           String newpass = sign_up_re_pw_edt.getEditText().getText().toString().trim();
                                                        if(sign_up_re_pw_edt.getEditText().getText().toString().length()<=6){
                                                           // sign_up_re_pw_edt.setError("문자의 길이가 다릅니다.");
                                                        }
                                                        else if(!hasSpecialCharacter(sign_up_re_pw_edt.getEditText().getText().toString())){
                                                            //sign_up_re_pw_edt.setError("특수 문자가 들어가지 않았습니다.");
                                                        }
                                                        else if(sign_up_pw_edt.getEditText().getText().toString().trim().equals(newpass)){
                                                            //sign_up_re_pw_edt.setError("기존 비밀번호와 일치합니다!");
                                                            presenterSignUp.validatePw(newpass);
                                                        }
                                                        else{
                                                            sign_up_re_pw_edt.setError(null);
                                                        }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }
        });

       sign_up_check_id_btn.setOnClickListener(this);
       sign_up_compelte_btn.setOnClickListener(this);
       sign_up_check_authorization_num_btn.setOnClickListener(this);







    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void isValidatedUser(boolean isValidatedID, boolean isValidatedPW, boolean isValidatedEmail) {


    }

    @Override
    public void makeToastMessage(String message) {
        phoneResult(message);
    }

    @Override
    public void nonValidatedIl() {
        sign_up_id_edt.setText("");
    }

    @Override
    public void nonValidatedPW() {
        sign_up_pw_edt.getEditText().setText("");
    }

    @Override
    public void nonValidatedEmail() {
        sign_up_yourself_email.setText("");
        sign_up_yourself_address_email.setText("");
    }



    @Override
    public void onSignUoResponse(boolean loginResponse) {
            if(loginResponse){
                signOk();
            }
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void isValidedId(boolean isId) {

        if(!isId){
            sign_up_id_edt.setText("");
        }else
            availableId = true;
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
       if(!isPn){
            sign_up_phone_number_edt.setText("");
           sign_up_phone_number_edt_2.setText("");
           sign_up_phone_number_edt_3.setText("");
        }else {
           avaliablePn = true;
       }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_check_id_btn:
                presenterSignUp.validateId(sign_up_id_edt.getText().toString());
                break;

            case R.id.sign_up_complete_btn:
                String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(email_final);
                String pattern = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$";
                //아이디->비빌번호->이름->전화번호->이메일->생년월일->중복확인
                if(sign_up_id_edt.getText().toString().equals("")){
                    phoneResult("아이디를 입력하세요");
                }
                else if(sign_up_pw_edt.getEditText().getText().toString().equals("")){
                    phoneResult("비밀번호를 입력하세요");
                }
                else if(sign_up_re_pw_edt.getEditText().getText().toString().equals("")){
                    phoneResult("비밀번호확인을 입력하세요");
                }
                else if(!sign_up_pw_edt.getEditText().getText().toString().equals(sign_up_re_pw_edt.getEditText().getText().toString())){
                    phoneResult("비밀번호가 같지 않습니다");
                }
                else if(sign_up_name_edt.getText().toString().equals("")) {
                    phoneResult("이름을 입력하세요");
                }
                else if (sign_up_pw_edt.getEditText().getText().toString().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")){
                    phoneResult("비밀번호에"+"\n"+"특수문자를 입력하세요");
                }
                else if(sign_up_pw_edt.getEditText().getText().toString().length()<6){
                    phoneResult("비밀번호를"+"\n"+"6자리 이상 입력하세요");
                }
                else if(sign_up_phone_number_edt.getText().toString().equals("")||
                        sign_up_phone_number_edt_2.getText().toString().equals("")||
                        sign_up_phone_number_edt_3.getText().toString().equals("")){
                    phoneResult("전화번호를 확인하세요");
                }
                else if(sign_up_yourself_email.getText().toString().equals("")){
                    phoneResult("이메일을 입력하세요");
                }
                else if(self_email){
                    if(sign_up_yourself_address_email.getText().toString().equals("")){
                        phoneResult("이메일 주소를 입력하세요");
                    }
                }
                else if(!availableId){
                    phoneResult("아이디 중복을 확인하세요");
                }
                else if(!avaliablePn){
                    phoneResult("전화번호 중복을 확인하세요");
                }
                else if(year.equals("년도") || month.equals("월") || day.equals("일")){
                    errMessage("생년월일을 입력해주세요 ");
                }

                else if(m.matches()){
                    presenterSignUp.validateEmail(email_final);
                    user_info_model.setId(sign_up_id_edt.getText().toString());
                    user_info_model.setPw(sign_up_pw_edt.getEditText().getText().toString());
                    user_info_model.setNickname(sign_up_name_edt.getText().toString());
                    user_info_model.setPhoneNumber(sign_up_phone_number_edt.getText().toString() + sign_up_phone_number_edt_2.getText().toString() +
                            sign_up_phone_number_edt_3.getText().toString());
                    user_info_model.setEmail(email_final);
                    user_info_model.setBirthday(birth);
                    user_info_model.setUserType("G");
                    presenterSignUp.setSignUp(user_info_model);
                }
               /* if(availableId && avaliablePn){
                    if (m.matches() && !sign_up_name_edt.getText().equals(""))  {
                        Log.e("onClick: ", "맞음"+email_final);
                        presenterSignUp.validateEmail(email_final);
                        presenterSignUp.setName(sign_up_name_edt.getText().toString());
                        presenterSignUp.isValdiatedUser();
                        Log.e("onClick: ","sssssssssss" );
                    }else if(!m.matches()){
                        Log.e("onClick: ",email_final);
                        sign_up_yourself_email.setText("");
                        if(!sign_up_yourself_address_email.getText().toString().equals("")){
                            sign_up_yourself_address_email.setText("");
                            makeToastMessage("이메일을 확인해주세요");
                        }
                    }else if(sign_up_name_edt.getText().equals("")){
                        makeToastMessage("이름을 다시 확인해주세요");
                        sign_up_name_edt.setFocusable(true);
                    }
                }else{
                    makeToastMessage("아이디 및 전화번호\n중복확인 안됨");
                }*/

                break;
            case R.id.sign_up_pn_register_btn:
                if(sign_up_phone_number_edt.getText().toString().equals("")||sign_up_phone_number_edt_2.getText().toString().equals("")||sign_up_phone_number_edt_3.getText().toString().equals("")){
                    phoneNull();
                }
                else if((sign_up_phone_number_edt.getText().toString() + sign_up_phone_number_edt_2.getText().toString() + sign_up_phone_number_edt_3.getText().toString()).length()<11){
                    phoneShort();
                }
                else {
                    presenterSignUp.isvalidatePhone(sign_up_phone_number_edt.getText().toString() + sign_up_phone_number_edt_2.getText().toString() + sign_up_phone_number_edt_3.getText().toString());
                }
                break;

        }
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

    private void phoneNull() {
        log_D.text_tv.setText("전화번호를 입력하세요");

        log_D.show();


        log_D.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_D.dismiss();
            }
        });
    }
    private void phoneShort() {
        log_D.text_tv.setText("전화번호가 너무 짧습니다");

        log_D.show();


        log_D.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_D.dismiss();
            }
        });
    }

    private void phoneResult(String text) {
        log_D.text_tv.setText(text);

        log_D.show();


        log_D.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_D.dismiss();
            }
        });
    }

    private void signOk(){
        log_D.text_tv.setText("가입이 완료되었습니다.");

        log_D.show();


        log_D.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Id.setUser_Id(sign_up_id_edt.getText().toString());
                User_Id.setType("G");
                User_Id.setNickname(sign_up_name_edt.getText().toString());
                User_Id.setE_mail(email_final);
                User_Id.setPhone_No(sign_up_phone_number_edt.getText().toString() + sign_up_phone_number_edt_2.getText().toString() + sign_up_phone_number_edt_3.getText().toString());
                Log.d("SignPhone",sign_up_phone_number_edt.getText().toString());
                startActivity(new Intent(getApplicationContext(), WetherChooseFamilyOrNot.class));
                log_D.dismiss();
                finish();
            }
        });
    }
    private void errMessage(String message){
        log_D.text_tv.setText(message);

        log_D.show();


        log_D.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_D.dismiss();

            }
        });
    }

}

