package yaksok.dodream.com.yaksok_refactoring.presenter.Main;

public interface MainToPresenter {
    void getNearTimePill();
    void onMyNearPillResponce(boolean MyNearPillResponse);
    void MyNearTime(int nearTime_sec, boolean pillTime_day);
}
