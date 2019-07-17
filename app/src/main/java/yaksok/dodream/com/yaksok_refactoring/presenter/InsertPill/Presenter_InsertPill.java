package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill;

import android.content.Context;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.InsertPill_Model;
import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.InsertPill_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.InsertPill_PresenterToView;
import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public class Presenter_InsertPill implements InsertPillToPresenter{

    private InsertPill_PresenterToView view;
    private InsertPill_PresenterToModel model;

    public Presenter_InsertPill(InsertPill_PresenterToView view){
        this.view = view;
        model = new InsertPill_Model(this);
    }

    @Override
    public void insertPill(InsertPill_Item insertPill_item) {
        model.InsertPill(insertPill_item);
    }

    @Override
    public void onInsertResponse(boolean response, int status) {
        view.onInsertResponse(response, status);
    }

    @Override
    public void getFamilyList() {
        model.getFamilyList();
    }

    @Override
    public void onFamilyResponce(boolean Responce) {
        view.onFamilyResponce(Responce);
    }

    @Override
    public void myFamilyList(List<String> myFamilyList, List<String> myFamily_Id) {
        view.myFamilyList(myFamilyList, myFamily_Id);
    }

    @Override
    public void getMyContext(Context context) {
        model.getMyContext(context);
    }
}
