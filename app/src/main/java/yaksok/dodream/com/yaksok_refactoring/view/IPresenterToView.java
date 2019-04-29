package yaksok.dodream.com.yaksok_refactoring.view;

public interface IPresenterToView {
    void onLoginResponse(boolean loginResponse);
    void onError(String errorMsg);
    void isLoggedIn(boolean isLoggedIn);
    void onMakeToastMessage(String message);
}
