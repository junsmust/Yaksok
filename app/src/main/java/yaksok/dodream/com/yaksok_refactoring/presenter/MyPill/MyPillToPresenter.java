package yaksok.dodream.com.yaksok_refactoring.presenter.MyPill;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;

public interface MyPillToPresenter {
    void getMyPill();
    void onMyPillResponce(boolean MyPillResponse);
    void myPillList(MyPillVO pillList);
    void myPillDelete(int pillNo);
    void onMyPillDeleteRespoce(boolean MyPillResponse);
}
