package yaksok.dodream.com.yaksok_refactoring.view.signup;

public interface IPresenter_To_SignUp_View {

    void isValidatedUser(boolean isValidatedID, boolean isValidatedPW, boolean isValidatedEmail);

    void makeToastMessage(String message);
    void nonValidatedIl();
    void nonValidatedPW();
    void nonValidatedEmail();


    void onSignUoResponse(boolean loginResponse);
    void onError(String errorMsg);


}
