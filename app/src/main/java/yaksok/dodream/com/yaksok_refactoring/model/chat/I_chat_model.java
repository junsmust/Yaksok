package yaksok.dodream.com.yaksok_refactoring.model.chat;

public interface I_chat_model {
    void setPreviousRegistered();
    void setChatUserInfo(String user_name,String user_id);
    void sendMessage(String message,String user2_id);

}
