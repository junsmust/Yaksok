package yaksok.dodream.com.yaksok_refactoring.presenter.Find_Id;

public interface FindIDToPresenter {
    void getFindID(String phone);
    void onFindIDResponse(Boolean response,int status);
    void onMyID(String ID);
}
