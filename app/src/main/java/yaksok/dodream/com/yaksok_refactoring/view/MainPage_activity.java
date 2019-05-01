package yaksok.dodream.com.yaksok_refactoring.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_activity;

public class MainPage_activity extends AppCompatActivity{

    private Button bt_InsertPill;
    boolean auto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_activity);

        bt_InsertPill = (Button)findViewById(R.id.bt_InsertPill);
        bt_InsertPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPill_activity.class));
            }
        });
}

    @Override
    protected void onStart() {
        super.onStart();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
