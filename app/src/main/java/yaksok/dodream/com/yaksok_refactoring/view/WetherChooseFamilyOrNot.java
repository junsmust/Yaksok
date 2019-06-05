package yaksok.dodream.com.yaksok_refactoring.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.view.Main.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;

public class WetherChooseFamilyOrNot extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout find_familyFramelayout;
    private ImageView find_iv,skip_iv;
    private Intent intent;
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
}
