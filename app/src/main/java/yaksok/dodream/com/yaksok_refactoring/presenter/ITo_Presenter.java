package yaksok.dodream.com.yaksok_refactoring.presenter;


import com.google.gson.JsonObject;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;

public interface ITo_Presenter {
     void Login(User_Info_Model user_info_model);
     void IsLoggedIn();
     void IsLoggedIn(boolean isLoggedIn);
     void OnLoginResponse(boolean loginResponse);
     void OnErrorMessage(String message);
     void MakeToastMessage(String message);
     void NaverOAuthHandler(JSONObject jsonObject) throws JSONException;



}
