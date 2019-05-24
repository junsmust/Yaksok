package yaksok.dodream.com.yaksok_refactoring.model.Main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Main.Presenter_Main;
import yaksok.dodream.com.yaksok_refactoring.vo.NearTimePillVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Main_Model implements Main_PresenterToModel{

    Date now;
    String curTime;
    Presenter_Main presenter_main;
    int pilltime_h,pilltime_m,ptime,times,pilltime_sec;
    boolean pillTime_day = true;
    private static UserService userService;
    Retrofit retrofit;

    public Main_Model(Presenter_Main presenter_Main){this.presenter_main = presenter_Main;}

    @Override
    public void getNearTimePill() {
        pilltime_h = 0;
        pillTime_day = true;
        pilltime_m = 0;
        pilltime_sec = 0;
        ptime = 0;
        times = 0;
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        Call<NearTimePillVO> call = userService.getNearTime(User_Id.getUser_Id());
        call.enqueue(new Callback<NearTimePillVO>() {
            @Override
            public void onResponse(Call<NearTimePillVO> call, Response<NearTimePillVO> response) {
                NearTimePillVO nearTimePillVO = response.body();
                System.out.println("############" + nearTimePillVO.getStatus());
                if (nearTimePillVO.getStatus().equals("200")) {
                    //    TakeMedicineVO.setGivingUser(LoginActivity.userVO.getId());
                    //    TakeMedicineVO.setMedicineNO(String.valueOf(nearTimeMedicineVO.getResult().getMyMedicineNo()));
                    now = new Date();
                    SimpleDateFormat time = new SimpleDateFormat("HHmmss");
                    curTime = time.format(now);
                    Log.d("TImee",String.valueOf(curTime) + "!!" +String.valueOf(nearTimePillVO.getResult().getTime()));
                    //현재 시간, 서버에서 받은시간의 시간과 분 나누는 곳
                    int nowtime_hour = Integer.parseInt(curTime.substring(0, 2));
                    int nowtime_min = Integer.parseInt(curTime.substring(2,4));
                    int nowtime_sec = Integer.parseInt(curTime.substring(4));
                    pilltime_h = Integer.parseInt(nearTimePillVO.getResult().getTime().substring(0, 2));
                    pilltime_m = Integer.parseInt(nearTimePillVO.getResult().getTime().substring(2));
                    Log.d("min",String.valueOf(pilltime_m));
                    ptime = Integer.parseInt(nearTimePillVO.getResult().getTime().substring(0, 4));
                    if(ptime <= Integer.parseInt(curTime.substring(0,4))){
                        pillTime_day = false;//다음약이 내일일(초로 계산)
                        pilltime_sec = (((23 * 3600) + (59 * 60)) - ((nowtime_hour * 3600) + (nowtime_min * 60)));
                        times = (pilltime_sec + ((pilltime_h * 3600) + (pilltime_m * 60)));
                    }
                    else {
                        //다음약이 오늘 일 때(초로 계산)
                        Log.d("if_PillTime_day", "오늘");
                        times = ((pilltime_h * 3600) + (pilltime_m * 60)) - ((nowtime_hour * 3600) + (nowtime_min * 60));

                    }
                    Log.d("nowtimeMIN",curTime.substring(4));
                    times -= nowtime_sec;
                    presenter_main.MyNearTime(times,pillTime_day);
                    presenter_main.onMyNearPillResponce(true);

                } else if (nearTimePillVO.getStatus().equals("204")){
                    presenter_main.onMyNearPillResponce(false);
                }
                else if (nearTimePillVO.getStatus().equals("500")){
                    presenter_main.onMyNearPillResponce(false);
                }

            }

            @Override
            public void onFailure(Call<NearTimePillVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void setAlarm(int time, Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotificationUtil.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기

        Log.d("알림 시간", calendar.get(Calendar.YEAR) + "/" + String.valueOf(calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DATE) + "/" + pilltime_h + ":" + pilltime_m);
     /*   if (!pillTime_day) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1 , calendar.get(Calendar.DATE), pilltime_h + 24, pilltime_m);
            Log.d("음..","내일약");
        }
        else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1 , calendar.get(Calendar.DATE), pilltime_h, pilltime_m);//시간을 셋팅
            Log.d("음..","오늘약");
        }*/

     Log.d("time1",String.valueOf(times));
        Log.d("times",String.valueOf(System.currentTimeMillis()+times*1000));

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+times*1000,pendingIntent);
    }
}
