package yaksok.dodream.com.yaksok_refactoring.model.Register_Family;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;

public interface IRegister_Presenter_To_FamModel {

    void findFam(String pn);
    void setYesRegisterFam(boolean isOkay,String id);
    void setPreviousRegistered();

}
