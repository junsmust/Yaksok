package yaksok.dodream.com.yaksok_refactoring.presenter.Main;

import yaksok.dodream.com.yaksok_refactoring.model.Main.Main_Model;
import yaksok.dodream.com.yaksok_refactoring.model.Main.Main_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.Main.Main_PresenterToView;

public class Presenter_Main implements MainToPresenter {

    private Main_PresenterToView view;
    private Main_PresenterToModel model;
    public Presenter_Main(Main_PresenterToView view){
        this.view = view;
        model = new Main_Model(this);
    }

    @Override
    public void getNearTimePill() {
        model.getNearTimePill();
    }

    @Override
    public void onMyNearPillResponce(boolean MyNearPillResponse) {
        view.onMyNearPillResponce(MyNearPillResponse);
    }

    @Override
    public void MyNearTime(int nearTime_sec,boolean pillTime_day) {
        view.MyNearTime(nearTime_sec,pillTime_day);
    }
}