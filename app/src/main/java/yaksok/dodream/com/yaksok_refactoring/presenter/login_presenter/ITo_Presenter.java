package yaksok.dodream.com.yaksok_refactoring.presenter.login_presenter;


import android.content.SharedPreferences;

import com.kakao.usermgmt.response.model.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;

public interface ITo_Presenter {
     void Login(User_Info_Model user_info_model);
     void IsLoggedIn();
     void IsLoggedIn(boolean isLoggedIn);
     void OnLoginResponse(boolean loginResponse);
     void OnErrorMessage(String message);
     void MakeToastMessage(String message);
     void NaverOAuthHandler(JSONObject jsonObject) throws JSONException;
     void kakaoLoginMethod();
     void handleScopeError(UserAccount userAccount);
     void redirectLoginActivity();
     void checkBox(SharedPreferences sharedPreferences,SharedPreferences.Editor editor,boolean auto);
     void autoLogin(String id, String pw,String userType);
     void autoLogin(String id,String userType);



}
