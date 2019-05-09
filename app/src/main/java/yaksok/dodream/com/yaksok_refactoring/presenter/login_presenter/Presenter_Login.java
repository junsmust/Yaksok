package yaksok.dodream.com.yaksok_refactoring.presenter.login_presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.kakao.usermgmt.response.model.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import yaksok.dodream.com.yaksok_refactoring.model.user.IPresennterToModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.presenter.login_presenter.ITo_Presenter;
import yaksok.dodream.com.yaksok_refactoring.view.login.IPresenterToView;


public class Presenter_Login implements ITo_Presenter {
    private IPresenterToView view;
    private IPresennterToModel model;


    public Presenter_Login(IPresenterToView view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void Login(User_Info_Model user_info_model) {
        model.Login(user_info_model);
    }

    @Override
    public void IsLoggedIn() {
        model.IsLoggedin();
    }
    @Override
    public void IsLoggedIn(boolean isLoggedIn) {
        view.isLoggedIn(isLoggedIn);
    }



    @Override
    public void OnLoginResponse(boolean loginResponse) {
        view.onLoginResponse(loginResponse);

    }


    @Override
    public void OnErrorMessage(String message) {
        view.onError(message);
    }

    @Override
    public void MakeToastMessage(String message) {
        view.onMakeToastMessage(message);
    }

    @Override
    public void NaverOAuthHandler(JSONObject jsonObject) throws JSONException {
        model.NaverOAuthHandler(jsonObject);
    }

    @Override
    public void kakaoLoginMethod() {
        model.kakaoLoginMethod();
    }

    @Override
    public void handleScopeError(UserAccount userAccount) {
        model.handleScopeError(userAccount);
    }

    @Override
    public void redirectLoginActivity() {
        view.returnToLoginActivity();
    }

    @Override
    public void checkBox(SharedPreferences sharedPreferences, SharedPreferences.Editor editor,boolean auto) {
        model.checkBox(sharedPreferences,editor,auto);
    }

    @Override
    public void autoLogin(String id, String pw, String userType,boolean auto) {
        Log.d("!!!!",id);
        model.autoLogin(id,pw,userType,auto);
    }

    @Override
    public void autoLogin(String id, String userType,boolean auto) {
        model.autoLogin(id,userType,auto);
    }

    @Override
    public void onSnsSignUp(String userType) {
        view.onSnsSignUp(userType);
    }

    @Override
    public void sendEditor(SharedPreferences.Editor editor) {
        model.getEditor(editor);
    }



    /*@Override
    public void onSnsSignUp(String userType) {
        view.onSnsSignUp(userType);
    }*/


}
