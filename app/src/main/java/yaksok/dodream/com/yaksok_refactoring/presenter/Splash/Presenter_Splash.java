package yaksok.dodream.com.yaksok_refactoring.presenter.Splash;

import yaksok.dodream.com.yaksok_refactoring.model.Settings.MyPage_Model;
import yaksok.dodream.com.yaksok_refactoring.model.Splash.Splash_Model;
import yaksok.dodream.com.yaksok_refactoring.model.Splash.Splash_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.MyPage_PresenterToView;
import yaksok.dodream.com.yaksok_refactoring.view.Splash;
import yaksok.dodream.com.yaksok_refactoring.view.Splash_PresenterToView;

public class Presenter_Splash implements SplashToPresenter {
    public Splash_PresenterToView view;
    public Splash_PresenterToModel model;

    public Presenter_Splash(Splash_PresenterToView view){
        this.view = view;
        model = new Splash_Model(this);

    }

    @Override
    public void getServerStatus() {
        model.getServerStatus();
    }

    @Override
    public void onServerStatus(boolean status,String isFixing) {
        view.onServerStatus(status,isFixing);
    }
}
