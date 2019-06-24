package yaksok.dodream.com.yaksok_refactoring.Adapter.chat;

import java.util.ArrayList;

public class ChatItem {
    public String name,first_name,last_message,last_messge_time;
    public ArrayList<String> ids = new ArrayList<>();

    public ChatItem() {
    }

    public ChatItem(String name, String first_name, String last_message, String last_messge_time){
        this.name = name;
        this.first_name = first_name;
        this.last_message = last_message;
        this.last_messge_time = last_messge_time;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_messge_time() {
        return last_messge_time;
    }

    public void setLast_messge_time(String last_messge_time) {
        this.last_messge_time = last_messge_time;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }
}
