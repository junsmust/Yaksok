package yaksok.dodream.com.yaksok_refactoring.view.Find_Id;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import yaksok.dodream.com.yaksok_refactoring.ApplicationBase;
import yaksok.dodream.com.yaksok_refactoring.C_Dialog;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.Find_Id.Presenter_FindID;
import yaksok.dodream.com.yaksok_refactoring.presenter.find_pw.Find_pw_presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class ID_find_layout extends ApplicationBase implements FindID_PresenterToView{

    C_Dialog dialog;
    String id;
    EditText et_phone;
    Button find_btn;
    TextView tv_acton_name;
    FrameLayout fb;
    Presenter_FindID presenter_findID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_find_layout);

        //actionBarSetting
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_under_line));

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.action_bar_develop, null);

        tv_acton_name = view.findViewById(R.id.back_layout_name_delvel);
        fb = view.findViewById(R.id.fb_back_layout_back_devel);

        tv_acton_name.setText("아이디 찾기");

        dialog = new C_Dialog(this);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view,layoutParams);

        presenter_findID = new Presenter_FindID(this);

        find_btn = (Button)findViewById(R.id.find_ID_btn);
        et_phone = (EditText)findViewById(R.id.et_find_id_phone);


        find_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_phone.getText().toString().equals("")){
                    phoneNull("전화번호를 입력하세요");
                }
                else{
                    presenter_findID.getFindID(et_phone.getText().toString());
                }
            }
        });


    }

    private void findID(){
        dialog.text_tv.setText("회원님의 ID는"+"\n\""+id+"\" 입니다.");

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    private void phoneNull(String text){
        dialog.text_tv.setText(text);

        dialog.show();


        dialog.ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    @Override
    public void onFindIDResponse(Boolean response, int status) {
        if(response&&status==200){
            findID();
        }
        else if(!response && status==204){
            phoneNull("계정이 존재하지 않습니다.");
        }
        else if(!response && status==400){
            phoneNull("잘못된 요청입니다.");
        }
        else{
            phoneNull("서버오류");
        }

    }

    @Override
    public void onMyID(String ID) {
        this.id = ID;
    }
}
