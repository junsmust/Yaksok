package yaksok.dodream.com.yaksok_refactoring.view.MyPill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.InsertPill_activity;

public class MyPill_activity extends AppCompatActivity implements MyPill_PresenterToView {

    private Presenter_MyPill presenter_myPill;
    ListView lv_MyPill;
    List<String> myPillList = new ArrayList<String>();;
    ArrayAdapter adapter;
    Button bt_Insert;
    public AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypill);

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

        lv_MyPill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog("약이름","10:50");
            }
        });

    }



    @Override
    public void onMyPillResponce(boolean MyPillResponse) {
        if(MyPillResponse){
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myPillList);
            lv_MyPill.setAdapter(adapter);
        }
    }

    @Override
    public void myPillList(List<String> pillList) {
        Log.d("test5",String.valueOf(pillList.size()));
        for(int i=0; i<pillList.size(); i++){
            myPillList.add(pillList.get(i));
            Log.d("test",pillList.get(i));
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

    public void showDialog(String name, String regidate){

        dialog.setTitle("약 등록 확인");
        dialog.setMessage("등록된 약 이름은  \n"+name+"\n"+regidate);
        dialog.setCancelable(false);



        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("삭제하기", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //삭제할 메서드 만들자
            }
        });
       AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }



}
