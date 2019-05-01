package yaksok.dodream.com.yaksok_refactoring.model.signup;

public interface IPresenterToSignUpModel {
    void validateId(String id);
    void validatePw(String pw1,String pw2);
    void validateEmail(String email1,String email2);

    void completeSignUp();
    void guessAge(String currentYear, String bornYear);
    void setBirth(String month,String day);

    void onSignupResponse(boolean SignUpResponse);
    void onError(String errorMsg);
    void onSignUp();
    void setName(String name);
    void isvalidatePhone(String pn);
}
