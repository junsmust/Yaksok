package yaksok.dodream.com.yaksok_refactoring.model.Splash;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;
import yaksok.dodream.com.yaksok_refactoring.presenter.Splash.Presenter_Splash;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Splash_Model implements Splash_PresenterToModel {
    public Presenter_Splash presenter_splash;
    private static UserService userService;
    public BodyVO bodyVO;
    Retrofit retrofit;

    public Splash_Model(Presenter_Splash presenter_splash){this.presenter_splash = presenter_splash;}

    @Override
    public void getServerStatus() {
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<BodyVO> call = userService.getServerStatus();
        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                bodyVO = response.body();

//                Log.d("약 가져올때 번호",String.valueOf(myPillVO.getResult().get(0).getMedicineNo()));
                //1System.out.println("############" + myMedicineResponseTypeVO.getStatus());
                if (bodyVO.getStatus().equals("200")) {
                    presenter_splash.onServerStatus(true,bodyVO.getResult().getStatuses());
                    Log.d("status",String.valueOf(bodyVO.getStatus()));

                }else if (bodyVO.getStatus().equals("500")){
                    presenter_splash.onServerStatus(false,bodyVO.getResult().getStatuses());
                    Log.d("status",String.valueOf(bodyVO.getStatus()));}

            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }
}
