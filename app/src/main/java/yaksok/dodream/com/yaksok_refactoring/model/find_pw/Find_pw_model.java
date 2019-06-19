package yaksok.dodream.com.yaksok_refactoring.model.find_pw;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.presenter.find_pw.Find_pw_presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Find_pw_model implements I_Find_pw_model {
    Find_pw_presenter pw_presenter;
    private static UserService userService;

    public Find_pw_model(Find_pw_presenter pw_presenter) {
        this.pw_presenter = pw_presenter;
    }

    @Override
    public void findPw(String id, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<BodyVO> bodyVOCall = userService.putFindUserPassword(id,email);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                if(bodyVO.getStatus().equals("200")){
                    pw_presenter.onResponse(true);
                }else if(bodyVO.getStatus().equals("403")){
                    pw_presenter.onResponse(false);
                }else{
                    Log.e( "error", "서버에러");
                }

            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });


    }
}
