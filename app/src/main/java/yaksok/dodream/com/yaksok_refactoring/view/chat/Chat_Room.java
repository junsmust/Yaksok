package yaksok.dodream.com.yaksok_refactoring.view.chat;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.chat.Chat_Presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;

public class Chat_Room extends AppCompatActivity implements I_chat_list{

    private Intent intent;
    String user_name,user_id;
    RecyclerView chat_recycler_list;
    Button bt_send;
    EditText ed_context;

    public static ArrayList<SendMessageVO> albumList;
    public static LinearLayoutManager linearLayoutManager;
    private Chat_Presenter presenter;
    public static boolean iInTheChattingRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__room);

        intent = new Intent(getIntent());

        user_name = intent.getStringExtra("user_name");
        user_id = intent.getStringExtra("user_id");

        Log.d("user_id",user_id);
       // presenter.setChatUserInfo(user_name,user_id);

        presenter = new Chat_Presenter(this);
        chat_recycler_list = (RecyclerView)findViewById(R.id.chat_recycler_list);
        bt_send = (Button)findViewById(R.id.send_btn);
        ed_context = (EditText)findViewById(R.id.user_context_edt);


        linearLayoutManager = new LinearLayoutManager(this);






        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed_context.getText().toString().equals("")){
                    presenter.sendMessage(ed_context.getText().toString(),user_id);
                }
            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = LayoutInflater.from(this).inflate(R.layout.chattingactionbar,null);
        ImageView imageView = view.findViewById(R.id.back_layout_imv);
        TextView textView = view.findViewById(R.id.title_txt);

        FrameLayout frameLayout = view.findViewById(R.id.frame_layout);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent resultIntent = new Intent();
        setResult(4000,resultIntent);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);
//                startActivityForResult(intent,7777);
                finish();
            }
        });
        textView.setText(user_name);
        textView.setGravity(Gravity.CENTER);
        actionBar.setTitle(textView.getText().toString());



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        actionBar.setCustomView(view,layoutParams);
    }

    @Override
    public void makeToastMessage(String message) {

    }

    @Override
    public void onResponse(boolean response) {

        if(response){




        }
    }

    @Override
    public void getArrayList(ArrayList<FamilyItem> familyItems) {

    }

    @Override
    public void sendChatArrayList(ArrayList<SendMessageVO> albumList) {

    }
}
