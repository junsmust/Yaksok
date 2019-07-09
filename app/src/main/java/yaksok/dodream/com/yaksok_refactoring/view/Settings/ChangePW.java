package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Settings.Presenter_ChangePW;
import yaksok.dodream.com.yaksok_refactoring.presenter.Settings.Presenter_MyPage;

public class ChangePW extends AppCompatActivity implements ChangePW_PresenterToView{
    EditText et_orPW,et_chPW,et_chPWcon;
    Button bt_change;
    String orPW,chPW,chPWcon;
    private Presenter_ChangePW presenter_changePW;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        et_chPW = (EditText)findViewById(R.id.et_chPW);
        et_chPWcon = (EditText)findViewById(R.id.et_chPW_con);
        et_orPW = (EditText)findViewById(R.id.et_orPW);
        bt_change = (Button)findViewById(R.id.bt_change);

        presenter_changePW = new Presenter_ChangePW(this);

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_orPW.getText().toString().equals(et_chPW.getText().toString())){
                    Toast.makeText(getApplicationContext(), "다른 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else if(!et_chPW.getText().toString().equals(et_chPWcon.getText().toString())){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                }
                else{
                    //비밀번호 변경 API호출
                    presenter_changePW.onChangePW(User_Id.getUser_Id(),et_chPW.getText().toString());
                }
            }
        });
    }

    @Override
    public void onChangeResponse(boolean response, int status) {
        if(response){
            if(status==1){
                Toast.makeText(getApplicationContext(), "변경 완료", Toast.LENGTH_LONG).show();
                finish();
            }
            if(status==2){
                Toast.makeText(getApplicationContext(), "기존 비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
            }
        }
    }
}
