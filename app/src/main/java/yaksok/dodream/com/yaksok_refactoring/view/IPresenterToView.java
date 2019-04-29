package yaksok.dodream.com.yaksok_refactoring.view;

import com.nhn.android.naverlogin.OAuthLoginHandler;

public interface IPresenterToView {
    void onLoginResponse(boolean loginResponse);
    void onError(String errorMsg);
    void isLoggedIn(boolean isLoggedIn);
    void onMakeToastMessage(String message);


}
