package yaksok.dodream.com.yaksok_refactoring.presenter.chat;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.model.chat.Chat_Model;
import yaksok.dodream.com.yaksok_refactoring.model.chat.I_chat_model;
import yaksok.dodream.com.yaksok_refactoring.view.chat.I_chat_list;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;

public class Chat_Presenter implements I_chat_presenter{
    I_chat_list view;
    I_chat_model model;

    public Chat_Presenter(I_chat_list view) {
        this.view = view;
        model = new Chat_Model(this);
    }

    @Override
    public void setPreviousRegistered() {
        model.setPreviousRegistered();
    }

    @Override
    public void makeToastMessage(String message) {
        view.makeToastMessage(message);
    }

    @Override
    public void onResponse(boolean response) {
        view.onResponse(response);
    }

    @Override
    public void sendArrayList(ArrayList<FamilyItem> familyItems) {
        view.getArrayList(familyItems);
    }

    @Override
    public void sendChatArrayList(ArrayList<SendMessageVO> albumList) {
        view.sendChatArrayList(albumList);
    }

    @Override
    public void setChatUserInfo(String user_name, String user_id) {
        model.setChatUserInfo(user_name,user_id);
    }

    @Override
    public void sendMessage(String message,String user2_id) {
        model.sendMessage(message,user2_id);
    }
}
