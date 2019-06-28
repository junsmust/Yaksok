package yaksok.dodream.com.yaksok_refactoring.view.InsertPill;

import java.util.List;

public interface InsertPill_PresenterToView {
    void onInsertResponse(boolean response, int status);
    void onFamilyResponce(boolean Responce);
    void myFamilyList(List<String> myFamilyList, List<String> myFamily_Id);
}
