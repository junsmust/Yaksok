package yaksok.dodream.com.yaksok_refactoring.view.MyPill;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;

public interface MyPill_PresenterToView {
    void onMyPillResponce(boolean MyPillResponse,int status);
    void myPillList(MyPillVO myPillVO);
    void onMyPillDeleteRespoce(boolean MyPillResponse);
}
