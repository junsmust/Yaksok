package yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Dialog;

import yaksok.dodream.com.yaksok_refactoring.model.MyPill.Dialog.Dialog_Model;
import yaksok.dodream.com.yaksok_refactoring.model.MyPill.Dialog.Dialog_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.model.MyPill.MyPill_Model;
import yaksok.dodream.com.yaksok_refactoring.model.MyPill.MyPill_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.Dialog.Dialog_PresenterToView;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_PresenterToView;

public class Presenter_Dialog implements DialogToPresenter{
    private Dialog_PresenterToView view;
    private Dialog_PresenterToModel model;

    public Presenter_Dialog(Dialog_PresenterToView view){
        this.view = view;
        model = new Dialog_Model(this);
    }

    @Override
    public void myPillDelete(int pillNo) {
        model.myPillDelete(pillNo);
    }

    @Override
    public void onMyPillDeleteRespoce(boolean MyPillResponse) {
        view.onMyPillDeleteRespoce(MyPillResponse);
    }
}
