package yaksok.dodream.com.yaksok_refactoring.model.Settings;

import android.content.Context;

public interface MyPage_PresenterToModel {
    void onSecOut();
    void onChangePw(String id,String od_pw, String ch_pw);
    void getMyCotext(Context context);
}
