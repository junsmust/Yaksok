package yaksok.dodream.com.yaksok_refactoring;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Custom_pill_delete_Dialog{
    Context context;
    String pill_name,pill_data;
    int pillNo;

    public Custom_pill_delete_Dialog(Context context){
        this.context = context;

    }

    public void callFunction(String name, String data, int pillNo) {
        this.pill_name = name;
        this.pill_data = data;
        this.pillNo = pillNo;

        final Dialog dlg = new Dialog(context);


        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.


        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.pill_delete_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = (TextView)dlg.findViewById(R.id.cd_add_category_title_tv);
        final TextView name1 = (TextView)dlg.findViewById(R.id.cd_add_category1_tv);
        final TextView date = (TextView)dlg.findViewById(R.id.cd_add_category2_tv);
        final Button delete = (Button)dlg.findViewById(R.id.cd_add_category_submit_bt);
        final Button cancel = (Button)dlg.findViewById(R.id.cd_add_category_cancel_bt);

        name1.setText("약 이름 : "+pill_name);
        date.setText("등록날짜 : "+pill_data);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
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

}
