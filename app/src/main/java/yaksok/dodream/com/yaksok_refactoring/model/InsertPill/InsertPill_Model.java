package yaksok.dodream.com.yaksok_refactoring.model.InsertPill;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Presenter_InsertPill;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.SearchPill.Presenter_SearchPill;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class InsertPill_Model implements InsertPill_PresenterToModel {
    private Presenter_InsertPill presenter_insertPill;
    private static UserService userService;
    Retrofit retrofit;

    public InsertPill_Model(Presenter_InsertPill presenter_insertPill){
        this.presenter_insertPill = presenter_insertPill;
    }

    @Override
    public void InsertPill(InsertPill_Item insertPillItem) {
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<BodyVO> call = userService.postMyInserttPill(insertPillItem);
        call.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO statusVO = response.body();
                System.out.println("############" + statusVO.getStatus());
                if (statusVO.getStatus().equals("201")) {
                    presenter_insertPill.onInsertResponse(true);
                } else if (statusVO.getStatus().equals("402")) {
                    presenter_insertPill.onInsertResponse(false);
                }
                else if(statusVO.getStatus().equals("403")){
                    presenter_insertPill.onInsertResponse(false);
                }
                else if (statusVO.getStatus().equals("500")){
                    presenter_insertPill.onInsertResponse(false);
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });

    }
}
