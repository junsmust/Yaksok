package yaksok.dodream.com.yaksok_refactoring.presenter.Splash;

public interface SplashToPresenter {

    void getServerStatus();
    void onServerStatus(boolean status,String isFixing);
}
