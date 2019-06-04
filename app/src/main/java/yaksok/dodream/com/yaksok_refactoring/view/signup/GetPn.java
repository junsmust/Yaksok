package yaksok.dodream.com.yaksok_refactoring.view.signup;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.signup_presenter.PresenterSignUp;

public class GetPn extends AppCompatActivity implements IPresenter_To_SignUp_View {

    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pn);

        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");

        final PresenterSignUp presenterSignUp = new PresenterSignUp(this);

        final EditText get_pn_edt = (EditText) findViewById(R.id.get_pn_edt);
        Button get_pn_ok_btn = (Button) findViewById(R.id.get_pn_btn);

        get_pn_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(get_pn_edt.getText().equals("")){
                    makeToastMessage("번호를 입력해주세요");
                }else{
                    presenterSignUp.setUserType(userType);
                    presenterSignUp.setPhone(get_pn_edt.getText().toString());
                    makeToastMessage(userType+" "+get_pn_edt.getText().toString());


                }

            }
        });

    }

    @Override
    public void isValidatedUser(boolean isValidatedID, boolean isValidatedPW, boolean isValidatedEmail) {

    }

    @Override
    public void makeToastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void nonValidatedIl() {

    }

    @Override
    public void nonValidatedPW() {

    }

    @Override
    public void nonValidatedEmail() {

    }

    @Override
    public void onSignUoResponse(boolean loginResponse) {

        if(loginResponse){
            startActivity(new Intent(getApplicationContext(), Register_Family.class));
            finish();
        }
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void isValidedId(boolean isId) {

    }

    @Override
    public void isValidedPW(boolean isPw) {

    }

    @Override
    public void isValidedEmail(boolean isEmail) {

    }

    @Override
    public void isValidedPn(boolean isPn) {

    }

    @Override
    protected void onStart() {
        super.onStart();

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


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);
//                startActivityForResult(intent,7777);
                finish();
            }
        });
        textView.setText("전화번호 입력");
        textView.setGravity(Gravity.CENTER);
//        textView.setGravity(Gravity.CENTER);
        actionBar.setTitle(textView.getText().toString());



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        actionBar.setCustomView(view,layoutParams);
    }
}
