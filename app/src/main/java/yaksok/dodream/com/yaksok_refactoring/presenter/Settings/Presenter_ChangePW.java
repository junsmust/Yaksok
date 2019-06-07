package yaksok.dodream.com.yaksok_refactoring.presenter.Settings;

import yaksok.dodream.com.yaksok_refactoring.model.Settings.ChangePW_Model;
import yaksok.dodream.com.yaksok_refactoring.model.Settings.ChangePW_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.model.Settings.MyPage_Model;
import yaksok.dodream.com.yaksok_refactoring.model.Settings.MyPage_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.ChangePW_PresenterToView;
import yaksok.dodream.com.yaksok_refactoring.view.Settings.MyPage_PresenterToView;

public class Presenter_ChangePW implements ChangePWToPresenter{

    private ChangePW_PresenterToView view;
    private ChangePW_PresenterToModel model;

    public Presenter_ChangePW(ChangePW_PresenterToView view){
        this.view = view;
        model = new ChangePW_Model(this);
    }

    public ChangePW_PresenterToView getView() { return view; }

    public void setView(ChangePW_PresenterToView view) { this.view = view; }

    public ChangePW_PresenterToModel getModel() { return model; }

    public void setModel(ChangePW_PresenterToModel model) { this.model = model; }

    @Override
    public void onChangePW(String id, String opw,String ch_pw) {
        model.onChangePw(id,opw,ch_pw);
    }

    @Override
    public void onChangeResponse(boolean response, int status) {
        view.onChangeResponse(response,status);
    }
}
