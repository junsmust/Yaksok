package yaksok.dodream.com.yaksok_refactoring.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.view.Main.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FcmTokenVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class WetherChooseFamilyOrNot extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout find_familyFramelayout;
    private ImageView find_iv,skip_iv;
    private Intent intent;
    private User_Info_Model user_info_model = new User_Info_Model();
    private Retrofit retrofit;
    private UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wether_choose_family_or_not);

        find_familyFramelayout = (FrameLayout)findViewById(R.id.family_find_frameLayout);

        find_iv = (ImageView)findViewById(R.id.find_family_iv);
        skip_iv = (ImageView)findViewById(R.id.skip_iv);

        find_familyFramelayout.setOnClickListener(this);
        find_iv.setOnClickListener(this);
        skip_iv.setOnClickListener(this);


        pushToken();



    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.family_find_frameLayout:
                intent = new Intent(getApplicationContext(), Register_Family.class);
                startActivity(intent);
                finish();
                break;
            case R.id.find_family_iv:
                intent = new Intent(getApplicationContext(), Register_Family.class);
                startActivity(intent);

                finish();
                break;
            case R.id.skip_iv:
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
