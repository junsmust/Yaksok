package yaksok.dodream.com.yaksok_refactoring.presenter.chat;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.ChatItem;
import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.Chat_List_Model;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;

public interface I_chat_presenter {
    void setPreviousRegistered();
    void makeToastMessage(String message);
    void onResponse(boolean response);
    void sendArrayList2(ArrayList<Chat_List_Model> chat_list_model);


    void sendIdIndex(int index);

    void sendId(String s);
    void getArrayId(ArrayList<String> ids);

    void sendIdIndex(String id);

    void sendUpdate(ArrayList<Chat_List_Model> chat_list_model);

     
}
