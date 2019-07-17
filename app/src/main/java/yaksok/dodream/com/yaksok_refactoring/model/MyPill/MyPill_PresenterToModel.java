package yaksok.dodream.com.yaksok_refactoring.model.MyPill;

import android.content.Context;

public interface MyPill_PresenterToModel {
    void getMyPill_List();
    void myPillDelete(int pillNo);
    void getMyContext(Context context);
}
