package yaksok.dodream.com.yaksok_refactoring.view.InsertPill.Sel_AlarmRecive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Sel_AlarmRecive.Presenter_Sel_AlarmRecive;

public class Sel_AlarmRecive_activity extends AppCompatActivity implements Sel_AlarmRecive_PresenterToView {

    private Presenter_Sel_AlarmRecive presenter_alarmRecive;
    private List<String> familyList = new ArrayList<String>();
    private List<String> familyList_Id = new ArrayList<String>();
    ListView lv_sel_family;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_alarmrecive);

        lv_sel_family = (ListView)findViewById(R.id.listview_family);

        presenter_alarmRecive = new Presenter_Sel_AlarmRecive(this);
        presenter_alarmRecive.getFamilyList();
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
