package yaksok.dodream.com.yaksok_refactoring.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import yaksok.dodream.com.yaksok_refactoring.R;

public class Alarm_On extends Activity {
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

        Log.d("다이얼로그","약 먹으세요 들어옴");

//        Log.d("제발",intent.getStringExtra("user"));

        // userId = intent.getStringExtra("user");
        // pillNo = intent.getStringExtra("pill");

        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* MainPageActivity.n = 1;
                Intent intent1 = new Intent(getApplicationContext(), TakeMedicineDialog.class);
                intent1.putExtra("uId",userId);
                intent1.putExtra("pNo",pillNo);
                //  startActivityForResult(intent1,7000);
                startActivity(intent1);*/
            }
        });

        bt_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
