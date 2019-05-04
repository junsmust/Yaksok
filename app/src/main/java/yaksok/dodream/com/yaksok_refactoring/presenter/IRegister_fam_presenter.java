package yaksok.dodream.com.yaksok_refactoring.presenter;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;

public interface IRegister_fam_presenter {
    void searchFam(String pn);
    void makeToastMessage(String message);
    void onResponse(boolean response);
    void sendArrayList(ArrayList<FamilyItem> familyItems);
    void makeDialog(String name,String id);
    void setYesRegisterFam(boolean isOkay,String id);
    void onResponse2(boolean response2,FamilyItem familyItem);

    void setPreviousRegistered();



}
