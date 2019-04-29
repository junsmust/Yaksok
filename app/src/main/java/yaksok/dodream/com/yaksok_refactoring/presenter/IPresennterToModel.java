package yaksok.dodream.com.yaksok_refactoring.presenter;


import com.google.gson.JsonObject;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;

public interface IPresennterToModel  {

    void Login(User_Info_Model user_info_model);
    void IsLoggedin();
    void NaverOAuthHandler(JSONObject jsonObject) throws JSONException;
    //void setOAuth(OAuthLoginHandler oAuth);
   //OAuthLoginHandler getOAuth();
    void getTocken(String token);
    void getData(String data);

}
