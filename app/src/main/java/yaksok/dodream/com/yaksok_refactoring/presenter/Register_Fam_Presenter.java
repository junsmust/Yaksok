package yaksok.dodream.com.yaksok_refactoring.presenter;


import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.model.Register_Family.IRegister_Presenter_To_FamModel;
import yaksok.dodream.com.yaksok_refactoring.model.Register_Family.Register_Fam_Model;
import yaksok.dodream.com.yaksok_refactoring.presenter.IRegister_fam_presenter;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.IRegister_Presenter_Family_To_View;

public class Register_Fam_Presenter implements IRegister_fam_presenter {
    IRegister_Presenter_To_FamModel model;
    IRegister_Presenter_Family_To_View view;

    public Register_Fam_Presenter() {
    }

    public Register_Fam_Presenter(IRegister_Presenter_Family_To_View view) {
        this.view = view;
        model = new Register_Fam_Model();
    }


    @Override
    public void searchFam(String pn) {
        model.findFam(pn);
    }

    @Override
    public void makeToastMessage(String message) {
        view.makeToastMessage(message);
    }

    @Override
    public void sendAdapterToView(FamilyFindAdapter adapter) {

    }


    @Override
    public void adapterInit(FamilyFindAdapter adapter) {
        model.adapterInit(adapter);
    }

    @Override
    public void onResponse(boolean response) {
        view.onResponse(response);
    }

    @Override
    public void getArrayList(ArrayList<FamilyItem> familyItems) {
        view.getArrayList(familyItems);
    }


}
