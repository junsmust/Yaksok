package yaksok.dodream.com.yaksok_refactoring.model.InsertPill;

import android.content.Context;

import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public interface InsertPill_PresenterToModel {
    void InsertPill(InsertPill_Item insertPillItem);
    void getFamilyList();
    void getMyContext(Context context);
}
