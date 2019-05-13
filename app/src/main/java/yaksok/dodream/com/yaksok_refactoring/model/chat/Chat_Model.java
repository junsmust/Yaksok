package yaksok.dodream.com.yaksok_refactoring.model.chat;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.chat.Chat_Presenter;
import yaksok.dodream.com.yaksok_refactoring.view.chat.Chat_Room;
import yaksok.dodream.com.yaksok_refactoring.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageResultBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MessageService;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Chat_Model implements I_chat_model {
    private Chat_Presenter chat_presenter;
    private Retrofit retrofit;
    UserService userService;
    ArrayList<FamilyItem> familyItems = new ArrayList<>();
    MessageService messageService;
    ArrayList<SendMessageVO> albumList = new ArrayList<>();
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


        Call<FindFamilyVO> findFamilyVOCall = userService.getConnectedFamilyInfo(User_Id.getUser_Id());
        findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
            @Override
            public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                FindFamilyVO findFamilyVO = response.body();

                if (findFamilyVO.getStatus().equals("200")) {

                    for(int i = 0; i < findFamilyVO.getResult().size();i++){
                        familyItems.add(new FamilyItem(findFamilyVO.getResult().get(i).getNickName()+"("+findFamilyVO.getResult().get(i).getUserId()+")"));
                    }
                   // Log.e(TAG, "onResponse: "+ familyItems.size() );
                    chat_presenter.sendArrayList(familyItems);
                    chat_presenter.onResponse(true);

                } else if (findFamilyVO.getStatus().equals("204")) {
                    chat_presenter.makeToastMessage( "상대의 계정이 존재하지 않습니다.");
                } else if (findFamilyVO.getStatus().equals("400")) {
                    chat_presenter.makeToastMessage("잘못된 요청입니다.");
                } else if (findFamilyVO.getStatus().equals("500")) {
                    chat_presenter.makeToastMessage("서버 오루 입니다..");
                }

            }


            @Override
            public void onFailure(Call<FindFamilyVO> call, Throwable t) {
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
    public void sendMessage(String message,String user2_id) {
        final SendMessageVO sendVO = new SendMessageVO();
        sendVO.setGivingUser(User_Id.getUser_Id());
        sendVO.setContent(message);
        sendVO.setReceivingUser(user2_id);
        //sendVO.setRegidate(time);

        retrofit = new Retrofit.Builder()
                .baseUrl(messageService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageService = retrofit.create(MessageService.class);
/*

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
                   chat_presenter.makeToastMessage("전송되었습니다.");
                    sendVO.setRegidate(messageBodyVO.getRegiDate().substring(11,16));
                    albumList.add(sendVO);
                    chat_presenter.sendChatArrayList(albumList);
                    chat_presenter.onResponse(true);




                }
                else if(messageBodyVO.getStatus().equals("400")){
                    chat_presenter.makeToastMessage("FCM Error");
                } else if (messageBodyVO.getStatus().equals("500")) {
                    chat_presenter.makeToastMessage("Server Error");
                }
            }

            @Override
            public void onFailure(Call<MessageResultBodyVO> call, Throwable t) {

            }
        });
*/

    }
}
