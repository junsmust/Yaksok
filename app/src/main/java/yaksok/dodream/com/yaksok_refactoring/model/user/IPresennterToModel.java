package yaksok.dodream.com.yaksok_refactoring.model.user;


import android.content.Context;
import android.content.SharedPreferences;

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
    void kakaoLoginMethod();
    void handleScopeError(UserAccount userAccount);
    void checkBox(SharedPreferences sharedPreferences,SharedPreferences.Editor editor,boolean auto);
    void autoLogin(String id, String pw,String userType,boolean auto);
    void autoLogin(String id,String userType,boolean auto);
    void getEditor(SharedPreferences.Editor editor);
    void performLoginOperation(final User_Info_Model user_info_model);
    void getMyContext(Context context);

    void findPassword();
}
