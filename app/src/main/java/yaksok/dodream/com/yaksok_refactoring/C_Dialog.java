package yaksok.dodream.com.yaksok_refactoring;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class C_Dialog extends Dialog {

    UserService userService;

    public TextView text_tv;
    public Button ok_btn;

    public C_Dialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.log_dialog);     //다이얼로그에서 사용할 레이아웃입니다.

        text_tv = (TextView)findViewById(R.id.tv_dialog_text);
        ok_btn = (Button)findViewById(R.id.bt_dialog_ok);

    }

    public void setOYesnClick(){
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
