package yaksok.dodream.com.yaksok_refactoring.model.Find_Id;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.presenter.Find_Id.Presenter_FindID;
import yaksok.dodream.com.yaksok_refactoring.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

import static android.support.constraint.Constraints.TAG;

public class FindID_Model implements FindID_PresenterToModel {
    Presenter_FindID presenter_findID;
    private UserService userService;
    public Retrofit retrofit;
    public FindID_Model(Presenter_FindID presenter_findID){this.presenter_findID = presenter_findID;}

    @Override
    public void getFindID(String phone) {
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<FindFamilyVO> findFamilyVOCall = userService.getUserList(phone, "phoneNumber","searchFamily");
        findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
            @Override
            public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                final FindFamilyVO findFamilyVO = response.body();

                // Log.e(TAG, "find"+findFamilyVO.getStatus()+findFamilyVO.getResult().getNickName()+"  "+findFamilyVO.getResult().getUserId());
                if (findFamilyVO.getStatus().equals("200")) {
                    presenter_findID.onMyID(findFamilyVO.getResult().getUserId());
                    presenter_findID.onFindIDResponse(true,200);
                   // Log.d("ddddddd",findFamilyVO.getResult().getNickName()+findFamilyVO.getResult().getUserId());
                }
                else if (findFamilyVO.getStatus().equals("204")) {
                    presenter_findID.onFindIDResponse(false,204);
                   // presenter.makeToastMessage( "상대의 계정이 존재하지 않습니다.");
                } else if (findFamilyVO.getStatus().equals("400")) {
                    presenter_findID.onFindIDResponse(false,400);
                  // presenter.makeToastMessage( "잘못된 요청입니다.");
                } else if (findFamilyVO.getStatus().equals("500")) {
                    presenter_findID.onFindIDResponse(false,500);
                   // presenter.makeToastMessage( "서버 오루 입니다..");
                }

            }


            @Override
            public void onFailure(Call<FindFamilyVO> call, Throwable t) {
                //Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
}
