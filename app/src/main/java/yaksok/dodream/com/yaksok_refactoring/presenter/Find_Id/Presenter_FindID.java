package yaksok.dodream.com.yaksok_refactoring.presenter.Find_Id;

import yaksok.dodream.com.yaksok_refactoring.model.Find_Id.FindID_Model;
import yaksok.dodream.com.yaksok_refactoring.model.Find_Id.FindID_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.model.MyPill.MyPill_Model;
import yaksok.dodream.com.yaksok_refactoring.model.MyPill.MyPill_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.Find_Id.FindID_PresenterToView;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_PresenterToView;

public class Presenter_FindID implements FindIDToPresenter {
    private FindID_PresenterToView view;
    private FindID_PresenterToModel model;

    public Presenter_FindID(FindID_PresenterToView view){
        this.view = view;
        model = new FindID_Model(this);
    }

    @Override
    public void getFindID(String phone) {
        model.getFindID(phone);
    }

    @Override
    public void onFindIDResponse(Boolean response,int status) {
        view.onFindIDResponse(response,status);
    }

    @Override
    public void onMyID(String ID) {
        view.onMyID(ID);
    }
}
