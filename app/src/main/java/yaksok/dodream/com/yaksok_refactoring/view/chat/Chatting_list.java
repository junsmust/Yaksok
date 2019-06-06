package yaksok.dodream.com.yaksok_refactoring.view.chat;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.chat.Chat_Presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;


public class Chatting_list extends AppCompatActivity implements I_chat_list{
    private ListView chat_list;
    private ArrayList<FamilyItem> familyItemss = new ArrayList<>();
    private FamilyFindAdapter adapter;
    private Chat_Presenter presenter;
    public static String user2_id;
    public static String user_id;
    public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_list);

        chat_list = (ListView)findViewById(R.id.family_connected_listview);

        presenter = new Chat_Presenter(this);

        presenter.setPreviousRegistered();


        adapter = new FamilyFindAdapter(this,familyItemss,R.layout.family_list_item);


        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index1 = ((FamilyItem)adapter.getItem(position)).getName().indexOf('(');
                int index2 = ((FamilyItem)adapter.getItem(position)).getName().indexOf(')');

                Log.e( "onItemClick:2 ", (((FamilyItem) adapter.getItem(position)).getName().substring(index1+1,index2)));
                String user_name = (((FamilyItem) adapter.getItem(position)).getName().substring(0,index1));
                user_id = (((FamilyItem) adapter.getItem(position)).getName().substring(index1+1,index2));
                user = User_Id.getUser_Id();

                Intent intent = new Intent(getApplicationContext(),Chat_Room.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("user_id",user_id);


                startActivity(intent);
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
        textView.setText("채팅");
        textView.setGravity(Gravity.CENTER);
//        textView.setGravity(Gravity.CENTER);
        actionBar.setTitle(textView.getText().toString());



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        actionBar.setCustomView(view,layoutParams);
    }

    @Override
    public void makeToastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(boolean response) {
        if(response){
            for(int i=0;i<familyItemss.size();i++){
                // Log.d("ffffff1"," "+familyItemss.size());

                adapter.addItem(familyItemss.get(i).getName());
                adapter.notifyDataSetChanged();
                chat_list.setAdapter(adapter);

            }
        }
    }

    @Override
    public void getArrayList(ArrayList<FamilyItem> familyItems) {
        this.familyItemss = familyItems;
    }

    @Override
    public void sendChatArrayList(ArrayList<SendMessageVO> albumList) {

    }

    @Override
    public void getSendVO(SendMessageVO sendVO) {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
