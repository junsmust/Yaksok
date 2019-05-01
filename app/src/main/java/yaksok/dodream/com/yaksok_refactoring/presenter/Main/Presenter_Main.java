package yaksok.dodream.com.yaksok_refactoring.presenter.Main;

import yaksok.dodream.com.yaksok_refactoring.model.user.IPresennterToModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.LoginModel;
import yaksok.dodream.com.yaksok_refactoring.view.Main.IPresenterToView_Main;

public class Presenter_Main implements MainToPresenter {

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
