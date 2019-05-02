package yaksok.dodream.com.yaksok_refactoring.model.Register_Family;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;

public interface IRegister_Presenter_To_FamModel {

    void findFam(String pn);
    void adapterInit(FamilyFindAdapter adapter);
}
