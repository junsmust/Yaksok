package yaksok.dodream.com.yaksok_refactoring;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class CustomDialog extends Dialog {

    UserService userService;

    public TextView title_tv,message_tv;
    public Button ok_btn,no_btn;

    public CustomDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.dialog_register_or_not);     //다이얼로그에서 사용할 레이아웃입니다.

        title_tv = (TextView)findViewById(R.id.title_tv);
        message_tv = (TextView)findViewById(R.id.message_tv);
        ok_btn = (Button)findViewById(R.id.ok_btn);
        no_btn = (Button)findViewById(R.id.no_btn);






    }

    public void setOYesnClick(){
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void setOnNoClick(){
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"악", Toast.LENGTH_LONG).show();
                dismiss();   //다이얼로그를 닫는 메소드입니다.



            }
        });
    }
    /*private void onRegisterFamily(final String finalUser2_id){

        Log.d("@@@@@@@@",finalUser2_id);
        int index1 = finalUser2_id.indexOf('(');
        int index2 = finalUser2_id.indexOf(')');

        final String id2 = finalUser2_id.substring(index1+1,index2);
        final FamilyVO familyVO = new FamilyVO();
        familyVO.setUser_1(User_Id.getUser_Id());
        familyVO.setUser_2(id2);

        final FamilyItem familyItem = new FamilyItem();
        //code
        //201 : OK
        //403 : 삽입시 중복
        //500 : Server Error
        Log.d("eeeeee",familyVO.getUser_1()+familyVO.getUser_2());
        Call<BodyVO> call = userService.postRegisterFamily(familyVO);
        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                assert bodyVO != null;

                switch (bodyVO.getStatus()) {
                    case "201":
                        familyItem.setFirst_name(user_last_name);
                        familyItem.setName(finalUser2_id);
                        familyItem.setUser_pn(user_pn);
                        Log.d("setName",familyItem.getName());
                        presenter.onResponse2(true,familyItem);
                        presenter.makeToastMessage( "가족 추가가 되었습니다.");
                        break;
                    case "403":
                        presenter.makeToastMessage( "삽입시 중복이 됩니다.");
                        break;
                    case "500":
                        presenter.makeToastMessage( "서버 에러");
                        //Log.d("eeeee3",finalUser2_id+User_Id.getUser_Id());
                        break;
                }

            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                presenter.makeToastMessage(t.getMessage());
            }
        });
    }*/

}
