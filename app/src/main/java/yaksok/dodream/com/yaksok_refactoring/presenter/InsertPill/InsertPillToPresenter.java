package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill;

import android.content.Context;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public interface InsertPillToPresenter {
    void insertPill(InsertPill_Item insertPill_item);
    void onInsertResponse(boolean response, int status);
    void getFamilyList();
    void onFamilyResponce(boolean Responce);
    void myFamilyList(List<String> myFamilyList, List<String> myFamily_Id);
    void getMyContext(Context context);
}
