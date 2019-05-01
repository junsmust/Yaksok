package yaksok.dodream.com.yaksok_refactoring.presenter.main_menu;

import yaksok.dodream.com.yaksok_refactoring.model.user.IPresennterToModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.view.IPresenterToView_Main;
import yaksok.dodream.com.yaksok_refactoring.view.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.view.login.IPresenterToView;

public class Presenter_Main implements I_mainmenu {

    private IPresenterToView_Main view;
    private IPresennterToModel model;

    public Presenter_Main(IPresenterToView_Main view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void getAutoInfo(boolean auto) {
        view.getAutoInfo(auto);
    }
}
