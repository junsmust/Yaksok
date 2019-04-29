package yaksok.dodream.com.yaksok_refactoring.presenter.MyPill;

import java.util.List;

public interface MyPillToPresenter {
    void getMyPill();
    void onMyPillResponce(boolean MyPillResponse);
    void myPillList(List<String> pillList);
}
