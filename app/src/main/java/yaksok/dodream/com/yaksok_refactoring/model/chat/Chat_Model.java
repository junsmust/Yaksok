package yaksok.dodream.com.yaksok_refactoring.model.chat;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import yaksok.dodream.com.yaksok_refactoring.view.chat.Chatting_list;
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
    Chat_Presenter chat_presenter;
    Retrofit retrofit;

    MessageService messageService,messageService2;
    UserService userService;

    Chat_List_Model chat_list_model;
    ChatItem familyItem = new ChatItem();

    ArrayList<ChatItem> familyItems = new ArrayList<>();
    ArrayList<SendMessageVO> albumList = new ArrayList<>();
    ArrayList<String> otherPersonIdList = new ArrayList<>();
    ArrayList<Chat_List_Model> chat_list_models  = new ArrayList<>();

    public ChatItem getFamilyItem() {
        return familyItem;
    }

    public void setFamilyItem(ChatItem familyItem) {
        this.familyItem = familyItem;
    }


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


                if (findFamilyVO.getStatus().equals("200")) {
                    for(int  i = 0; i < findFamilyVO.getResult().size(); i++)
                        otherPersonIdList.add(findFamilyVO.getResult().get(i).getUserId());
                    messageService2 = retrofit2.create(MessageService.class);
                    setChatList(messageService2, 0, findFamilyVO);

                } else if (findFamilyVO.getStatus().equals("204")) {
                    chat_presenter.makeToastMessage("상대의 계정이 존재하지 않습니다.");
                } else if (findFamilyVO.getStatus().equals("400")) {
                    chat_presenter.makeToastMessage("잘못된 요청입니다.");
                } else if (findFamilyVO.getStatus().equals("500")) {
                    chat_presenter.makeToastMessage("서버 오류 입니다..");
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
    public void getIdIndex(int index) {
        // this.index = index;
        chat_presenter.sendId(otherPersonIdList.get(index));
    }

    @Override
    public void getID(String id) {
        chat_presenter.sendId(id);
    }

    @Override
    public void sendUpdate(ArrayList<Chat_List_Model> chat_list_model) {
        this.chat_list_models = chat_list_model;
        setPreviousRegistered();
    }

    private void setChatList(final MessageService messageService2, final int currentIdx, final Connected_Family findFamilyVO) {

        Call<MessageBodyVO> call2 = messageService2.getTheChatting(User_Id.getUser_Id(), otherPersonIdList.get(currentIdx), 1, 0);
        call2.enqueue(new Callback<MessageBodyVO>() {
            @Override
            public void onResponse(Call<MessageBodyVO> call, Response<MessageBodyVO> response) {
                MessageBodyVO messageBodyVO = response.body();
                String name,last_name,last_message,last_message_time;
                //200 : OK
                //204 : 값없음(null반환)
                //500 : Server Error
                //assert bodyVO != null;
                if (messageBodyVO.getStatus().equals("200")) {
                    Chatting_list.linearLayout.setVisibility(View.GONE);
                    CalculateTime calculateTime = new CalculateTime();
                    String lastTime = calculateTime.formatTimeString((messageBodyVO.getResult().get(0).getRegiDate().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").substring(0, 14)));
                    name = findFamilyVO.getResult().get(currentIdx).getNickName();
                    last_name = findFamilyVO.getResult().get(currentIdx).getNickName().substring(0,1);

                    if(messageBodyVO.getResult().get(0).getContent().length() >= 20)
                        last_message = messageBodyVO.getResult().get(0).getContent().substring(0,20)+"...";
                    else
                        last_message = messageBodyVO.getResult().get(0).getContent();

                    last_message_time = lastTime;
                    chat_list_model = new Chat_List_Model(otherPersonIdList.get(currentIdx),name,last_name,last_message,last_message_time);
                    chat_list_models.add(chat_list_model);

                } else if (messageBodyVO.getStatus().equals("204")) {

                    last_message = " ";
                    last_message_time = " ";
                    name = findFamilyVO.getResult().get(currentIdx).getNickName();
                    last_name = findFamilyVO.getResult().get(currentIdx).getNickName().substring(0,1);

                    chat_list_model = new Chat_List_Model(otherPersonIdList.get(currentIdx),name , last_name,last_message,last_message_time);
                    chat_list_models.add(chat_list_model);

                } else if (messageBodyVO.getStatus().equals("500")) {

                }

                if(currentIdx + 1 == findFamilyVO.getResult().size()){
                    System.out.println("재환");
                    chat_presenter.sendArrayList2(chat_list_models);
                    chat_presenter.onResponse(true);
                    return;
                }else
                    setChatList(messageService2, currentIdx + 1, findFamilyVO);
            }

            @Override
            public void onFailure(Call<MessageBodyVO> call, Throwable t) {

            }
        });
    }
}