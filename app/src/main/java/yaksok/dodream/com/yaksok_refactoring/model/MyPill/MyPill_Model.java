package yaksok.dodream.com.yaksok_refactoring.model.MyPill;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;
import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class MyPill_Model implements MyPill_PresenterToModel {
    private static UserService userService;
    Retrofit retrofit;
    public MyPillVO myPillVO = null;
    Presenter_MyPill presenter_myPill;
    List<String> pillList = new ArrayList<String>();


    public MyPill_Model(Presenter_MyPill presenter_myPill){this.presenter_myPill = presenter_myPill;}

    @Override
    public void getMyPill_List() {
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<MyPillVO> call = userService.getMymediciens(User_Id.getUser_Id());
        call.enqueue(new Callback<MyPillVO>() {
            @Override
            public void onResponse(Call<MyPillVO> call, Response<MyPillVO> response) {
                myPillVO = response.body();
                //1System.out.println("############" + myMedicineResponseTypeVO.getStatus());
                if (myPillVO.getStatus().equals("200")) {
                    pillList.clear();
                    for(int i=0; i<myPillVO.getResult().size(); i++) {
                        //adapter.addItem(myPillVO.getResult().get(i).getName());
                       // myMedicineNo = myPillVO.getResult().get(i).getMyMedicineNo();
                       // Log.d("meddddi",""+myMedicineNo);
                        Log.d("status",String.valueOf(myPillVO.getStatus()));
                        pillList.add(myPillVO.getResult().get(i).getName());

                    }
                    presenter_myPill.myPillList(pillList);
                    presenter_myPill.onMyPillResponce(true);

                } else if (myPillVO.getStatus().equals("204")){
                    presenter_myPill.onMyPillResponce(false);
                    Log.d("status",String.valueOf(myPillVO.getStatus()));}

                else if (myPillVO.getStatus().equals("500")){
                    presenter_myPill.onMyPillResponce(false);
                    Log.d("status",String.valueOf(myPillVO.getStatus()));}

            }

            @Override
            public void onFailure(Call<MyPillVO> call, Throwable t) {

            }
        });
    }
}
