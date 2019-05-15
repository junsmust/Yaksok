package yaksok.dodream.com.yaksok_refactoring.presenter.family_register;


import android.util.Log;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.model.Register_Family.IRegister_Presenter_To_FamModel;
import yaksok.dodream.com.yaksok_refactoring.model.Register_Family.Register_Fam_Model;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.IRegister_Presenter_Family_To_View;

public class Register_Fam_Presenter implements IRegister_fam_presenter {
    private static final String TAG = "Register_Fam_Presenter";
    IRegister_Presenter_To_FamModel model;
    IRegister_Presenter_Family_To_View view;



    public Register_Fam_Presenter(IRegister_Presenter_Family_To_View view) {
        this.view = view;
        model = new Register_Fam_Model(this);
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
    public void onResponse(boolean response) {
        view.onResponse(response);
    }

   @Override
    public void sendArrayList(ArrayList<FamilyItem> familyItems) {
        view.getArrayList(familyItems);
       Log.e(TAG, "sendArrayList: "+ familyItems.size() );

    }

    @Override
    public void snedViewToModelArrayList(ArrayList<FamilyItem> familyItems) {
        model.getArrayList(familyItems);
    }

    @Override
    public void makeDialog(String name,String id) {
        view.makeDialog(name,id);
    }

    @Override
    public void setYesRegisterFam(boolean isOkay,String id) {
        model.setYesRegisterFam(isOkay,id);
    }

    @Override
    public void onResponse2(boolean response2, FamilyItem familyItem) {
        view.onResponse2(response2,familyItem);
    }

    @Override
    public void onResponse3(boolean response3) {
        view.onResponse3(response3);
    }

    @Override
    public void deleteFam(boolean isOkay, String id,int position) {
        model.deleteFam(isOkay,id,position);
    }


    @Override
    public void setPreviousRegistered() {
        model.setPreviousRegistered();
    }

    @Override
    public void sendAdapter(FamilyFindAdapter adapter) {
        model.getAdapter(adapter);
    }

    @Override
    public void sendToViewAdapter(FamilyFindAdapter adapter) {
        view.getFromModelAdapter(adapter);
    }


}
