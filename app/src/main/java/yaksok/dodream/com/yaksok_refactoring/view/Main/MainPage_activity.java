package yaksok.dodream.com.yaksok_refactoring.view.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.New_Settings;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Main.Presenter_Main;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_activity;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.view.chat.Chatting_list;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;

public class MainPage_activity extends ApplicationBase implements Main_PresenterToView, View.OnClickListener{

    private Presenter_Main presenter_main;
    private Button bt_InsertPill,bt_InsertFamily,bt_chat,bt_setting;
    private boolean pillTime_day;
    private TextView tv_main_hour, tv_main_min,tv_main_sec,tv_main_hour_text, tv_main_min_text,tv_main_sec_text,tv_name;
    private int myNeaeTime_sec,hour,min;
    CountDownTimer countDownTimer = null;
    private Boolean countSW = false;
    LinearLayout lL1;
    ProgressBar progressBar;
    public SharedPreferences loginInformation;
    public  SharedPreferences.Editor editor;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_activity);

        bt_setting = (Button)findViewById(R.id.bt_option);
        bt_InsertPill = (Button)findViewById(R.id.bt_InsertPill);
        tv_main_hour = (TextView)findViewById(R.id.tv_main_hour);
        tv_main_min = (TextView)findViewById(R.id.tv_main_min);
        //tv_main_sec = (TextView)findViewById(R.id.tv_main_sec);
        tv_main_hour_text = (TextView)findViewById(R.id.tv_main_hour_text);
        tv_main_min_text = (TextView)findViewById(R.id.tv_main_min_text);
       // tv_main_sec_text = (TextView)findViewById(R.id.tv_main_sec_text);
        bt_InsertFamily = (Button)findViewById(R.id.bt_InsertFamily);
        bt_chat = (Button)findViewById(R.id.bt_Chat);
        tv_name = (TextView)findViewById(R.id.tv_main_name);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        lL1 = (LinearLayout)findViewById(R.id.lL1);

       tv_name.setText(User_Id.getNickname());
        tv_name.setPaintFlags(tv_name.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        tv_main_hour_text.setPaintFlags(tv_main_hour_text.getPaintFlags()  | Paint.FAKE_BOLD_TEXT_FLAG);
        tv_main_min_text.setPaintFlags(tv_main_min_text.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        bt_chat.setPaintFlags(bt_chat.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        bt_InsertPill.setPaintFlags(bt_InsertPill.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        bt_InsertFamily.setPaintFlags(bt_InsertFamily.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        bt_setting.setPaintFlags(bt_setting.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);


        presenter_main = new Presenter_Main(this);

        bt_InsertPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPill_activity.class));
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);

            }
        });

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), New_Settings.class));
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);

            }
        });


        bt_InsertFamily.setOnClickListener(this);
        bt_chat.setOnClickListener(this);

        loginInformation = getSharedPreferences("user",MODE_PRIVATE);
        editor = loginInformation.edit();

    }

    @Override // 가까운 약시간 요청해서 UI변경 할 부분
    public void onMyNearPillResponce(boolean MyNearPillResponse, int status) {
        if(MyNearPillResponse) {
            if(status == 204){
                progressBar.setVisibility(View.INVISIBLE);
                lL1.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(false);
                tv_main_hour.setText(""+" "+"등록된 약이 없습니다");
                tv_main_hour.setPaintFlags(tv_main_min.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                tv_main_hour.setTextSize(30);
                tv_main_hour.setTextColor(Color.WHITE);
                //tv_main_hour.setText("");
               // tv_main_sec.setText("");
                tv_main_min.setText("");
                tv_main_hour_text.setText("");
                tv_main_min_text.setText("");
                //tv_main_min_text.setElevation(20);
                //tv_main_sec_text.setText("");
            }
            else if(status == 200) {
                progressBar.setVisibility(View.INVISIBLE);
                lL1.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(false);
                tv_main_hour.setTextSize(60);
                tv_main_min.setTextColor(Color.WHITE);
                tv_main_hour_text.setText("시간");
                tv_main_min_text.setText("분");
               // tv_main_sec_text.setText("초");
                countSW = true;
                countDownTimer = new CountDownTimer((myNeaeTime_sec+60) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //시간 설정 부분
                        if (millisUntilFinished >= 3600000) {
                            if ((millisUntilFinished / 60000L) / 60 < 10) {
                                tv_main_hour.setText("0" + String.valueOf((millisUntilFinished / 60000L) / 60));
                            } else {
                                tv_main_hour.setText(String.valueOf((millisUntilFinished / 60000L) / 60));
                            }
                        } else {
                            tv_main_hour.setText("00");
                        }
                        tv_main_hour.setTextSize(60);
                        tv_main_hour.setPaintFlags(tv_main_hour.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

                        //분 설정 부분
                        if ((millisUntilFinished >= 60000)) {
                            if ((millisUntilFinished / 1000) % 3600 / 60 < 10) {
                                tv_main_min.setText("0" + String.valueOf(((millisUntilFinished / 1000) % 3600 / 60)));
                            } else {
                                tv_main_min.setText(String.valueOf(((millisUntilFinished / 1000) % 3600 / 60)));
                            }
                        } else {
                            tv_main_min.setText("00");
                        }
                        tv_main_min.setTextSize(60);
                        tv_main_min.setPaintFlags(tv_main_min.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

                        tv_main_min_text.setShadowLayer(30,2,7,Color.GRAY);
                        tv_main_min.setShadowLayer(30,2,7,Color.GRAY);
                        tv_main_hour.setShadowLayer(30,2,7,Color.GRAY);
                        tv_main_hour_text.setShadowLayer(30,2,7,Color.GRAY);
                        //초 설정 부분
                       /* if ((millisUntilFinished >= 1000)) {
                            if ((millisUntilFinished / 1000) % 3600 % 60 < 10) {
                                tv_main_sec.setText("0" + (millisUntilFinished / 1000) % 3600 % 60);
                            } else {
                                tv_main_sec.setText(String.valueOf((millisUntilFinished / 1000) % 3600 % 60));
                            }
                        }*/
                   /* if((millisUntilFinished/60000L)/60 < 10){
                        if((millisUntilFinished/6000L)/60 <= 0){
                            tv_main_hour.setText("00");
                            if ((millisUntilFinished / 1000) % 3600 / 60 < 10) {
                                if((millisUntilFinished / 1000) % 3600 / 60 == 0){
                                }
                                else
                                    tv_main_min.setText("0" + String.valueOf((millisUntilFinished / 1000) % 3600 / 60));
                            } else {
                                tv_main_min.setText(String.valueOf((millisUntilFinished / 1000) % 3600 / 60));
                            }
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
                    Log.d("Time", String .valueOf((millisUntilFinished/60000L)/60));*/
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        }
    }

    @Override // 가까운 약시간 가져와서 화면에 뿌릴 값 정리
    public void MyNearTime(int  nearTime_sec, boolean pillTime_day) {
        presenter_main.onAlarm(nearTime_sec,this);
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
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);

                break;
            case R.id.bt_Chat:
                startActivity(new Intent(getApplicationContext(), Chatting_list.class));
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lL1.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
            presenter_main.getNearTimePill();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(countSW)
            countDownTimer.cancel();

    }

    @Override
    public void onBackPressed() {

        if(loginInformation.getBoolean("auto",true)){
            long tempTime = System.currentTimeMillis();//154872781039
            long intervalTime = tempTime - backPressedTime; // 154872781039 - 0

            // invervalTime = 154872781039
            if(0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime){
                super.onBackPressed();
                moveTaskToBack(true);
                AppFinish();

            }else{

                backPressedTime = tempTime; //backPressedTime = 154872781039
                Toast.makeText(getApplicationContext(),"종료하시려면 한번 더 눌러주세요",Toast.LENGTH_LONG).show();

            }
        }else{
            finish();
        }

    }


    public void AppFinish(){
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
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
