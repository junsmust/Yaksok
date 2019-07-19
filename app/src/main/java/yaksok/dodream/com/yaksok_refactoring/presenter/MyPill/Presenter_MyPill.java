package yaksok.dodream.com.yaksok_refactoring.presenter.MyPill;

import android.content.Context;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.model.MyPill.MyPill_Model;
import yaksok.dodream.com.yaksok_refactoring.model.MyPill.MyPill_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_PresenterToView;
import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;

public class Presenter_MyPill implements MyPillToPresenter {
    private MyPill_PresenterToView view;
    private MyPill_PresenterToModel model;

    public Presenter_MyPill(MyPill_PresenterToView view){
        this.view = view;
        model = new MyPill_Model(this);
    }

    @Override
    public void getMyPill() {
        model.getMyPill_List();
    }

    @Override
    public void onMyPillResponce(boolean MyPillResponse, int status) {
        view.onMyPillResponce(MyPillResponse,status);
    }

    @Override
    public void myPillList(MyPillVO myPillVO) {
        view.myPillList(myPillVO);
    }

    @Override
    public void myPillDelete(int pillNo) {
        model.myPillDelete(pillNo);
    }

    @Override
    public void onMyPillDeleteRespoce(boolean MyPillResponse) {
        view.onMyPillDeleteRespoce(MyPillResponse);
    }

    @Override
    public void getMyContext(Context context) {
        model.getMyContext(context);
    }


}
