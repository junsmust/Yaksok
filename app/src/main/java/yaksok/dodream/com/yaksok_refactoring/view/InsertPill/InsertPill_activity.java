package yaksok.dodream.com.yaksok_refactoring.view.InsertPill;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.C_Dialog;
import yaksok.dodream.com.yaksok_refactoring.CustomChoiceListViewAdapter;
import yaksok.dodream.com.yaksok_refactoring.CustomDialog;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Presenter_InsertPill;
import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public class InsertPill_activity extends ApplicationBase implements InsertPill_PresenterToView, View.OnClickListener{

    Button time1,time2,time3,bt_slidcalcel;
    EditText et_name;
    CheckBox checkBox;
    Spinner spinner;
    TextView tv_day_time,tv_dosagi,tv_IP_family;
    ArrayList<String> sp_array;
    ArrayAdapter<String> sp_arrayAdapter;
    TimePickerDialog dialog1,dialog2,dialog3;
    FrameLayout fb1, fb2;
    String h1,m1,h2,m2,h3,m3;
    String family_names = "";
    String day1,day2,day3;
    ImageView minus_count,plus_count,imageView,imageView1,time_m,time_p;
    RelativeLayout rb_family;
    SlidingDrawer slidingDrawer;
    Presenter_InsertPill presenter_insertPill;
    InsertPill_Item insertPill_item;
    List<String> time, family_id, alarm_f_list;
    Intent resultIntent;
    public  AlertDialog.Builder dialog;
    private List<String> familyList = new ArrayList<String>();
    private List<String> familyList_Id = new ArrayList<String>();
    ListView lv_sel_family;
    ArrayAdapter adapter;
    CustomChoiceListViewAdapter adapter1;
    int size = 0;
    String time_1,time_2,time_3;
    int status = 1;
    boolean slid_Satuts=false;
    C_Dialog customDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.insertpill_bar, null);

        imageView = view.findViewById(R.id.back_layout_cancel);
        imageView1 = view.findViewById(R.id.back_layout_check);
        fb1 = view.findViewById(R.id.fb_back_layout_cancel);
        fb2 = view.findViewById(R.id.fb_back_layout_check);

        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view,layoutParams);

        slidingDrawer = (SlidingDrawer)findViewById(R.id.drawer);

        resultIntent = new Intent();
        setResult(4000,resultIntent);

        presenter_insertPill = new Presenter_InsertPill(this);
        insertPill_item = new InsertPill_Item();

        presenter_insertPill.getFamilyList();

        family_id = new ArrayList<String>();

        alarm_f_list = new ArrayList<String>();
        time =  new ArrayList<>();

        time1 = (Button)findViewById(R.id.bt_IP_time1);
        time2 = (Button)findViewById(R.id.bt_IP_time2);
        time3 = (Button)findViewById(R.id.bt_IP_time3);
        //spinner = (Spinner)findViewById(R.id.sp_IP_day_time);
        tv_day_time = (TextView)findViewById(R.id.tv_IP_day_time);
        minus_count = (ImageView) findViewById(R.id.iv_IP_m);
        plus_count = (ImageView) findViewById(R.id.iv_IP_p);
        time_m = (ImageView)findViewById(R.id.iv_IP_time_m);
        time_p = (ImageView)findViewById(R.id.iv_IP_time_p);
        tv_dosagi = (TextView)findViewById(R.id.tv_IP_dosagi);
        rb_family = (RelativeLayout)findViewById(R.id.rb_IP_family);
        bt_slidcalcel = (Button)findViewById(R.id.bt_sliding_cancel);
        et_name = (EditText)findViewById(R.id.et_IP_name);
        lv_sel_family = (ListView)findViewById(R.id.iv_sliding_family);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);
        tv_IP_family = (TextView)findViewById(R.id.tv_IP_family);

        customDialog = new C_Dialog(this);

        et_name.setPaintFlags(et_name.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        lv_sel_family.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                family_names += familyList.get(position);
            }
        });


        bt_slidcalcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slid_Satuts = false;
                SparseBooleanArray checkedItems = lv_sel_family.getCheckedItemPositions();
                if(checkedItems.size()>0) {
                    family_names = " ";
                    family_id.clear();
                    int count = adapter1.getCount();
                    for (int i = 0; i < count; i++) {
                        if (checkedItems.get(i)) {
                            family_names += familyList.get(i);
                            family_id.add(familyList_Id.get(i));
                        }
                    }
                    tv_IP_family.setText(family_names);
                }
                slidingDrawer.animateClose();
            }
        });

        rb_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!slid_Satuts) {
                    slid_Satuts = true;
                    slidingDrawer.animateOpen();
                }
            }
        });

        minus_count.setOnClickListener(this);
        plus_count.setOnClickListener(this);
        time_m.setOnClickListener(this);
        time_p.setOnClickListener(this);

        sp_array = new ArrayList<>();
        sp_array.add("1번");
        sp_array.add("2번");
        sp_array.add("3번");

        sp_arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                sp_array);

//        spinner.setAdapter(sp_arrayAdapter);

        time2.setVisibility(View.INVISIBLE);
        time3.setVisibility(View.INVISIBLE);

       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_day_time.setText(String.valueOf(position+1));
                if(position == 0){
                    Log.d("visi","1번");
                    status = 1;
                    time1.setVisibility(View.VISIBLE);
                    time2.setVisibility(View.INVISIBLE);
                    time3.setVisibility(View.INVISIBLE);
                }
                else if(position == 1){
                    status = 2;
                    time1.setVisibility(View.VISIBLE);
                    time2.setVisibility(View.VISIBLE);
                    time3.setVisibility(View.INVISIBLE);
                }
                else{
                    status = 3;
                    time1.setVisibility(View.VISIBLE);
                    time2.setVisibility(View.VISIBLE);
                    time3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        dialog1 = new TimePickerDialog(this, listener1, 00, 00, true);
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.show();
            }
        });
        dialog2 = new TimePickerDialog(this, listener2, 00, 00, true);
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.show();
            }
        });
        dialog3 = new TimePickerDialog(this, listener3, 00, 00, true);
        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.show();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "약 이름을 입력하세요", Toast.LENGTH_LONG).show();
                }else if(tv_dosagi.getText().toString().equals("0")){
                    Toast.makeText(getApplicationContext(), "복용횟수를 설정하세요", Toast.LENGTH_LONG).show();
                }else if(time1.getText().toString().equals("시간설정1")){
                    Toast.makeText(getApplicationContext(), "시간을 입력하세요", Toast.LENGTH_LONG).show();
                }else if(time2.getText().toString().equals("시간설정2")&&tv_day_time.getText().toString().equals("2")){
                    Toast.makeText(getApplicationContext(), "시간을 입력하세요", Toast.LENGTH_LONG).show();
                }else if(time3.getText().toString().equals("시간설정3")&&tv_day_time.getText().toString().equals("3")){
                    Toast.makeText(getApplicationContext(), "시간을 입력하세요", Toast.LENGTH_LONG).show();
                }
                else {
                    for(int i=0; i<status+1; i++){
                        if(i == 1){
                            time.add(time_1);
                        }
                        if(i == 2){
                            time.add(time_2);
                        }
                        if(i == 3){
                            time.add(time_3);
                        }
                    }
                    Log.d("commName",et_name.getText().toString());
                    Log.d("commDosagi",tv_dosagi.getText().toString());
                    Log.d("commTIme",time.get(0));
//                    Log.d("commElement",String.valueOf(pill_list_num.get(0)));
                    //Log.d("commAlarm",family_id.get(0));
                    Log.d("commUserId", User_Id.getUser_Id());
                    insertPill_item.setName(et_name.getText().toString());
                    insertPill_item.setDosagi(tv_dosagi.getText().toString());
                    insertPill_item.setTimeList(time);
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
            if(hourOfDay < 10){
                h1 = "0" + String.valueOf(hourOfDay);
            }
            else{
                h1 = String.valueOf(hourOfDay);
            }

            if(minute < 10){
                m1 = "0" + String.valueOf(minute);
            }
            else{
                m1 = String.valueOf(minute);
            }
            if(hourOfDay < 12){
                day1 = "오전";
            }
            else{
                if(hourOfDay == 12){
                    h1 = String.valueOf(12);
                }
                else {
                    h1 = String.valueOf(Integer.parseInt(h1) - 12);
                    if(h1.length() < 2){
                        h1 = "0" + h1;
                    }
                }
                day1 = "오후";
            }
            time1.setText(day1+" "+h1 + ":" + m1);
            if(String.valueOf(hourOfDay).length()<2) {
                time_1 = "0"+String.valueOf(hourOfDay) + m1;
            }else{
                time_1 = String.valueOf(hourOfDay) + m1;
            }
            Log.d("time1",time_1);
        }
    };
    private TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {//시간설정 다이얼로그 2
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            if(hourOfDay < 10){
                h2= "0" + String.valueOf(hourOfDay);
            }
            else{
                h2 = String.valueOf(hourOfDay);
            }

            if(minute < 10){
                m2 = "0" + String.valueOf(minute);
            }
            else{
                m2 = String.valueOf(minute);
            }
            if(hourOfDay < 12){
                day2 = "오전";
            }
            else{
                if(hourOfDay == 12){
                    h2 = String.valueOf(12);
                }
                else {
                    h2 = String.valueOf(Integer.parseInt(h2) - 12);
                    if(h2.length() < 2){
                        h2 = "0" + h2;
                    }
                }
                day2 = "오후";
            }
            time2.setText(day2+" "+h2 + ":" + m2);
            if(String.valueOf(hourOfDay).length()<2){
                time_2 = "0"+String.valueOf(hourOfDay)+m2;
            }
            else {
                time_2 = String.valueOf(hourOfDay) + m2;
            }
        }
    };
    private TimePickerDialog.OnTimeSetListener listener3 = new TimePickerDialog.OnTimeSetListener() {//시간설정 다이얼로그 3
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            if(hourOfDay < 10){
                h3 = "0" + String.valueOf(hourOfDay);
            }
            else{
                h3 = String.valueOf(hourOfDay);
            }

            if(minute < 10){
                m3 = "0" + String.valueOf(minute);
            }
            else{
                m3 = String.valueOf(minute);
            }
            if(hourOfDay < 12){
                day3 = "오전";
            }
            else{
                if(hourOfDay == 12){
                    h3 = String.valueOf(12);
                }
                else {
                    h3 = String.valueOf(Integer.parseInt(h3) - 12);
                    if(h3.length() < 2){
                        h3 = "0" + h3;
                    }
                }
                day3 = "오후";
            }
            time3.setText(day3+" "+h3 + ":" + m3);
            if(String.valueOf(hourOfDay).length()<2) {
                time_3 = "0"+String.valueOf(hourOfDay) + m3;
            }else{
                time_3 = String.valueOf(hourOfDay) + m3;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_IP_m:
                if(Integer.parseInt(tv_dosagi.getText().toString())<=0){
                    Toast.makeText(getApplicationContext(),"복용횟수를 차감할 수 없습니다.",Toast.LENGTH_LONG).show();
                }
                else{ // 복용횟수 뻬기
                    int count = Integer.parseInt(tv_dosagi.getText().toString());
                    count--;
                    tv_dosagi.setText(String.valueOf(count));
                    //e_dosagi.setText(et_dosagi.getText().toString().substring(0,1)+String.valueOf(count)+et_dosagi.getText().toString().substring(2));
                }
                break;
            case R.id.iv_IP_p: //복용시기 횟수 플러
                int count = Integer.parseInt(tv_dosagi.getText().toString());
                count++;
                tv_dosagi.setText(String.valueOf(count));
                break;
            case R.id.iv_IP_time_m:
                if(Integer.parseInt(tv_day_time.getText().toString())<=1){

                }
                else{ // 복용시기 뻬기
                    int count_t = Integer.parseInt(tv_day_time.getText().toString());
                    count_t--;
                    if(count_t == 1){
                        Log.d("visi","1번");
                        status = 1;
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.INVISIBLE);
                        time3.setVisibility(View.INVISIBLE);
                    }
                    else if(count_t == 2){
                        status = 2;
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.VISIBLE);
                        time3.setVisibility(View.INVISIBLE);
                    }
                    else{
                        status = 3;
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.VISIBLE);
                        time3.setVisibility(View.VISIBLE);
                    }
                    tv_day_time.setText(String.valueOf(count_t));
                    break;
                    //e_dosagi.setText(et_dosagi.getText().toString().substring(0,1)+String.valueOf(count)+et_dosagi.getText().toString().substring(2));
                }
            case R.id.iv_IP_time_p:
                if(Integer.parseInt(tv_day_time.getText().toString())>=3){

                }
                else {
                    int count_t = Integer.parseInt(tv_day_time.getText().toString());
                    count_t++;
                    if (count_t == 1) {
                        Log.d("visi", "1번");
                        status = 1;
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.INVISIBLE);
                        time3.setVisibility(View.INVISIBLE);
                    } else if (count_t == 2) {
                        status = 2;
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.VISIBLE);
                        time3.setVisibility(View.INVISIBLE);
                    } else {
                        status = 3;
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.VISIBLE);
                        time3.setVisibility(View.VISIBLE);
                    }

                    tv_day_time.setText(String.valueOf(count_t));
                    break;
                }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;

    }*/

    @Override
    public void onInsertResponse(boolean response) {
        if(response){
            makeDialog();
        }
    }

    private void makeDialog(){


        customDialog.text_tv.setText("약이 등록되었습니다.");

        customDialog.show();


        customDialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public void onFamilyResponce(boolean Responce) {
        if(Responce){
            adapter1 = new CustomChoiceListViewAdapter();
            //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, familyList);
            for(int i=0; i<familyList.size();i++){
                adapter1.addItem(familyList.get(i).substring(0,1),familyList.get(i));
                size++;
            }
            lv_sel_family.setAdapter(adapter1);
           /* ViewGroup.LayoutParams params = lv_sel_family.getLayoutParams();
            params.height = 200 * size;
            lv_sel_family.setLayoutParams(params);
            lv_sel_family.setAdapter(adapter1);*/
        }
    }

    @Override
    public void myFamilyList(List<String> myFamilyList, List<String> myFamily_Id) {
        for(int i=0; i<myFamilyList.size();i++){
            familyList.add(myFamilyList.get(i));
            familyList_Id.add(myFamily_Id.get(i));
        }
    }

}
