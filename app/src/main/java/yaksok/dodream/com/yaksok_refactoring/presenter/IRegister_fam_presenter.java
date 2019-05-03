package yaksok.dodream.com.yaksok_refactoring.presenter;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;

public interface IRegister_fam_presenter {
    void searchFam(String pn);
    void makeToastMessage(String message);
    void sendAdapterToView(FamilyFindAdapter adapter);
    void adapterInit(FamilyFindAdapter adapter);
    void onResponse(boolean response);
    void sendArrayList(ArrayList<FamilyItem> familyItems);
    void makeDialog(String name);
    void setYesRegisterFam(boolean isOkay);

}
