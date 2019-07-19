package yaksok.dodream.com.yaksok_refactoring.presenter.MyPill;

import android.content.Context;

import java.util.List;

import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;

public interface MyPillToPresenter {
    void getMyPill();
    void onMyPillResponce(boolean MyPillResponse, int status);
    void myPillList(MyPillVO pillList);
    void myPillDelete(int pillNo);
    void onMyPillDeleteRespoce(boolean MyPillResponse);
    void getMyContext(Context context);
}
