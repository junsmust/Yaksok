package yaksok.dodream.com.yaksok_refactoring.view.Main;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.Main.Presenter_Main;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_activity;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.view.chat.Chatting_list;

public class MainPage_activity extends AppCompatActivity implements Main_PresenterToView, View.OnClickListener{

    private Presenter_Main presenter_main;
    private Button bt_InsertPill,bt_InsertFamily,bt_chat;
    private boolean pillTime_day;
    private TextView tv_main_hour, tv_main_min;
    private int myNeaeTime_sec,hour,min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_activity);

        bt_InsertPill = (Button)findViewById(R.id.bt_InsertPill);
        tv_main_hour = (TextView)findViewById(R.id.tv_main_hour);
        tv_main_min = (TextView)findViewById(R.id.tv_main_min);
        bt_InsertFamily = (Button)findViewById(R.id.bt_InsertFamily);
        bt_chat = (Button)findViewById(R.id.bt_Chat);

        presenter_main = new Presenter_Main(this);
        presenter_main.getNearTimePill();

        bt_InsertPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPill_activity.class));
            }
        });


        bt_InsertFamily.setOnClickListener(this);
        bt_chat.setOnClickListener(this);
    }

    @Override // 가까운 약시간 요청해서 UI변경 할 부분
    public void onMyNearPillResponce(boolean MyNearPillResponse) {
        if(MyNearPillResponse) {
            CountDownTimer countDownTimer = new CountDownTimer((myNeaeTime_sec)*1000,60000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if((millisUntilFinished/60000L)/60 < 10){
                        if((millisUntilFinished/6000L)/60 <= 0){
                            tv_main_hour.setText("00");
                        }
                        else {
                            tv_main_hour.setText("0" + String.valueOf((millisUntilFinished / 60000L) / 60));
                            if ((millisUntilFinished / 1000) % 3600 / 60 < 10) {
                                tv_main_min.setText("0" + String.valueOf((millisUntilFinished / 1000) % 3600 / 60));
                            } else {
                                tv_main_min.setText(String.valueOf((millisUntilFinished / 1000) % 3600 / 60));
                            }
                        }
                    }
                    else{
                        tv_main_hour.setText(String.valueOf((millisUntilFinished/60000L)/60));
                        if((millisUntilFinished/1000)%3600/60 < 10){
                            tv_main_min.setText("0"+String.valueOf((millisUntilFinished/1000)%3600 /60));
                        }
                        else{
                            tv_main_min.setText(String.valueOf((millisUntilFinished/1000)%3600 / 60));
                        }
                    }
                    Log.d("Time", String .valueOf((millisUntilFinished/60000L)/60));
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
    }

    @Override // 가까운 약시간 가져와서 화면에 뿌릴 값 정리
    public void MyNearTime(int  nearTime_sec, boolean pillTime_day) {
        myNeaeTime_sec = nearTime_sec;
        this.pillTime_day = pillTime_day;
        Log.d("MyNearTime",String.valueOf(myNeaeTime_sec)+String.valueOf(this.pillTime_day));
    }


    @Override
    protected void onStart() {
        super.onStart();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_InsertFamily:
                startActivity(new Intent(getApplicationContext(), Register_Family.class));
                break;
            case R.id.bt_Chat:
                startActivity(new Intent(getApplicationContext(), Chatting_list.class));
                break;
        }
    }


   /* @Override
    public void onBackPressed() {
        //if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
        //            super.onBackPressed();
        //        } else {
        //            backPressedTime = tempTime;
        //            Toast.makeText(this, "one more", Toast.LENGTH_SHORT).show();

        if(auto){
            Log.d("aaaa",""+auto);
           *//* long tempTime = System.currentTimeMillis();//154872781039
            long intervalTime = tempTime - backPressedTime; // 154872781039 - 0

            // invervalTime = 154872781039
            if(0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime){
                super.onBackPressed();
                moveTaskToBack(true);
                finish();

            }else{
*//*
               // backPressedTime = tempTime; //backPressedTime = 154872781039
                Toast.makeText(getApplicationContext(),"종료하시려면 한번 더 눌러주세요",Toast.LENGTH_LONG).show();

           // }
        }else{
            finish();
        }

    }

    @Override
    public void getAutoInfo(boolean auto) {
        this.auto = auto;
    }*/
}
