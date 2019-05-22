package yaksok.dodream.com.yaksok_refactoring.view.chat;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.vo.SendMessageVO;

public interface I_chat_list {
    void makeToastMessage(String message);
    void onResponse(boolean response);
    void getArrayList(ArrayList<FamilyItem> familyItems);
    void sendChatArrayList(ArrayList<SendMessageVO> albumList);
    void getSendVO(SendMessageVO sendVO);
}
