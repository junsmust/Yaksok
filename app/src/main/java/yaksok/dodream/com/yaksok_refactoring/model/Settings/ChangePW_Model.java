package yaksok.dodream.com.yaksok_refactoring.model.Settings;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Settings.Presenter_ChangePW;
import yaksok.dodream.com.yaksok_refactoring.presenter.Settings.Presenter_MyPage;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;
import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class ChangePW_Model implements ChangePW_PresenterToModel {

    Presenter_ChangePW presenter_changePW;
    private static UserService userService;
    Retrofit retrofit;

    public ChangePW_Model(Presenter_ChangePW presenter_changePW){this.presenter_changePW=presenter_changePW;}

    @Override
    public void onChangePw(String id, String opw,String ch_pw) {
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<BodyVO> call = userService.putChangePW(id,opw,ch_pw);
        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO statusVO = response.body();
                System.out.println("############123" + statusVO.getStatus());
                if (statusVO.getStatus().equals("200")) {
                    presenter_changePW.onChangeResponse(true,1);
                   // presenter_insertPill.onInsertResponse(true);
                } else if (statusVO.getStatus().equals("401")) {
                   // presenter_insertPill.onInsertResponse(false);
                    presenter_changePW.onChangeResponse(true,2);
                }
                else if (statusVO.getStatus().equals("500")){
                   // presenter_insertPill.onInsertResponse(false);
                    presenter_changePW.onChangeResponse(false,0);
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }
}
