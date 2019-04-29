package yaksok.dodream.com.yaksok_refactoring.presenter;


import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;

public interface ITo_Presenter {
     void Login(User_Info_Model user_info_model);
     void IsLoggedIn();
     void IsLoggedIn(boolean isLoggedIn);
     void OnLoginResponse(boolean loginResponse);
     void OnErrorMessage(String message);
     void MakeToastMessage(String message);


}
