package yaksok.dodream.com.yaksok_refactoring.model.user;


import com.kakao.usermgmt.response.model.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;

public interface IPresennterToModel  {

    void Login(User_Info_Model user_info_model);
    void IsLoggedin();
    void NaverOAuthHandler(JSONObject jsonObject) throws JSONException;
    //void setOAuth(OAuthLoginHandler oAuth);
   //OAuthLoginHandler getOAuth();
    void getTocken(String token);
    void getData(String data);
    void kakaoLoginMethod();
    void handleScopeError(UserAccount userAccount);


}
