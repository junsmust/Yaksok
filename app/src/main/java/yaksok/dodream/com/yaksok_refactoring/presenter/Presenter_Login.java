package yaksok.dodream.com.yaksok_refactoring.presenter;

import com.google.gson.JsonObject;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import yaksok.dodream.com.yaksok_refactoring.model.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.view.IPresenterToView;


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





}
