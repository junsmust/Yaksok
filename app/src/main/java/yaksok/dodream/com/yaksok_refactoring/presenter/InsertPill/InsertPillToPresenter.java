package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public interface InsertPillToPresenter {
    void insertPill(InsertPill_Item insertPill_item);
    void onInsertResponse(boolean response);
    void getFamilyList();
    void onFamilyResponce(boolean Responce);
    void myFamilyList(List<String> myFamilyList, List<String> myFamily_Id);
}
