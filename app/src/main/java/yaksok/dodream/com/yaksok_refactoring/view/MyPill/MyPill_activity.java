package yaksok.dodream.com.yaksok_refactoring.view.MyPill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;

public class MyPill_activity extends AppCompatActivity implements MyPill_PresenterToView {

    private Presenter_MyPill presenter_myPill;
    ListView lv_MyPill;
    List<String> myPillList = new ArrayList<String>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypill);

        lv_MyPill = (ListView)findViewById(R.id.lv_MyPill);

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
