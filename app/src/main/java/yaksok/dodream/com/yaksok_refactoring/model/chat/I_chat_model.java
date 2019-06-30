package yaksok.dodream.com.yaksok_refactoring.model.chat;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.chat.Chat_List_Model;

public interface I_chat_model {
    void setPreviousRegistered();
    void setChatUserInfo(String user_name,String user_id);
    void getIdIndex(int index);

    void getID(String id);

    void sendUpdate(ArrayList<Chat_List_Model> chat_list_model);
    
}