package yaksok.dodream.com.yaksok_refactoring.view.chat;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatItem;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.Chat_List_Model;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.Chat_list_adater;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.chat.Chat_Presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageService;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;


public class Chatting_list extends ApplicationBase implements I_chat_list{
    private ListView chat_list;
    private ArrayList<ChatItem> familyItemss = new ArrayList<>();
    private Chat_list_adater adapter;
    public static Chat_Presenter presenter;
    public static String user2_id;
    public static String user_id;
    public static String user;
    MessageService messageService;
    String id;
    public ArrayList<String> ids = new ArrayList<>();
    public ArrayList<Chat_List_Model> chat_list_model = new ArrayList<>();
    SendMessageVO sendMessageVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_list);

        chat_list = (ListView)findViewById(R.id.family_connected_listview);

        presenter = new Chat_Presenter(this);




        adapter = new Chat_list_adater(this,familyItemss,R.layout.chat_list_item);


        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                presenter.sendIdIndex(chat_list_model.get(position).getId());

               /* Log.e( "onItemClick:2 ", (((FamilyItem) adapter.getItem(position)).getName().substring(index1+1,index2)));
                String user_name = (((FamilyItem) adapter.getItem(position)).getName().substring(0,index1));
                user_id = (((FamilyItem) adapter.getItem(position)).getName().substring(index1+1,index2));*/
                user = User_Id.getUser_Id();
                String user_name = (((ChatItem) adapter.getItem(position)).getName());
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
        textView.setText("채팅방 목록");
        textView.setGravity(Gravity.CENTER);
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
            Log.e( "onResponse: ", chat_list_model.size()+" ");
            for(int i=0;i<chat_list_model.size();i++){
                // Log.d("ffffff1"," "+familyItemss.size());
               // Log.e( "familyitemssize",familyItemss.size()+"   "+familyItemss.get(i).getFirst_name()+ familyItemss.get(i).getName()+familyItemss.get(i).last_message+familyItemss.get(i).last_messge_time);
                Log.e( "test","i "+i+  "\n"+"id :"+chat_list_model.get(i).getId()+" \n"+"name :"+chat_list_model.get(i).getName()+"\n"+"lastname : "+chat_list_model.get(i).getLastName()+"\n"+"message :"+ chat_list_model.get(i).getLastMessage()+"\n"+"messagetime :"+
                        chat_list_model.get(i).getLastTime()+"\n");

                //Log.e( "test", "\n"+"id :"+chat_list_model+" \n"+"name :"+name_list+"\n"+"lastname : "+last_nam_list+"\n"+"message :"+ last_message_list+"\n"+"messagetime :"+last_message_time_list+"\n");
                adapter.addItem(chat_list_model.get(i).getLastName(),chat_list_model.get(i).getName(),chat_list_model.get(i).getLastMessage(),chat_list_model.get(i).getLastTime());
                adapter.notifyDataSetChanged();
                chat_list.setAdapter(adapter);

            }
        }
    }

    @Override
    public void getArrayList(ArrayList<ChatItem> familyItems) {
        this.familyItemss = familyItems;
    }

    @Override
    public void sendChatArrayList(ArrayList<SendMessageVO> albumList) {

    }

    @Override
    public void getSendVO(SendMessageVO sendVO) {

    }

    @Override
    public void getId(String s) {
        user_id = s;
    }

    @Override
    public void getArrayIds(ArrayList<String> ids) {
        this.ids.addAll(ids);
        for(int i = 0; i < ids.size(); i++){
            Log.e( "getArrayIds: ", ids.get(i));
        }

    }

    @Override
    public void sendChatList2(ArrayList<Chat_List_Model> chat_list_model) {
        this.chat_list_model = chat_list_model;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setPreviousRegistered();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //presenter.setPreviousRegistered();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.chat_list_model.clear();

        Log.e( "onDestroy","onDestroy@@@@" );
    }
    public void setUpdate(){
        presenter.setPreviousRegistered();
    }

}

