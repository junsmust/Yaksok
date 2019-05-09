package yaksok.dodream.com.yaksok_refactoring.view.InsertPill.SearchPill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.SearchPill.PillSearchItem;
import yaksok.dodream.com.yaksok_refactoring.Adapter.SearchPill.SearchListAdapter;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.SearchPill.Presenter_SearchPill;

public class SearchPill_activity extends AppCompatActivity implements SearchPill_PresenterToView{

    private Presenter_SearchPill presenter_searchPill;
    EditText et_PillSearch;
    Button bt_PillSearch;
    ListView lv_SearchList;
    ArrayList<PillSearchItem> searchItems = new ArrayList<PillSearchItem>();
    SearchListAdapter adapter;
    Intent resultIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpill);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        resultIntent = new Intent();
        setResult(2000,resultIntent);

        resultIntent.putExtra("status","false");

        presenter_searchPill = new Presenter_SearchPill(this);

        et_PillSearch = (EditText)findViewById(R.id.et_PillSearch);
        bt_PillSearch = (Button)findViewById(R.id.bt_PillSearch);
        lv_SearchList = (ListView)findViewById(R.id.lv_SearchList);

        adapter = new SearchListAdapter();
        lv_SearchList.setAdapter(adapter);

        bt_PillSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_PillSearch.getText().toString().equals("")) {
                    Log.d("dosagiName",et_PillSearch.getText().toString());
                    presenter_searchPill.searchpill(et_PillSearch.getText().toString());
                }
            }
        });


        lv_SearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.d("sta",String.valueOf(i));
                builder.setTitle("알림");
                builder.setMessage("약을 등록하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("status","true");
                                resultIntent.putExtra("result", searchItems.get(i).getName());
                                resultIntent.putExtra("number", String.valueOf(searchItems.get(i).getMedicineNO()));
                                setResult(RESULT_OK,resultIntent);
                                Log.d("zzzz",searchItems.get(i).getName() + String.valueOf(searchItems.get(i).getMedicineNO()));
                                finish();
                            }
                        });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });

    }

    @Override
    public void getPillList(ArrayList<PillSearchItem> arrayList) {
        searchItems = arrayList;
        Log.d("약",searchItems.get(0).getElement());
    }

    @Override
    public void onSearchResponse(boolean SearchResponse) {
        if(SearchResponse){
            for(int i=0;i<searchItems.size();i++){
                adapter.addItem(searchItems.get(i).getMedicineNO(),searchItems.get(i).getName(),searchItems.get(i).getElement());
                adapter.notifyDataSetChanged();
            }
        }
    }
}
