package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.SearchPill;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.SearchPill.PillSearchItem;

public interface SearchPillToPresenter {
    void searchpill(String dosagiName);
    void getPillList(ArrayList<PillSearchItem> arrayList);
    void onSearchResponse(boolean SearchResponse);
}
