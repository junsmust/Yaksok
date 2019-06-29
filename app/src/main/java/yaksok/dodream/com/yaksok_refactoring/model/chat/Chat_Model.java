package yaksok.dodream.com.yaksok_refactoring.model.chat;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.CalculateTime;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatItem;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.Chat_List_Model;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.model.user.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.chat.Chat_Presenter;
import yaksok.dodream.com.yaksok_refactoring.view.chat.Chat_Room;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.Connected_Family;
import yaksok.dodream.com.yaksok_refactoring.vo.FcmTokenVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageResultBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageService;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Chat_Model implements I_chat_model {
    private Chat_Presenter chat_presenter;
    private Retrofit retrofit;
    UserService userService;
    ArrayList<ChatItem> familyItems = new ArrayList<>();
    MessageService messageService,messageService2;
    ArrayList<SendMessageVO> albumList = new ArrayList<>();
    ArrayList<String> id_list = new ArrayList<>();
   /* ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> last_nam_list = new ArrayList<>();
    ArrayList<String> last_message_list = new ArrayList<>();
    ArrayList<String> last_message_time_list = new ArrayList<>();
*/

   String name_list,last_nam_list,last_message_list,last_message_time_list;

   ArrayList<Chat_List_Model> chat_list_models = new ArrayList<>();
    Chat_List_Model chat_list_model;


    ChatItem familyItem = new ChatItem();
    String message;

    public ChatItem getFamilyItem() {
        return familyItem;
    }

    public void setFamilyItem(ChatItem familyItem) {
        this.familyItem = familyItem;
    }

    private int index = 0;

    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl(messageService.API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public Chat_Model() {
    }

    public Chat_Model(Chat_Presenter chat_presenter) {
        this.chat_presenter = chat_presenter;

    }

    @Override
    public void setPreviousRegistered() {

        Log.e("setPreviousRegistered: ",chat_list_models.size()+"   " );
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);


        Call<Connected_Family> findFamilyVOCall = userService.getConnectedFamilyInfo(User_Id.getUser_Id());
        findFamilyVOCall.enqueue(new Callback<Connected_Family>() {
            @Override
            public void onResponse(Call<Connected_Family> call, Response<Connected_Family> response) {
                final Connected_Family findFamilyVO = response.body();

                Log.e("onResponse:55555 ", findFamilyVO.getStatus());

                if (findFamilyVO.getStatus().equals("200")) {

                    familyItem.setIds(id_list);


                    for (int i = 0; i < findFamilyVO.getResult().size(); i++) {



                        id_list.add(findFamilyVO.getResult().get(i).getUserId());



                        final String id = findFamilyVO.getResult().get(i).getUserId();
                        String last_name,last_message,last_time;
                        final int index = i;

                        messageService2 = retrofit2.create(MessageService.class);
                        Call<MessageBodyVO> call2 = messageService2.getTheChatting(User_Id.getUser_Id(),id, 1, 0);

                        call2.enqueue(new Callback<MessageBodyVO>() {
                            @Override
                            public void onResponse(Call<MessageBodyVO> call, Response<MessageBodyVO> response) {
                                MessageBodyVO bodyVO = response.body();
                                String name = "";

                                Log.e("id2 ","i "+ index + "   "+findFamilyVO.getResult().get(index).getUserId());

                                //200 : OK
                                //204 : 값없음(null반환)
                                //500 : Server Error


                                assert bodyVO != null;
                                if (bodyVO.getStatus().equals("200")) {

                                    Log.d("실행", "실행 됨");



                                        CalculateTime calculateTime = new CalculateTime();
                                        String lastTime = calculateTime.formatTimeString((bodyVO.getResult().get(0).getRegiDate().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").substring(0, 14)));

                                        name_list = findFamilyVO.getResult().get(index).getNickName();
                                        last_nam_list = findFamilyVO.getResult().get(index).getNickName().substring(0,1);
                                    //Log.e( "onResponse3 ", bodyVO.getResult().get(0).getContent().length()+"  ");
                                        if(bodyVO.getResult().get(0).getContent().length() >= 20){
                                            last_message_list = bodyVO.getResult().get(0).getContent().substring(0,20)+"...";
                                            Log.e( "onResponse3 ", bodyVO.getResult().get(0).getContent().length()+"  ");
                                        }else{
                                            last_message_list = bodyVO.getResult().get(0).getContent();
                                        }

                                        last_message_time_list = lastTime;

                                        chat_list_model = new Chat_List_Model(id,name_list,last_nam_list,last_message_list,last_message_time_list);
                                        chat_list_models.add(chat_list_model);

                                    if(chat_list_models.size() == findFamilyVO.getResult().size()){
                                        chat_presenter.sendArrayList2(chat_list_models);
                                        chat_presenter.onResponse(true);

                                    }

                                        //chat_list_models.add(chat_list_model);
                                       //chat_presenter.sendArrayList2(chat_list_models);

                                        /*if(chat_list_models.size() == findFamilyVO.getResult().size()){
                                            chat_presenter.onResponse(true);


                                        }
*/





                                    Log.e( "chat_list_modelsSize:1 ", chat_list_models.size()+" ");

                                    //Log.e( "test_1", "\n"+"id :"+chat_list_model.getId()+" \n"+"name :"+name_list+"\n"+"lastname : "+last_nam_list+"\n"+"message :"+ last_message_list+"\n"+"messagetime :"+last_message_time_list+"\n");



                                    Log.d("name111", "aaa" + name);
                                } else if (bodyVO.getStatus().equals("204")) {

                                    last_message_list = " ";
                                    last_message_time_list = " ";
                                    name_list = findFamilyVO.getResult().get(index).getNickName();
                                    last_nam_list = findFamilyVO.getResult().get(index).getNickName().substring(0,1);

                                    chat_list_model = new Chat_List_Model(id,name_list,last_nam_list,last_message_list,last_message_time_list);
                                    chat_list_models.add(chat_list_model);


                                    if(chat_list_models.size() == findFamilyVO.getResult().size()){
                                        chat_presenter.sendArrayList2(chat_list_models);
                                        chat_presenter.onResponse(true);

                                    }




                                } else if (bodyVO.getStatus().equals("500")) {

                                }

                            }

                            @Override
                            public void onFailure(Call<MessageBodyVO> call, Throwable t) {

                            }
                        });



                    }




                } else if (findFamilyVO.getStatus().equals("204")) {
                    chat_presenter.makeToastMessage("상대의 계정이 존재하지 않습니다.");
                } else if (findFamilyVO.getStatus().equals("400")) {
                    chat_presenter.makeToastMessage("잘못된 요청입니다.");
                } else if (findFamilyVO.getStatus().equals("500")) {
                    chat_presenter.makeToastMessage("서버 오루 입니다..");
                }



            }



            @Override
            public void onFailure(Call<Connected_Family> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });



    }

    private void makeList(ArrayList<Chat_List_Model> chat_list_model) {

        this.chat_list_models = chat_list_model;

        Log.e( "makeList4: ",this.chat_list_models.size()+" " );








    }

    private void makeList(Chat_List_Model chat_list_model) {

            chat_list_models.add(chat_list_model);
            chat_presenter.sendArrayList2(chat_list_models);
            if(chat_list_models.size() == id_list.size()){
                chat_presenter.onResponse(true);

            }



    }

    @Override
    public void setChatUserInfo(String user_name, String user_id) {
        User_info.setName(user_name);
        User_info.setId(user_id);
    }

    @Override
    public void sendMessage(final String use2_id, String message) {
        final SendMessageVO sendVO = new SendMessageVO(User_Id.getUser_Id(), use2_id, message);

        //sendVO.setRegidate(time);

        retrofit = new Retrofit.Builder()
                .baseUrl(messageService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageService = retrofit.create(MessageService.class);

        Log.d("@@@@@@@@@@@" + "id", sendVO.getGivingUser() + "recive user " + sendVO.getReceivingUser() + "\n" + "context" + sendVO.getContent());
        Call<MessageResultBodyVO> call = messageService.sendAmeesage(sendVO);
        call.enqueue(new Callback<MessageResultBodyVO>() {
            @Override
            public void onResponse(Call<MessageResultBodyVO> call, Response<MessageResultBodyVO> response) {
                MessageResultBodyVO messageBodyVO = response.body();
                //201 : OK
                //400: FCM error
                //500 : Server Error
                Log.e("onResponse: ", messageBodyVO.getStatus() + "\n" + sendVO.getGivingUser() + "\n" + sendVO.getReceivingUser() + "\n" + sendVO.getContent());
                if (messageBodyVO.getStatus().equals("201")) {
                    chat_presenter.makeToastMessage("전송되었습니다.");
                    sendVO.setRegidate(messageBodyVO.getRegiDate().substring(11, 16));
                    chat_presenter.sendSendVO(sendVO);
                    chat_presenter.onResponse(true);
                    /*albumList.add(sendVO);
                    chat_presenter.sendChatArrayList(albumList);
                    chat_presenter.onResponse(true);*/


                } else if (messageBodyVO.getStatus().equals("400")) {
                    chat_presenter.makeToastMessage("FCM Error");
                } else if (messageBodyVO.getStatus().equals("500")) {
                    chat_presenter.makeToastMessage("Server Error");

                }
            }

            @Override
            public void onFailure(Call<MessageResultBodyVO> call, Throwable t) {

            }
        });

    }

    @Override
    public void getPreviouseConversation(String u_id, String y_id) {
        retrofit = new Retrofit.Builder()
                .baseUrl(messageService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageService = retrofit.create(MessageService.class);

        Call<MessageBodyVO> call = messageService.getTheChatting(u_id, y_id, 1, 0);
        call.enqueue(new Callback<MessageBodyVO>() {
            @Override
            public void onResponse(Call<MessageBodyVO> call, Response<MessageBodyVO> response) {
                MessageBodyVO bodyVO = response.body();
                String name = "";
                //200 : OK
                //204 : 값없음(null반환)
                //500 : Server Error


                assert bodyVO != null;
                if (bodyVO.getStatus().equals("200")) {
                    for (int i = 0; i < bodyVO.getResult().size(); i++) {
                        Log.d("실행", "실행 됨");

                        SendMessageVO sendMessageVO = new SendMessageVO(bodyVO.getResult().get(i).getGivingUser(),
                                bodyVO.getResult().get(i).getContent(),
                                bodyVO.getResult().get(i).getReceivingUser(),
                                bodyVO.getResult().get(i).getRegiDate().substring(11, 16));


                        message = bodyVO.getResult().get(i).getContent();


                        Log.e("god", message + "11111" + familyItem.getLast_message());

                        albumList.add(sendMessageVO);


//                        chat_presenter.sendSendVO(sendMessageVO);
//                        chat_presenter.onResponse(true);

                    }
                    Collections.reverse(albumList);
                    chat_presenter.sendChatArrayList(albumList);
                    Log.e("onResponse: ", "  ss " + bodyVO.getResult().size()
                            + "\n" + albumList.size());
                    //Collections.reverse(albumList);
                   /* mRecyclerView.setAdapter(new MyRecyclerAdapter(albumList,R.layout.recycleritem));
                    linearLayoutManager.setStackFromEnd(true);
                    mRecyclerView.setLayoutManager(linearLayoutManager);*/

                    Log.d("name111", "aaa" + name);
                } else if (bodyVO.getStatus().equals("204")) {
                    //  user_contextEdt.setFocusable(true);
                } else if (bodyVO.getStatus().equals("500")) {
                    //  Toast.makeText(getApplicationContext(),"서버 오류입니다.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MessageBodyVO> call, Throwable t) {

            }
        });
    }


    private void sendFamiyItem(ChatItem familyItem) {
        this.familyItem = familyItem;
    }


    @Override
    public void getIdIndex(int index) {
        this.index = index;
       // chat_presenter.makeToastMessage(id_list.get(index));
        chat_presenter.sendId(id_list.get(index));
    }

    @Override
    public void getID(String id) {
        chat_presenter.sendId(id);
    }

    @Override
    public void sendUpdate(ArrayList<Chat_List_Model> chat_list_model) {
        this.chat_list_models = chat_list_model;
       // chat_list_models.clear();
        //Log.e( "sendUpdate: ",chat_list_models.size()+"' " );

        setPreviousRegistered();
    }

    public void makeList() {

    }


}