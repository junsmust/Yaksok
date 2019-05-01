package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.SearchPill;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.PillSearchItem;
import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.SearchPill.SearchPill_Model;
import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.SearchPill.SearchPill_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.SearchPill.SearchPill_PresenterToView;

public class Presenter_SearchPill implements SearchPillToPresenter {
    private SearchPill_PresenterToView view;
    private SearchPill_PresenterToModel model;
    public Presenter_SearchPill(SearchPill_PresenterToView view){
        this.view = view;
        model = new SearchPill_Model(this);
    }

    @Override
    public void searchpill(String dosagiName) {
        model.searchpill(dosagiName);
    }

    @Override
    public void getPillList(ArrayList<PillSearchItem> arrayList) {
        view.getPillList(arrayList);
    }

    @Override
    public void onSearchResponse(boolean SearchResponse) {
        view.onSearchResponse(SearchResponse);
    }
}
