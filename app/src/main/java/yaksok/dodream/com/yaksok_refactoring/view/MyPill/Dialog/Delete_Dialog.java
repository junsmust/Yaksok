package yaksok.dodream.com.yaksok_refactoring.view.MyPill.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Dialog.Presenter_Dialog;

public class Delete_Dialog extends Dialog implements View.OnClickListener,Dialog_PresenterToView{

    private Button positiveButton;
    private Button negativeButton;
    private Context context;
    String p_name, p_data;
    int p_pill_no;
    Presenter_Dialog presenter_dialog;

    private CustomDialogListener customDialogListener;
    public Delete_Dialog(Context context) {
        super(context);
        this.context = context;
    }


    //인터페이스 설정
    public interface CustomDialogListener{
        void onPositiveClicked(boolean isOk);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(String name, String data, int pill_No,CustomDialogListener customDialogListener){
        this.p_name = name;
        this.p_data = data;
        this.p_pill_no = pill_No;
        this.customDialogListener = customDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pill_delete_dialog);

        presenter_dialog  = new Presenter_Dialog(this);

        //init
        positiveButton = (Button)findViewById(R.id.cd_add_category_submit_bt);
        negativeButton = (Button)findViewById(R.id.cd_add_category_cancel_bt);

        //버튼 클릭 리스너 등록
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cd_add_category_submit_bt: //확인 버튼을 눌렀을 때
                presenter_dialog.myPillDelete(p_pill_no);
                //인터페이스의 함수를 호출하여 변수에 저장된 값들을 Activity로 전달
                customDialogListener.onPositiveClicked(true);
                dismiss();
                break;
            case R.id.cd_add_category_cancel_bt: //취소 버튼을 눌렀을 때
                cancel();
                break;
        }
    }

    @Override
    public void onMyPillDeleteRespoce(boolean MyPillResponse) {

    }






}