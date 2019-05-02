package yaksok.dodream.com.yaksok_refactoring.view.InsertPill;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.SearchPill.SearchPill_activity;

public class InsertPill_activity extends AppCompatActivity implements InsertPill_PresenterToView, View.OnClickListener{

    EditText et_DiseaseName;
    Button bt_ListAdd, bt_1time, bt_2time, bt_3time;
    TextView tv_1h, tv_1m, tv_2h, tv_2m, tv_3h, tv_3m;
    TextView et_dosagi;
    ImageView minus_count,plus_count;
    List<String> time;
    String h, m;
    TimePickerDialog dialog1,dialog2,dialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertpill_self);

        et_DiseaseName = (EditText)findViewById(R.id.et_DiseaseName);
        bt_ListAdd = (Button)findViewById(R.id.bt_ListAdd);
        minus_count = (ImageView) findViewById(R.id.minus_count);
        plus_count = (ImageView) findViewById(R.id.plus_count);
        et_dosagi = (TextView) findViewById(R.id.et_Dosagi);
        bt_1time = (Button) findViewById(R.id.bt_1time);
        bt_2time = (Button) findViewById(R.id.bt_2time);
        bt_3time = (Button) findViewById(R.id.bt_3time);
        tv_1h = (TextView) findViewById(R.id.tv_1_h);
        tv_1m = (TextView) findViewById(R.id.tv_1_m);
        tv_2h = (TextView) findViewById(R.id.tv_2_h);
        tv_2m = (TextView) findViewById(R.id.tv_2_m);
        tv_3h = (TextView) findViewById(R.id.tv_3_h);
        tv_3m = (TextView) findViewById(R.id.tv_3_m);

        minus_count.setOnClickListener(this);
        plus_count.setOnClickListener(this);

        time =  new ArrayList<>();

        dialog1 = new TimePickerDialog(this, listener1, 00, 00, true);
        bt_1time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.show();
            }
        });
        dialog2 = new TimePickerDialog(this, listener2, 00, 00, true);
        bt_2time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.show();
            }
        });
        dialog3 = new TimePickerDialog(this, listener3, 00, 00, true);
        bt_3time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.show();
            }
        });


        bt_ListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchPill_activity.class));
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            if(String.valueOf(hourOfDay).length() < 2)
                h = "0"+String.valueOf(hourOfDay);
            else
                h = String.valueOf(hourOfDay);
            if(String.valueOf(minute).length() < 2)
                m = "0" + String.valueOf(minute);
            else
                m = String.valueOf(minute);

            tv_1h.setText(h + "시 ");
            tv_1m.setText(m + "분");
            time.add(h+m);
        }
    };
    private TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            if(String.valueOf(hourOfDay).length() < 2)
                h = "0"+String.valueOf(hourOfDay);
            else
                h = String.valueOf(hourOfDay);
            if(String.valueOf(minute).length() < 2)
                m = "0" + String.valueOf(minute);
            else
                m = String.valueOf(minute);
            tv_2h.setText(h + "시 ");
            tv_2m.setText(m + "분");
            time.add(h+m);
        }
    };
    private TimePickerDialog.OnTimeSetListener listener3 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            if(String.valueOf(hourOfDay).length() < 2)
                h = "0"+String.valueOf(hourOfDay);
            else
                h = String.valueOf(hourOfDay);
            if(String.valueOf(minute).length() < 2)
                m = "0" + String.valueOf(minute);
            else
                m = String.valueOf(minute);
            tv_3h.setText(h + "시 ");
            tv_3m.setText(m + "분");
            time.add(h+m);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.minus_count:
                if(Integer.parseInt(et_dosagi.getText().toString())<=0){
                    Toast.makeText(getApplicationContext(),"복욕횟수를 차감할 수 없습니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    int count = Integer.parseInt(et_dosagi.getText().toString());
                    count--;
                    System.out.print(count);
                    et_dosagi.setText(String.valueOf(count));
                    //e_dosagi.setText(et_dosagi.getText().toString().substring(0,1)+String.valueOf(count)+et_dosagi.getText().toString().substring(2));
                }
                break;
            case R.id.plus_count:
                int count = Integer.parseInt(et_dosagi.getText().toString());
                count++;
                System.out.print(count);
                et_dosagi.setText(String.valueOf(count));
                break;

        }
    }
}
