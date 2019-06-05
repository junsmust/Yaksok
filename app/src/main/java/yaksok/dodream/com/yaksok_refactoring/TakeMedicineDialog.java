package yaksok.dodream.com.yaksok_refactoring;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import yaksok.dodream.com.yaksok_refactoring.model.Main.NotificationUtil;
import yaksok.dodream.com.yaksok_refactoring.view.Alarm_On;
import yaksok.dodream.com.yaksok_refactoring.view.Main.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.TakeOk;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class TakeMedicineDialog extends AppCompatActivity {
    Retrofit retrofit;
    UserService userService;
    Alarm_On alarm_on = new Alarm_On();
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        TakeOk takeOk = new TakeOk();
        takeOk.setGivingUser(NotificationUtil.userId);
        takeOk.setMyMedicineNo(NotificationUtil.pillNo);

        // Log.d("Check",intent.getStringExtra("uId") + takeOk.getGivingUser());



        Call<BodyVO> call = userService.putTakeMedicine(takeOk);
        System.out.println("@@@@@@@@@@@@@@@@@@@");
        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO mVO = response.body();
                if (mVO.getStatus().equals("201")) {
                    Log.d("약복용 서비스 ", "복용 완료");
                    //  startActivity(new Intent(this, LoginActivity.class));
                } else if (mVO.getStatus().equals("202")) {
                    Log.d("약복용 서비스 ", "복용 완료 후 약 삭제");
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                System.out.println(t.fillInStackTrace().getMessage());
            }

        });
        if(isAppRunning(this)) {
            finish();
        }
        else {
            finish();
            startActivity(new Intent(this, Login_activity.class));
        }


    }
    private boolean isAppRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i++){
            if(procInfos.get(i).processName.equals(context.getPackageName())){
                return true;
            }
        }

        return false;
    }

}
