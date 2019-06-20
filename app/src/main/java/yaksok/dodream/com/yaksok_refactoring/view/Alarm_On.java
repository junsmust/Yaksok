package yaksok.dodream.com.yaksok_refactoring.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.Main.NotificationUtil;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.TakeOk;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Alarm_On extends Activity {
    Retrofit retrofit;
    UserService userService;
    Intent intent = getIntent();
    String userId,pillNo;
    TakeOk takeOk;
    boolean status;
    Intent launchIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.alarm_on);

        Button bt_Ok = (Button) findViewById(R.id.bt_D_Ok);
        Button bt_Cancle = (Button)findViewById(R.id.bt_D_Cancel);

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        takeOk = new TakeOk();
        takeOk.setGivingUser(NotificationUtil.userId);
        takeOk.setMyMedicineNo(NotificationUtil.pillNo);


        Log.d("다이얼로그","약 먹으세요 들어옴");

//        Log.d("제발",intent.getStringExtra("user"));

         //userId = intent.getStringExtra("TakingUser");
         //pillNo = intent.getStringExtra("TakingPill");

        launchIntent = getPackageManager().getLaunchIntentForPackage("yaksok.dodream.com.yaksok");
        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent1.putExtra("uId",userId);
               // intent1.putExtra("pNo",pillNo);
               // startActivityForResult(intent1,7000);
                //finish();
                //startActivity(intent1);
                Call<BodyVO> call = userService.putTakeMedicine(takeOk);
                System.out.println("@@@@@@@@@@@@@@@@@@@");
                call.enqueue(new Callback<BodyVO>() {
                    @Override
                    public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                        BodyVO mVO = response.body();
                        if (mVO.getStatus().equals("201")) {
                            Log.d("약복용 서비스 ", "복용 완료");
                        } else if (mVO.getStatus().equals("202")) {
                            Log.d("약복용 서비스 ", "복용 완료 후 약 삭제");
                        }
                    }

                    @Override
                    public void onFailure(Call<BodyVO> call, Throwable t) {
                        System.out.println(t.fillInStackTrace().getMessage());
                    }

                });
                if(isAppRunning()){
                    Intent i = new Intent(getApplicationContext()/*현재 액티비티 위치*/ , Login_activity.class/*이동 액티비티 위치*/);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                   // startActivity(launchIntent);
                    finish();
                }
                else{
                    finish();
                }
            }
        });

        bt_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private boolean isAppRunning(){
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i++){
            if(procInfos.get(i).processName.equals(getPackageName())){

                return true;
            }
        }
        return false;
    }
}
