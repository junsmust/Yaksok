package yaksok.dodream.com.yaksok_refactoring.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_activity;

public class MainPage_activity extends AppCompatActivity {

    private Button bt_InsertPill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_activity);

        bt_InsertPill = (Button)findViewById(R.id.bt_InsertPill);
        bt_InsertPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPill_activity.class));
            }
        });
<<<<<<< HEAD

=======
>>>>>>> f67d051b19616e1a9a14240228862e867ec0dcfb
    }
}
