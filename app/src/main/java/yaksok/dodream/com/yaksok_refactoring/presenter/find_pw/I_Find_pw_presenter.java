package yaksok.dodream.com.yaksok_refactoring.presenter.find_pw;

public interface I_Find_pw_presenter{
    void onResponse(boolean onSuccess,String message);
    void findPW(String id, String email);

}
