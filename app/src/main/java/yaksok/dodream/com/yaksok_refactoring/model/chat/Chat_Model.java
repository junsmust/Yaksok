package yaksok.dodream.com.yaksok_refactoring.model.chat;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.CalculateTime;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatItem;
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
    MessageService messageService;
    ArrayList<SendMessageVO> albumList = new ArrayList<>();
    ArrayList<String> id_list = new ArrayList<>();
    ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> last_nam_list = new ArrayList<>();
    ArrayList<String> last_message_list = new ArrayList<>();
    ArrayList<String> last_message_time_list = new ArrayList<>();

    ChatItem familyItem = new ChatItem();
    String message;

    public ChatItem getFamilyItem() {
        return familyItem;
    }

    public void setFamilyItem(ChatItem familyItem) {
        this.familyItem = familyItem;
    }

    int index;

    public Chat_Model(Chat_Presenter chat_presenter) {
        this.chat_presenter = chat_presenter;

    }

    @Override
    public void setPreviousRegistered() {
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
                Connected_Family findFamilyVO = response.body();

                Log.e("onResponse:55555 ", findFamilyVO.getStatus());

                if (findFamilyVO.getStatus().equals("200")) {

                    familyItem.setIds(id_list);


                    for (int i = 0; i < findFamilyVO.getResult().size(); i++) {

                        Log.e("size: ", findFamilyVO.getResult().size() + " " + id_list.size());
                        id_list.add(findFamilyVO.getResult().get(i).getUserId());
                        name_list.add(findFamilyVO.getResult().get(i).getNickName());
                        last_nam_list.add(findFamilyVO.getResult().get(i).getNickName().substring(0, 1));
                    }


                    Log.e("setPreviousRegistered: ", last_nam_list.size() + " " + last_nam_list.size() + "  ");

                    getLast();


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

    public void getLast() {


        retrofit = new Retrofit.Builder()
                .baseUrl(messageService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageService = retrofit.create(MessageService.class);

        final ChatItem chatItem = new ChatItem();


        for (int i = 0; i < id_list.size(); i++) {

            Log.e("id: ", id_list.size() + "  ");

            Call<MessageBodyVO> call = messageService.getTheChatting(User_Id.getUser_Id(), id_list.get(i), 1, 0);
            final int finalI = i;
            final int finalI1 = i;
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

                        Log.d("실행", "실행 됨");


                        last_message_list.add(bodyVO.getResult().get(0).getContent());
                        last_message_time_list.add((bodyVO.getResult().get(0).getRegiDate().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").substring(0, 14)));


                        if (last_message_time_list.size() == 2 && last_message_time_list.size() == 2) {
                            Log.e("first if ", last_message_list.size() + " " + last_message_time_list.size() + "  ");
                            Log.e("first if2: ", last_message_list.get(0) + " " + last_message_list.get(1));
                            makeList();
                        }


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

            Log.e("name", name_list.get(i));

//            Log.e( "getLast: ", chatItem.getName());


/*
            chat_presenter.sendArrayList(familyItems);
            chat_presenter.onResponse(true);*/

               /* Log.e("m_l ",last_message_list.get(i));
                Log.e("t_l ",last_message_time_list.get(i));*/


            Log.e("i_big for: ", last_message_list.size() + " " + last_message_time_list.size() + "  ");

        }

        Log.e("i_out for: ", last_message_list.size() + " " + last_message_time_list.size() + "  ");


    }

    private void sendFamiyItem(ChatItem familyItem) {
        this.familyItem = familyItem;
    }


    @Override
    public void getIdIndex(int index) {
        this.index = index;
        chat_presenter.makeToastMessage(id_list.get(index));
        chat_presenter.sendId(id_list.get(index));
    }

    public void makeList() {


        for (int i = 0; i < id_list.size(); i++) {

            familyItem = new ChatItem(name_list.get(i), last_nam_list.get(i), last_message_list.get(i), last_message_time_list.get(i));
            familyItems.add(familyItem);

        }
        chat_presenter.sendArrayList(familyItems);
        chat_presenter.onResponse(true);

    }
}