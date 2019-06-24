package yaksok.dodream.com.yaksok_refactoring.view.InsertPill.Sel_AlarmRecive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Sel_AlarmRecive.Presenter_Sel_AlarmRecive;

public class Sel_AlarmRecive_activity extends ApplicationBase implements Sel_AlarmRecive_PresenterToView {

    private Presenter_Sel_AlarmRecive presenter_alarmRecive;
    private List<String> familyList = new ArrayList<String>();
    private List<String> familyList_Id = new ArrayList<String>();
    ListView lv_sel_family;
    ArrayAdapter adapter;
    Intent resultIntent;
    Button bt_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_alarmrecive);

        resultIntent = new Intent();
        setResult(3000,resultIntent);

        resultIntent.putExtra("status","false");

        lv_sel_family = (ListView)findViewById(R.id.listview_family);
        bt_select = (Button)findViewById(R.id.bt_AlarmReciveFamily_Ok);

        presenter_alarmRecive = new Presenter_Sel_AlarmRecive(this);
        presenter_alarmRecive.getFamilyList();

        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = lv_sel_family.getCheckedItemPositions();
                Log.d("data+++","들어 왔는데..");
                int count = adapter.getCount();
                resultIntent.putExtra("status","true");
                resultIntent.putExtra("list_size",String.valueOf(count));
                Log.d("list_size",String.valueOf(count));
                for(int i=count-1; i>=0; i--) {
                    Log.d("dataNum",String.valueOf(i));
                    if (checkedItems.get(i)) {
                        resultIntent.putExtra("name"+i,familyList.get(i));
                        resultIntent.putExtra("id"+i,familyList_Id.get(i));
                        Log.d("data_name", familyList.get(i) + familyList_Id.get(i));
                    }
                    else{
                        resultIntent.putExtra("name"+i,"null");
                        resultIntent.putExtra("id"+i,"null");
                    }
                }
                finish();
            }
        });
    }



    @Override
    public void onFamilyResponce(boolean Responce) {
        if(Responce){
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, familyList);
            lv_sel_family.setAdapter(adapter);
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
