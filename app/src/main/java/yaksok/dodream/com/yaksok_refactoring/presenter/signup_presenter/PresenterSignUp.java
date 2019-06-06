package yaksok.dodream.com.yaksok_refactoring.presenter.signup_presenter;

import yaksok.dodream.com.yaksok_refactoring.model.signup.IPresenterToSignUpModel;
import yaksok.dodream.com.yaksok_refactoring.model.signup.SignUpModel;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;
import yaksok.dodream.com.yaksok_refactoring.view.signup.IPresenter_To_SignUp_View;

public class PresenterSignUp implements ITo_Presenter_SignUp {
    IPresenterToSignUpModel model;
    IPresenter_To_SignUp_View view;

    public PresenterSignUp(IPresenter_To_SignUp_View view) {
        this.view = view;
        model = new SignUpModel(this);
    }

    @Override
    public void validateId(String id) {
        model.validateId(id);
    }

    @Override
    public void validatePw(String pw1,String pw2) {
        model.validatePw(pw1,pw2);
    }

    @Override
    public void validateEmail(String email1,String email2) {
        model.validateEmail(email1,email2);
    }


    @Override
    public void isValidatedUser(boolean isValidatedID, boolean isValidatedPW, boolean isValidatedEmail) {
        view.isValidatedUser(isValidatedID,isValidatedPW,isValidatedEmail);
    }

    @Override
    public void isValdiatedUser() {
        model.completeSignUp();
    }

    @Override
    public void makeToastMessage(String message) {
        view.makeToastMessage(message);
    }

    @Override
    public void nonValidatedIl() {
        view.nonValidatedIl();
    }

    @Override
    public void nonValidatedPW() {
        view.nonValidatedPW();
    }

    @Override
    public void nonValidatedEmail() {
        view.nonValidatedEmail();
    }

    @Override
    public void guessAge(String currentYear, String bornYear) {
        model.guessAge(currentYear,bornYear);
    }

    @Override
    public void setBirth(String month, String day) {
        model.setBirth(month,day);
    }

    @Override
    public void onSignupResponse(boolean SignUpResponse) {
        view.onSignUoResponse(SignUpResponse);
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onSignUp() {

    }

    @Override
    public void setName(String name) {
        model.setName(name);
    }

    @Override
    public void isvalidatePhone(String pn) {
        model.isvalidatePhone(pn);
    }

    @Override
    public void setPhone(String pn) {
        model.setPn(pn);
    }

    @Override
    public void setPhone(String pn, String usertype) {
        model.setPn(pn,usertype);
    }


    @Override
    public void setUserType(String userType) {
        model.setUserType("G");
    }

    @Override
    public void setGeneralUserType() {
        model.setGeneralUserType();
    }

    @Override
    public void isValidedId(boolean isId) {
        view.isValidedId(isId);
    }

    @Override
    public void isValidedPW(boolean isPw) {
        view.isValidedPW(isPw);
    }

    @Override
    public void isValidedEmail(boolean isEmail) {
        view.isValidedEmail(isEmail);
    }

    @Override
    public void isValidedPn(boolean isPn) {
        view.isValidedPn(isPn);
    }


}
