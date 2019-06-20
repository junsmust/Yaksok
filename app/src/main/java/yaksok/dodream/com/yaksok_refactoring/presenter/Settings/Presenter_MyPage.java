package yaksok.dodream.com.yaksok_refactoring.presenter.Settings;

import android.view.View;

import yaksok.dodream.com.yaksok_refactoring.model.Settings.MyPage_Model;
import yaksok.dodream.com.yaksok_refactoring.model.Settings.MyPage_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.MyPage_PresenterToView;

public class Presenter_MyPage implements MyPageToPresenter {
    private MyPage_PresenterToView view;
    private MyPage_PresenterToModel model;

    public Presenter_MyPage(MyPage_PresenterToView view){
        this.view = view;
        model = new MyPage_Model(this);

    }

    public MyPage_PresenterToView getView() { return view; }

    public void setView(MyPage_PresenterToView view) { this.view = view; }

    public MyPage_PresenterToModel getModel() { return model; }

    public void setModel(MyPage_PresenterToModel model) { this.model = model; }

    @Override
    public void onSecOut() {
        model.onSecOut();
    }

    @Override
    public void onSecOutResponse(boolean response) {
        view.onSecOutResponse(response);
    }

    @Override
    public void onChangePW(String id, String opw, String ch_pw) {
        model.onChangePw(id,opw,ch_pw);
    }

    @Override
    public void onChangeResponse(boolean response, int status) {
        view.onChangeResponse(response,status);
    }
}
