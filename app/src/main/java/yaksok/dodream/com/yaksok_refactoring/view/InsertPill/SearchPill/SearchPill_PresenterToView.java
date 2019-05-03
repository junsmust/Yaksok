package yaksok.dodream.com.yaksok_refactoring.view.InsertPill.SearchPill;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.SearchPill.PillSearchItem;

public interface SearchPill_PresenterToView {
    void getPillList(ArrayList<PillSearchItem> arrayList);
    void onSearchResponse(boolean SearchResponse);
}
