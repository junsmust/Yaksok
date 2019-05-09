package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill;

import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public interface InsertPillToPresenter {
    void insertPill(InsertPill_Item insertPill_item);
    void onInsertResponse(boolean response);
}
