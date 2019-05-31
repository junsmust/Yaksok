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
import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;

public class MyPill_activity extends AppCompatActivity implements MyPill_PresenterToView {

    private Presenter_MyPill presenter_myPill;
    ListView lv_MyPill;
    List<String> myPillList = new ArrayList<String>();;
    ArrayAdapter adapter;
    Button bt_Insert;
    public AlertDialog.Builder dialog;
    MyPillVO mypill = null;



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
                showDialog(mypill.getResult().get(position).getName(),
                        mypill.getResult().get(position).getRegiDate().substring(0,10),
                        mypill.getResult().get(position).getMedicineNo());
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
    public void myPillList(MyPillVO myPillVO) {
        for(int i=0; i<myPillVO.getResult().size(); i++){
            myPillList.add(myPillVO.getResult().get(i).getName());
            Log.d("test",myPillVO.getResult().get(i).getName());

        }
        mypill = myPillVO;
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

    public void showDialog(String name, String regidate, final int pillNo){

        dialog.setTitle("약 등록 정보");
        dialog.setMessage(name+"\n"+regidate);
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
                presenter_myPill.myPillDelete(pillNo);
            }
        });
       AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }



}
