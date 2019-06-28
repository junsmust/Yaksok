package yaksok.dodream.com.yaksok_refactoring.presenter.signup_presenter;

import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;

public interface ITo_Presenter_SignUp {
    void validateId(String id);
    void validatePw(String pw1);
    void validateEmail(String email1);
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
    void setPhone(String pn);
    void setPhone(String pn,String usertype);
    void setUserType(String userType);
    void setGeneralUserType();
    void isValidedId(boolean isId);
    void isValidedPW(boolean isPw);
    void isValidedEmail(boolean isEmail);
    void isValidedPn(boolean isPn);

    void setSignUp(User_Info_Model user_info_model);



}
