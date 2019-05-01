package yaksok.dodream.com.yaksok_refactoring.presenter.signup_presenter;

public interface ITo_Presenter_SignUp {
    void validateId(String id);
    void validatePw(String pw1,String pw2);
    void validateEmail(String email1,String email2);
    void isValidatedUser(boolean isValidatedID, boolean isValidatedPW, boolean isValidatedEmail);
    void isValdiatedUser();
    void makeToastMessage(String message);
    void nonValidatedIl();
    void nonValidatedPW();
    void nonValidatedEmail();
    void guessAge(String currentYear, String bornYear);
    void setBirth(String month,String day);
    void onSignupResponse(boolean SignUpResponse);
    void onError(String errorMsg);
    void onSignUp();
    void setName(String name);
    void isvalidatePhone(String pn);



}
