package yaksok.dodream.com.yaksok_refactoring.model.Settings;

import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.Settings.Presenter_MyPage;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserDeleteVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class MyPage_Model implements MyPage_PresenterToModel {
    Presenter_MyPage presenter_myPage;
    private static UserService userService;
    private static DeleteService deleteService;
    Retrofit retrofit;

    public MyPage_Model(Presenter_MyPage presenter_myPage){this.presenter_myPage = presenter_myPage;}

    @Override
    public void onSecOut() {
        retrofit = new Retrofit.Builder()
                .baseUrl(deleteService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deleteService = retrofit.create(DeleteService.class);

        UserDeleteVO userDeleteVO = new UserDeleteVO();
        userDeleteVO.setId(User_Id.getUser_Id());
        userDeleteVO.setUserType(User_Id.getType());
        Call<FamilyBodyVO> familyBodyVOCall = deleteService.deleteUser(userDeleteVO);
        familyBodyVOCall.enqueue(new Callback<FamilyBodyVO>() {
            @Override
            public void onResponse(Call<FamilyBodyVO> call, Response<FamilyBodyVO> response) {
                FamilyBodyVO familyBodyVO = response.body();

                if(familyBodyVO.getStatus().equals("201")){
                    presenter_myPage.onSecOutResponse(true);
                }
                if(familyBodyVO.getStatus().equals("500")){
                    presenter_myPage.onSecOutResponse(false);
                }
            }

            @Override
            public void onFailure(Call<FamilyBodyVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void onChangePw(String id, String opw, String ch_pw) {
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
                    presenter_myPage.onChangeResponse(true,1);
                    // presenter_insertPill.onInsertResponse(true);
                } else if (statusVO.getStatus().equals("401")) {
                    // presenter_insertPill.onInsertResponse(false);
                    presenter_myPage.onChangeResponse(true,2);
                }
                else if (statusVO.getStatus().equals("500")){
                    // presenter_insertPill.onInsertResponse(false);
                    presenter_myPage.onChangeResponse(false,0);
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }
}
