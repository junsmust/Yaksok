package yaksok.dodream.com.yaksok_refactoring.presenter;


import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;

public interface IPresennterToModel  {

    void Login(User_Info_Model user_info_model);
    void IsLoggedin();
}
