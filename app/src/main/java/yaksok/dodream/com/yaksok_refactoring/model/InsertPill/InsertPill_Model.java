package yaksok.dodream.com.yaksok_refactoring.model.InsertPill;

import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Presenter_InsertPill;

public class InsertPill_Model implements InsertPill_PresenterToModel {
    private Presenter_InsertPill presenter_insertPill;
    public InsertPill_Model(Presenter_InsertPill presenter_insertPill){
        this.presenter_insertPill = presenter_insertPill;
    }
}
