package yaksok.dodream.com.yaksok_refactoring.view.InsertPill;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Presenter_InsertPill;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.Sel_AlarmRecive.Sel_AlarmRecive_activity;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.SearchPill.SearchPill_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public class InsertPill_activity extends AppCompatActivity implements InsertPill_PresenterToView, View.OnClickListener{

    EditText et_DiseaseName;
    Button bt_ListAdd, bt_1time, bt_2time, bt_3time, bt_AlarmReciveFamily, bt_PillInsert;
    TextView tv_1h, tv_1m, tv_2h, tv_2m, tv_3h, tv_3m;
    TextView tv_dosagi;
    ListView lv_alarmFamily, lv_Pill;
    ImageView minus_count,plus_count;
    List<String> time, family_id, alarm_f_list, pill_list;
    List<Integer> pill_list_num = null;
    String h, m;
    TimePickerDialog dialog1,dialog2,dialog3;
    ArrayAdapter adapter,pillAdapter;
    Presenter_InsertPill presenter_insertPill;
    InsertPill_Item insertPill_item;
    Intent resultIntent;
    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertpill_self);

        resultIntent = new Intent();
        setResult(4000,resultIntent);

        et_DiseaseName = (EditText)findViewById(R.id.et_DiseaseName);
        bt_ListAdd = (Button)findViewById(R.id.bt_ListAdd);
        minus_count = (ImageView) findViewById(R.id.minus_count);
        plus_count = (ImageView) findViewById(R.id.plus_count);
        tv_dosagi = (TextView) findViewById(R.id.et_Dosagi);
        bt_1time = (Button) findViewById(R.id.bt_1time);
        bt_2time = (Button) findViewById(R.id.bt_2time);
        bt_3time = (Button) findViewById(R.id.bt_3time);
        tv_1h = (TextView) findViewById(R.id.tv_1_h);
        tv_1m = (TextView) findViewById(R.id.tv_1_m);
        tv_2h = (TextView) findViewById(R.id.tv_2_h);
        tv_2m = (TextView) findViewById(R.id.tv_2_m);
        tv_3h = (TextView) findViewById(R.id.tv_3_h);
        tv_3m = (TextView) findViewById(R.id.tv_3_m);
        bt_AlarmReciveFamily = (Button)findViewById(R.id.bt_AlarmReciveFamily);
        lv_alarmFamily = (ListView)findViewById(R.id.lv_alarmFamily);
        lv_Pill = (ListView)findViewById(R.id.lv_Pill);
        bt_PillInsert = (Button)findViewById(R.id.bt_PillInsert);

        presenter_insertPill = new Presenter_InsertPill(this);

        insertPill_item = new InsertPill_Item();

        family_id = new ArrayList<String>();
        alarm_f_list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarm_f_list);

        pill_list = new ArrayList<String>();
        pill_list_num = new ArrayList<>();
        pillAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pill_list);


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
                Intent intent = new Intent(getApplicationContext(), SearchPill_activity.class); // 다음 넘어갈 클래스 지정
                // startActivity(intent);
                startActivityForResult(intent, 2000);
            }
        });

        bt_AlarmReciveFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sel_AlarmRecive_activity.class); // 다음 넘어갈 클래스 지정
                // startActivity(intent);
                startActivityForResult(intent, 3000);
            }
        });

        lv_alarmFamily.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lv_alarmFamily.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        bt_PillInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_DiseaseName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "약 이름을 입력하세요", Toast.LENGTH_LONG).show();
                }else if(tv_dosagi.getText().toString().equals("0")){
                    Toast.makeText(getApplicationContext(), "복용횟수를 설정하세요", Toast.LENGTH_LONG).show();
                }else if(time.size() == 0){
                    Toast.makeText(getApplicationContext(), "시간을 입력하세요", Toast.LENGTH_LONG).show();
                }else if(alarm_f_list.size() == 0){
                    Toast.makeText(getApplicationContext(), "알림받을 가족을 선택하세요", Toast.LENGTH_LONG).show();
                }else {
                    Log.d("commName",et_DiseaseName.getText().toString());
                    Log.d("commDosagi",tv_dosagi.getText().toString());
                    Log.d("commTIme",time.get(0));
//                    Log.d("commElement",String.valueOf(pill_list_num.get(0)));
                    Log.d("commAlarm",family_id.get(0));
                    Log.d("commUserId",User_Id.getUser_Id());
                    insertPill_item.setName(et_DiseaseName.getText().toString());
                    insertPill_item.setDosagi(tv_dosagi.getText().toString());
                    insertPill_item.setTimeList(time);
                    insertPill_item.setElementList(pill_list_num);
                    insertPill_item.setAlarmList(family_id);
                    insertPill_item.setUserId(User_Id.getUser_Id());

                    presenter_insertPill.insertPill(insertPill_item);
                }

            }
        });
    }

    private TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() { //시간설정 다이얼로그 1
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
    private TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {//시간설정 다이얼로그 2
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
    private TimePickerDialog.OnTimeSetListener listener3 = new TimePickerDialog.OnTimeSetListener() {//시간설정 다이얼로그 3
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
                if(Integer.parseInt(tv_dosagi.getText().toString())<=0){
                    Toast.makeText(getApplicationContext(),"복욕횟수를 차감할 수 없습니다.",Toast.LENGTH_LONG).show();
                }
                else{ // 복용횟수 뻬기
                    int count = Integer.parseInt(tv_dosagi.getText().toString());
                    count--;
                    System.out.print(count);
                    tv_dosagi.setText(String.valueOf(count));
                    //e_dosagi.setText(et_dosagi.getText().toString().substring(0,1)+String.valueOf(count)+et_dosagi.getText().toString().substring(2));
                }
                break;
            case R.id.plus_count: //복용 횟수 플러
                int count = Integer.parseInt(tv_dosagi.getText().toString());
                count++;
                System.out.print(count);
                tv_dosagi.setText(String.valueOf(count));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == 3000){
                int size = 0;
                if (data.getStringExtra("status").equals("true")) {
                    for (int i = 0; i < Integer.parseInt(data.getStringExtra("list_size")); i++) {
                        if (data.getStringExtra("name" + i).equals("null")) {
                        } else {
                            family_id.add(data.getStringExtra("id" + i));
                            alarm_f_list.add(data.getStringExtra("name" + i));
                            Log.d("data1", data.getStringExtra("name" + i) + "/" + data.getStringExtra("id" + i));
                            size++;
                        } //알림 받을 가족 리스트뷰 생성
                    }
                    adapter.notifyDataSetChanged();
                    bt_AlarmReciveFamily.setEnabled(false);
                    ViewGroup.LayoutParams params = lv_alarmFamily.getLayoutParams();
                    params.height = 200 * size;
                    lv_alarmFamily.setLayoutParams(params);
                    lv_alarmFamily.setAdapter(adapter);
                }
            }
            if(requestCode == 2000){ //선택 한 약 리스트뷰 생성
                if(data.getStringExtra("status").equals("true")) {
                    pill_list.add(size, data.getStringExtra("result"));
                    pill_list_num.add(size, Integer.parseInt(data.getStringExtra("number")));
                    Log.d("pill_Num", String.valueOf(pill_list_num.get(size)));
                    size++;
                    pillAdapter.notifyDataSetChanged();
                    ViewGroup.LayoutParams params = lv_Pill.getLayoutParams();
                    params.height = 200 * size;
                    lv_Pill.setLayoutParams(params);
                    lv_Pill.setAdapter(pillAdapter);
                }
            }
        }
    }

    @Override
    public void onInsertResponse(boolean response) {
        if(response){
            Toast.makeText(getApplicationContext(), "약이 등록되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
