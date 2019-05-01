package yaksok.dodream.com.yaksok_refactoring.model.Main;

import android.util.Log;

import java.text.SimpleDateFormat;
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
                    SimpleDateFormat time = new SimpleDateFormat("HHmm");
                    curTime = time.format(now);
                    //현재 시간, 서버에서 받은시간의 시간과 분 나누는 곳
                    int nowtime_hour = Integer.parseInt(curTime.substring(0, 2));
                    int nowtime_min = Integer.parseInt(curTime.substring(2));
                    pilltime_h = Integer.parseInt(nearTimePillVO.getResult().getTime().substring(0, 2));
                    pilltime_m = Integer.parseInt(nearTimePillVO.getResult().getTime().substring(2));
                    ptime = Integer.parseInt(nearTimePillVO.getResult().getTime().substring(0, 4));
                    if(ptime <= Integer.parseInt(curTime)){
                        pillTime_day = false;//다음약이 내일일(초로 계산)
                        pilltime_sec = (((23 * 3600) + (59 * 60)) - ((nowtime_hour * 3600) + (nowtime_min * 60)));
                        times = (pilltime_sec + ((pilltime_h * 3600) + (pilltime_m * 60)));
                        //  h = times / 3600;
                        //  m = (times % 3600 / 60);
                    }
                    else {
                        //다음약이 오늘 일 때(초로 계산)
                        Log.d("if_PillTime_day", "오늘");
                        times = ((pilltime_h * 3600) + (pilltime_m * 60)) - ((nowtime_hour * 3600) + (nowtime_min * 60));
                          /*  if (times < 3600)
                                h = 0;
                            else {
                                h = times / 3600;
                                m = (times % 3600 / 60) + 1;
                            }*/
                    }
                    presenter_main.MyNearTime(times,pillTime_day);
                    presenter_main.onMyNearPillResponce(true);

                    /*tv_main_hour.setText(h + "");
                    if (m / 10 == 0)
                        tv_main_min.setText("0" + String.valueOf(m).substring(0));
                    else
                        tv_main_min.setText(String.valueOf(m).substring(0, 2));//시간의 나머지초/60하여 분으로 표시
                    ctime = Integer.parseInt(curTime.substring(0, 4));
                    BackThread thread = new BackThread();
                    thread.setDaemon(true);
                    thread.start();

                   *//* Intent intent = new Intent(MainPageActivity.this, MyService.class);
                    intent.putExtra("pillTime", String.valueOf(times));*//*

                    Log.d("Test1:", String.valueOf(times));
                    alarm_on();*/

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
}
