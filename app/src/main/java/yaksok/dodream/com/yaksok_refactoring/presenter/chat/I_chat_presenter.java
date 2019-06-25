package yaksok.dodream.com.yaksok_refactoring.presenter.chat;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatItem;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;

public interface I_chat_presenter {
    void setPreviousRegistered();
    void makeToastMessage(String message);
    void onResponse(boolean response);
    void sendArrayList(ArrayList<ChatItem> familyItems);
    void sendChatArrayList(ArrayList<SendMessageVO> albumList);
    void setChatUserInfo(String user_name,String user_id);
    void sendMessage(String message,String user2_id);


    void sendSendVO(SendMessageVO sendVO);
    void getPreviouseConversation(String u_id,String y_id);

    void sendIdIndex(int index);

    void sendId(String s);
    void getArrayId(ArrayList<String> ids);

}
