package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Sel_AlarmRecive;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.Sel_AlarmRecive.Sel_AlarmRecive_Model;
import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.Sel_AlarmRecive.Sel_AlarmRecive_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.Sel_AlarmRecive.Sel_AlarmRecive_PresenterToView;

public class Presenter_Sel_AlarmRecive implements Sel_AlarmReciveToPresenter {
    private Sel_AlarmRecive_PresenterToModel model;
    private Sel_AlarmRecive_PresenterToView view;

    public Presenter_Sel_AlarmRecive(Sel_AlarmRecive_PresenterToView view){
        this.view = view;
        model =  new Sel_AlarmRecive_Model(this);
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


}
