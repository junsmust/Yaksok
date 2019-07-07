package yaksok.dodream.com.yaksok_refactoring.view.MyPill.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Dialog.Presenter_Dialog;
import yaksok.dodream.com.yaksok_refactoring.view.MyPill.MyPill_activity;

public class Custom_pill_delete_Dialog implements Dialog_PresenterToView{

    Context context;
    String pill_name,pill_data;
    int pillNo;
    Presenter_Dialog presenter_dialog;
    MyPill_activity myPill_activity;

    public Custom_pill_delete_Dialog(Context context){
        this.context = context;

    }


    public void callFunction(String name, String data, final int pillNo) {
        this.pill_name = name;
        this.pill_data = data;
        this.pillNo = pillNo;

        final Dialog dlg = new Dialog(context);

        presenter_dialog = new Presenter_Dialog(this);


        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.


        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.pill_delete_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button delete = (Button)dlg.findViewById(R.id.cd_add_category_submit_bt);
        final Button cancel = (Button)dlg.findViewById(R.id.cd_add_category_cancel_bt);


        myPill_activity = new MyPill_activity();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                Log.d("test...","삭제 눌림");
                presenter_dialog.myPillDelete(pillNo);
                dlg.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }

    @Override
    public void onMyPillDeleteRespoce(boolean MyPillResponse) {
        if(MyPillResponse){

        }

    }
}
