package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Sel_AlarmRecive;

import java.util.List;

public interface Sel_AlarmReciveToPresenter {
    void getFamilyList();
    void onFamilyResponce(boolean Responce);
    void myFamilyList(List<String> myFamilyList, List<String> myFamily_Id);
}
