package yaksok.dodream.com.yaksok_refactoring.view.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.CalculateTime;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatItem;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.Chat_List_Model;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.chat.Chat_Model;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.chat.Chat_Presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageResultBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageService;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;

public class Chat_Room extends ApplicationBase implements I_chat_list{

    private Intent intent;
    public static String user2_name,user2_id,my_id;
    public static RecyclerView chat_recycler_list;
    Button bt_send;
    EditText ed_context;
    public static ChatAdapter chatAdapter;

    public static boolean iInTheChattingRoom;
    public static boolean msgStatus=true;

    public static String connectedName;

    private String user_context;
    MessageService messageService;
    public static String your_id;

    Retrofit retrofit;
    String u_id;
    String y_id;

    public SharedPreferences lastime_sharepfreference;
    public  SharedPreferences.Editor time_editor;

    SendMessageVO sendMessageVO;





    public static ArrayList<SendMessageVO> albumList = new ArrayList<>();
    public static LinearLayoutManager linearLayoutManager;
    private Chat_Presenter presenter,presenter2;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__room);

        Intent intent = new Intent(getIntent());
        user2_id = Chatting_list.user_id;

        Log.e("id1 ", User_Id.getUser_Id());
        Log.e("user2_id ",user2_id);




        lastime_sharepfreference = getSharedPreferences(user2_id,MODE_PRIVATE);
        time_editor = lastime_sharepfreference.edit();

        Log.e( "받을 사람 아이디 ",user2_id );
        connectedName = intent.getStringExtra("user_name");



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.chat2);
        actionBar.setTitle(connectedName);


        albumList = new ArrayList<SendMessageVO>();
        iInTheChattingRoom = true;

        linearLayoutManager = new LinearLayoutManager(this);
        initLayout();



        ed_context = (EditText)findViewById(R.id.user_context_edt);
        ed_context.setFocusable(true);
        user_context = ed_context.getText().toString();
        bt_send = (Button)findViewById(R.id.send_btn);



        your_id = user2_id;


        retrofit = new Retrofit.Builder()
                .baseUrl(messageService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageService = retrofit.create(MessageService.class);
        Collections.reverse(albumList);



        chatAdapter = new ChatAdapter(albumList,R.layout.chat_item);

        if(intent.getStringExtra("goBack")==null){
            linearLayoutManager.setStackFromEnd(true);
            getPreviouseConversation(User_Id.getUser_Id(),user2_id);

        }
        else if(intent.getStringExtra("goBack").equals("123")){
            u_id = intent.getStringExtra("user_id");
            y_id = intent.getStringExtra("your_id");

            if(intent.getStringExtra("send_user")!=null){
                connectedName = intent.getStringExtra("send_user");
            }

            Log.d("uuuuuu",u_id+"   "+y_id);
            getPreviouseConversation(u_id,y_id);

        }

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgStatus = true;
                String inTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
                String time = inTime.substring(0,2)+":"+inTime.substring(2,4);
                Log.d("time","!!!!!!!!!!!!!"+inTime);


                if(ed_context.getText().length()==0){
                    return;
                }



                final SendMessageVO sendVO = new SendMessageVO();
                sendVO.setGivingUser(User_Id.getUser_Id());
                sendVO.setContent(ed_context.getText().toString());
                sendVO.setReceivingUser(user2_id);
                //sendVO.setRegidate(time);


                Log.d("@@@@@@@@@@@"+"id",sendVO.getGivingUser()+"recive"+sendVO.getReceivingUser()+"context"+sendVO.getContent());
                Call<MessageResultBodyVO> call = messageService.sendAmeesage(sendVO);
                call.enqueue(new Callback<MessageResultBodyVO>() {
                    @Override
                    public void onResponse(Call<MessageResultBodyVO> call, Response<MessageResultBodyVO> response) {
                        MessageResultBodyVO messageBodyVO = response.body();
                        //201 : OK
                        //400: FCM error
                        //500 : Server Error
                        if(messageBodyVO.getStatus().equals("201")){
                            Toast.makeText(getApplicationContext(),"전송되었습니다.",Toast.LENGTH_SHORT).show();
                            sendVO.setRegidate(messageBodyVO.getRegiDate().substring(11,16));
                            albumList.add(sendVO);
                            chat_recycler_list.setAdapter(chatAdapter);
                            chat_recycler_list.setLayoutManager(linearLayoutManager);
                            ed_context.setText("");
                            linearLayoutManager.setStackFromEnd(true);
                            //Chatting_list.presenter.setPreviousRegistered();


                        }
                        else if(messageBodyVO.getStatus().equals("400")){
                            Toast.makeText(getApplicationContext(),"FCM Error",Toast.LENGTH_SHORT).show();
                        } else if (messageBodyVO.getStatus().equals("500")) {
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResultBodyVO> call, Throwable t) {

                    }
                });

            }
        });


    }






    private void initLayout() {
        chat_recycler_list = (RecyclerView)findViewById(R.id.chat_recycler_list);


    }

    @SuppressLint("LongLogTag")
    public void getPreviouseConversation(String u_id, String y_id) {

        Log.e( "getPreviouseConversation: ",u_id+" "+y_id );


        Log.e("onResponse: ",u_id+y_id+"  dadddaad" );
        Call<MessageBodyVO> call = messageService.getTheChatting(u_id,y_id,10,0);
        call.enqueue(new Callback<MessageBodyVO>() {
            @Override
            public void onResponse(Call<MessageBodyVO> call, Response<MessageBodyVO> response) {
                MessageBodyVO bodyVO = response.body();

                Log.e( "ostatus: ", bodyVO.getStatus());
                String name ="" ;
                //200 : OK
                //204 : 값없음(null반환)
                //500 : Server Error
                assert bodyVO != null;
                // Log.d("@@@@@@@@@@@@@@@@@@",bodyVO.getResult().get(0).getContent()+"id"+bodyVO.getResult().get(0).getGivingUser()+"id2"+bodyVO.getResult().get(0).getReceivingUser());
                //Toast.makeText(getApplicationContext(),"body"+bodyVO.getStatus()+"result"+bodyVO.getResult().size(),Toast.LENGTH_SHORT).show();

                if(bodyVO.getStatus().equals("200")){
                    for(int i = 0; i < bodyVO.getResult().size(); i++){
                        Log.d("실행","실행 됨");

                        Log.e( "onResponse: ", bodyVO.getResult().get(i).getContent());
                        sendMessageVO = new SendMessageVO();
                        sendMessageVO.setGivingUser(bodyVO.getResult().get(i).getGivingUser());
                        sendMessageVO.setContent(bodyVO.getResult().get(i).getContent());
                        sendMessageVO.setReceivingUser(bodyVO.getResult().get(i).getReceivingUser());
                        sendMessageVO.setRegidate(bodyVO.getResult().get(i).getRegiDate().substring(11,16));
                        sendMessageVO.setName(connectedName);




                        //Collections.reverse(albumList);//역순으로
                        Log.d("list_test", sendMessageVO.getContent() + "," + i);
                        albumList.add(sendMessageVO);
                        Log.d("album_test", albumList.get(i).getContent());
                        name = bodyVO.getResult().get(i).getGivingUser();


                    }

                    long now = System.currentTimeMillis();
                    Date date = new Date(now);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                    String getTime = sdf.format(date);

                    Log.e( "right now time ", getTime);
                    //sendMessageVO.setRegidate(bodyVO.getResult().get(bodyVO.getResult().size()-1).getRegiDate().substring(11,16));
                    Log.e( "registed now time ", bodyVO.getResult().get(bodyVO.getResult().size()-1).getRegiDate());



                    Collections.reverse(albumList);
                    chat_recycler_list.setAdapter(new ChatAdapter(albumList,R.layout.chat_item));
                    linearLayoutManager.setStackFromEnd(true);
                    chat_recycler_list.setLayoutManager(linearLayoutManager);
                    //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    Log.d("name111","aaa"+name);
                }else if(bodyVO.getStatus().equals("204")){
                    ed_context.setFocusable(true);
                }
                else if(bodyVO.getStatus().equals("500")){
                    Toast.makeText(getApplicationContext(),"서버 오류입니다.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MessageBodyVO> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        iInTheChattingRoom = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        iInTheChattingRoom = false;
        finish();
        //Chatting_list.presenter.setPreviousRegistered();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iInTheChattingRoom = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iInTheChattingRoom = false;
        finish();


        Log.e( "onDestroy: ","ccccc1" );

    }
    @Override
    protected void onNewIntent(Intent intent) {
        System.out.println("newIntent");
        if (intent != null) {
            if(!intent.getStringExtra("from").equals("")) {
                Toast.makeText(getApplicationContext(), intent.getStringExtra("from"), Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(getApplicationContext(),"NULLLLL",Toast.LENGTH_LONG).show();
            }

        }
        super.onNewIntent(intent);
    }






    @Override
    protected void onStart() {
        super.onStart();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));

        View view = LayoutInflater.from(this).inflate(R.layout.chatting_green_actinbar,null);
        ImageView imageView = view.findViewById(R.id.back_layout_cancel);
        TextView textView = view.findViewById(R.id.title_txt_pill);
        FrameLayout frameLayout = view.findViewById(R.id.fb_back_layout_cancel);

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
                finish();

            }
        });
        textView.setText(connectedName);
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

    }

    @Override
    public void getArrayList(ArrayList<ChatItem> familyItems) {

    }

    @Override
    public void sendChatArrayList(ArrayList<SendMessageVO> albumList) {

    }

    @Override
    public void getSendVO(SendMessageVO sendVO) {

    }

    @Override
    public void getId(String s) {

    }

    @Override
    public void getArrayIds(ArrayList<String> ids) {

    }

    @Override
    public void sendChatList2(ArrayList<Chat_List_Model> chat_list_model) {

    }
}

