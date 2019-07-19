package yaksok.dodream.com.yaksok_refactoring.presenter.Settings;

import android.content.Context;

public interface MyPageToPresenter {
    void onSecOut();
    void onSecOutResponse(boolean response);
    void onChangePW(String id,String od_pw,String ch_pw);
    void onChangeResponse(boolean response, int status);
    void getMyContext(Context context);
}
