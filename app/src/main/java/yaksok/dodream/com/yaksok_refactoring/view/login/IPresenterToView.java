package yaksok.dodream.com.yaksok_refactoring.view.login;

import com.nhn.android.naverlogin.OAuthLoginHandler;

import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;

public interface IPresenterToView {
    void onLoginResponse(boolean loginResponse);
    void onError(String errorMsg);
    void isLoggedIn(boolean isLoggedIn);
    void onMakeToastMessage(String message);
    void returnToLoginActivity();
    void onSnsSignUp(String userType);




}
