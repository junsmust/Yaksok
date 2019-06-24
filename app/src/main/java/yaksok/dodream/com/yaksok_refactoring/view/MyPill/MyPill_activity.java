package yaksok.dodream.com.yaksok_refactoring.view.MyPill;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.MyPill.MyPillItem;
import yaksok.dodream.com.yaksok_refactoring.Adapter.MyPill.MypillListAdapter;
import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.Dialog.Custom_pill_delete_Dialog;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.InsertPill_activity;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.Dialog.Delete_Dialog;
import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;

public class MyPill_activity extends ApplicationBase implements MyPill_PresenterToView,View.OnClickListener {

    private Presenter_MyPill presenter_myPill;
    ListView lv_MyPill;
    ImageView imageView;
    ArrayList<MyPillVO> myPillList = new ArrayList<MyPillVO>();;
    ArrayList<MyPillItem> myPillItems = new ArrayList<MyPillItem>();
    FrameLayout fb;
    MypillListAdapter adapter1;
    ArrayAdapter adapter;
    Button bt_Insert;
    TextView tv_acton_name;
    public AlertDialog.Builder dialog;
    MyPillVO mypill = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypill);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_under_line));

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.action_bar_develop, null);

        imageView = view.findViewById(R.id.back_layout_back_devel);
        tv_acton_name = view.findViewById(R.id.back_layout_name_delvel);
        fb = view.findViewById(R.id.fb_back_layout_back_devel);

        tv_acton_name.setText("나의 약");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view,layoutParams);

        dialog = new AlertDialog.Builder(this);



        lv_MyPill = (ListView)findViewById(R.id.lv_MyPill);
        bt_Insert = (Button)findViewById(R.id.bt_Insert);



        bt_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPillList.clear();
                Intent intent = new Intent(getApplicationContext(), InsertPill_activity.class);
                startActivityForResult(intent,4000);
            }
        });
        presenter_myPill = new Presenter_MyPill( this);

        final Context context = this;

        lv_MyPill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Delete_Dialog dialog1 = new Delete_Dialog(MyPill_activity.this);
                dialog1.setDialogListener(mypill.getResult().get(position).getName(),
                        mypill.getResult().get(position).getRegiDate().substring(0,10),
                        mypill.getResult().get(position).getMedicineNo(),new Delete_Dialog.CustomDialogListener() {
                    @Override
                    public void onPositiveClicked(boolean isOk) {
                        if(isOk){
                            Toast.makeText(MyPill_activity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            myPillList.clear();
                            presenter_myPill.getMyPill();
                        }
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                dialog1.show();



                /*Custom_pill_delete_Dialog dialog = new Custom_pill_delete_Dialog(context);
                dialog.callFunction(mypill.getResult().get(position).getName(),
                        mypill.getResult().get(position).getRegiDate().substring(0,10),
                        mypill.getResult().get(position).getMedicineNo());*/


                /*showDialog(mypill.getResult().get(position).getName(),
                        mypill.getResult().get(position).getRegiDate().substring(0,10),
                        mypill.getResult().get(position).getMedicineNo());*/
                        Log.d("처음 약 번호", String.valueOf(mypill.getResult().get(position).getMedicineNo()));
            }
        });

    }



    @Override
    public void onMyPillResponce(boolean MyPillResponse) {
        if(MyPillResponse){
            adapter1 = new MypillListAdapter();
            lv_MyPill.setAdapter(adapter1);
            for(int i=0;i<mypill.getResult().size();i++) {
                String family = "";
                for(int j=0; j<mypill.getResult().get(i).getFamilies().size(); j++){
                    family += mypill.getResult().get(i).getFamilies().get(j) + " ";
                }
                    /*for (int j = 0; j <= mypill.getResult().get(i).getFamilies().size(); j++) {
                        family += mypill.getResult().get(i).getFamilies().get(j) + ", ";
                    }*/
                adapter1.addItem(mypill.getResult().get(i).getName(), mypill.getResult().get(i).getDosagi(),
                        mypill.getResult().get(i).getTakingOfDayNum(), family);
            }
            adapter1.notifyDataSetChanged();
            //lv_MyPill.setAdapter(adapter);
        }
    }

    @Override
    public void myPillList(MyPillVO myPillVO) {
        myPillList.clear();
        mypill = myPillVO;
        myPillList.add(myPillVO);
    }

    @Override
    public void onMyPillDeleteRespoce(boolean MyPillResponse) {
        if(MyPillResponse){
            onResume();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 4000){
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter_myPill.getMyPill();
        Log.d("test1","true");
    }

    @Override
    public void onClick(View v) {

    }

    /*public void showDialog(String name, String regidate, final int pillNo){

        dialog.setTitle("약 등록 정보");
        dialog.setMessage("약 이름 :"+name+"\n"+"등록날짜 : "+regidate);
        dialog.setCancelable(false);



        dialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("삭제하기", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter_myPill.myPillDelete(pillNo);
                myPillList.clear();
                onMyPillResponce(true);
            }
        });
       AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }*/




}
