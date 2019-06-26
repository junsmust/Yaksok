package yaksok.dodream.com.yaksok_refactoring.Adapter.chat;

import android.util.Log;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.model.chat.Chat_Model;
import yaksok.dodream.com.yaksok_refactoring.model.chat.I_chat_model;
import yaksok.dodream.com.yaksok_refactoring.presenter.chat.Chat_Presenter;

public class Chat_List_Model {
   private String id,name,lastName,lastMessage,lastTime;
   //private ArrayList<String> name;

    public Chat_List_Model(String id, String name, String lastName, String lastMessage, String lastTime) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
