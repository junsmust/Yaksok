package yaksok.dodream.com.yaksok_refactoring.view.MyPill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    List<String> myPillList = new ArrayList<String>();
    ArrayAdapter adapter;

    Button bt_Insert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypill);

        lv_MyPill = (ListView)findViewById(R.id.lv_MyPill);
        bt_Insert = (Button)findViewById(R.id.bt_Insert);

        bt_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InsertPill_activity.class));
            }
        });

        presenter_myPill = new Presenter_MyPill( this);
        presenter_myPill.getMyPill();


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
        for(int i=0; i<pillList.size(); i++){
            myPillList.add(pillList.get(i));
        }
    }

}
