package yaksok.dodream.com.yaksok_refactoring.view.Find_Id;

public interface FindID_PresenterToView {
    void onFindIDResponse(Boolean response, int status);
    void onMyID(String ID);
}
