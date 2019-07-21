package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;

public class Developer extends ApplicationBase{
    ListView lv1, lv2;
   /* private String[] name1 = {"박성빈","박한을","임지윤","서범준","이승준","한수정","윤예찬"};
    private String[] col1 = {"Columbia University in the City Of New York"+"\n"+"Computer Science and Mathematics",
            "성균관대학교 화학공학부",
            "홍익대학교 산업공학과",
            "강남대학교 소프트웨어응용학부",
            "숭실대학교 소프트웨어학부",
            "한양대학교 ERICA캠퍼스 산업경영공학과",
            "주식회사 스칼라데이터(SCALAR DATA Co., Ltd.) 대표이사"};*/
    private String[] name2 = {"박한을","서범준","윤성희","권재환","이재석","임명준","권보미"};
    private String[] col2 = {"성균관대학교 화학공학부",
            "강남대학교 소프트웨어응용학부",
            "강남대학교 컴퓨터미디어정보공학부",
            "강남대학교 컴퓨터공학과",
            "강남대학교 컴퓨터공학과",
            "강남대학교 컴퓨터공학과",
            "디자이너"};
   /* Team1_Adapter adapter1;*/
    Team1_Adapter adapter2;

    ImageView imageView;
    TextView tv_acton_name;
    FrameLayout fb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer);

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

        tv_acton_name.setText("개발자 정보");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view,layoutParams);

       /* lv1 = (ListView)findViewById(R.id.lv_developer1);*/
        lv2 = (ListView)findViewById(R.id.lv_developer2);

      /*  ArrayList<Item> arrayList1 = new ArrayList<Item>();*/
        ArrayList<Item> arrayList2 = new ArrayList<Item>();
       /* adapter1 = new Team1_Adapter();
*/
        adapter2 = new Team1_Adapter();
/*
        for(int i=0; i<name1.length; i++){
            adapter1.addItem(name1[i],col1[i]);
        }
        lv1.setAdapter(adapter1);

       *//* MyAdapter adapter1 = new MyAdapter(this, arrayList1);
        *//*

        ViewGroup.LayoutParams params = lv1.getLayoutParams();
        params.height = 300 * 7;
        lv1.setLayoutParams(params);
        lv1.setAdapter(adapter1);*/

        for(int i=0; i<name2.length; i++){
            adapter2.addItem(name2[i],col2[i]);
        }
        lv2.setAdapter(adapter2);

        /* MyAdapter adapter1 = new MyAdapter(this, arrayList1);
         */

        ViewGroup.LayoutParams params2 = lv2.getLayoutParams();
        params2.height = 300 * 7;
        lv2.setLayoutParams(params2);
        lv2.setAdapter(adapter2);


    }
}


