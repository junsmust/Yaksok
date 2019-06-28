package yaksok.dodream.com.yaksok_refactoring.presenter.find_pw;

import yaksok.dodream.com.yaksok_refactoring.model.find_pw.Find_pw_model;
import yaksok.dodream.com.yaksok_refactoring.model.find_pw.I_Find_pw_model;
import yaksok.dodream.com.yaksok_refactoring.view.find_pw.I_find_pw;

public class Find_pw_presenter implements I_Find_pw_presenter {
    I_Find_pw_model model;
    I_find_pw view;

    public Find_pw_presenter(I_find_pw view) {
        this.view = view;
        model = new Find_pw_model(this);
    }

    @Override
    public void onResponse(boolean onSuccess,String message) {
        view.onResponse(onSuccess,message);
    }

    @Override
    public void findPW(String id, String email) {
        model.findPw(id,email);
    }
}
