package yaksok.dodream.com.yaksok_refactoring.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.view.Main.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FcmTokenVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class WetherChooseFamilyOrNot extends ApplicationBase implements View.OnClickListener{

    private FrameLayout find_familyFramelayout;
    private Button register_btn,skip_btn;
    private Intent intent;
    private User_Info_Model user_info_model = new User_Info_Model();
    private Retrofit retrofit;
    private UserService userService;
    TextView welcom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wether_choose_family_or_not);



        register_btn = (Button) findViewById(R.id.register_btn);
        skip_btn = (Button) findViewById(R.id.skip_btn);
        welcom = (TextView)findViewById(R.id.welcome_ys);

        welcom.setPaintFlags(welcom.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);


        register_btn.setOnClickListener(this);
        skip_btn.setOnClickListener(this);


        pushToken();



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                intent = new Intent(getApplicationContext(), Register_Family.class);
                startActivity(intent);

                finish();
                break;
            case R.id.skip_btn:
                intent = new Intent(getApplicationContext(), MainPage_activity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
    public void pushToken(){

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);


        final FcmTokenVO fcmTokenVO = new FcmTokenVO();
        fcmTokenVO.setId(User_Id.getUser_Id());
        fcmTokenVO.setFcmToken(FirebaseInstanceId.getInstance().getToken());

        Call<BodyVO> bodyVOCall = userService.putToken(fcmTokenVO);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(@NonNull Call<BodyVO> call, @NonNull Response<BodyVO> response) {
                BodyVO bodyVO = response.body();

            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }
}
