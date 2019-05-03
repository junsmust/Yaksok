package yaksok.dodream.com.yaksok_refactoring.view.InsertPill.Sel_AlarmRecive;

import java.util.List;

public interface Sel_AlarmRecive_PresenterToView {
    void onFamilyResponce(boolean Responce);
    void myFamilyList(List<String> myFamilyList, List<String> myFamily_Id);
}
