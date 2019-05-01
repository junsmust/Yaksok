package yaksok.dodream.com.yaksok_refactoring.view.InsertPill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.SearchPill.SearchPill_activity;

public class InsertPill_activity extends AppCompatActivity implements InsertPill_PresenterToView{

    EditText et_DiseaseName;
    Button bt_ListAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertpill_self);

        et_DiseaseName = (EditText)findViewById(R.id.et_DiseaseName);
        bt_ListAdd = (Button)findViewById(R.id.bt_ListAdd);

        bt_ListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchPill_activity.class));
            }
        });
    }
}
