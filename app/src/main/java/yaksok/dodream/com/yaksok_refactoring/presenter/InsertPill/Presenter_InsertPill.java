package yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill;

import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.InsertPill_Model;
import yaksok.dodream.com.yaksok_refactoring.model.InsertPill.InsertPill_PresenterToModel;
import yaksok.dodream.com.yaksok_refactoring.view.InsertPill.InsertPill_PresenterToView;
import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;

public class Presenter_InsertPill implements InsertPillToPresenter{

    private InsertPill_PresenterToView view;
    private InsertPill_PresenterToModel model;

    public Presenter_InsertPill(InsertPill_PresenterToView view){
        this.view = view;
        model = new InsertPill_Model(this);
    }

    @Override
    public void insertPill(InsertPill_Item insertPill_item) {
        model.InsertPill(insertPill_item);
    }

    @Override
    public void onInsertResponse(boolean response) {
        view.onInsertResponse(response);
    }
}
