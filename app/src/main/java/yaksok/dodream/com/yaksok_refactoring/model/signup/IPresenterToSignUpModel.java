package yaksok.dodream.com.yaksok_refactoring.model.signup;

import android.content.Context;

import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;

public interface IPresenterToSignUpModel {
    void validateId(String id);
    void validatePw(String pw1);
    void validateEmail(String email1);

    void completeSignUp();
    void guessAge(String currentYear, String bornYear);
    void setBirth(String month,String day);

    void onSignupResponse(boolean SignUpResponse);
    void onError(String errorMsg);
    void onSignUp(User_Info_Model user_info_model);
    void setName(String name);
    void isvalidatePhone(String pn);
    void setPn(String pn);
    void setUserType(String userType);
    void setGeneralUserType();
    void setPn(String pn, String usertype);
    void getMyContext(Context context);

    void setSignUp(User_Info_Model user_info_model);

}
