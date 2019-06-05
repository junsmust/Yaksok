package yaksok.dodream.com.yaksok_refactoring.Settings;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;

public class MyPage extends AppCompatActivity {
    public Retrofit retrofit,retorofit2;
    public DeleteService deleteService;
    TextView id,nickname,email,phone;
    ToggleButton auto_cancel;
    Button bt1;
    public AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.chat2);
        actionBar.setTitle("개인 정보");



        id = (TextView) findViewById(R.id.tv_setting_id);
        nickname = (TextView) findViewById(R.id.tv_setting_nickname);
        email = (TextView) findViewById(R.id.tv_setting_email);
        phone = (TextView) findViewById(R.id.tv_setting_phone);



        retorofit2 = new Retrofit.Builder()
                .baseUrl(deleteService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deleteService = retorofit2.create(DeleteService.class);

        id.setText("아이디 : " + User_Id.getUser_Id());
        nickname.setText("닉네임 : " + User_Id.getNickname());
        email.setText("이메일 : " + User_Id.getE_mail());
        phone.setText("전화번호 : " + User_Id.getPhone_No());

        dialog = new AlertDialog.Builder(this);
    }
}
