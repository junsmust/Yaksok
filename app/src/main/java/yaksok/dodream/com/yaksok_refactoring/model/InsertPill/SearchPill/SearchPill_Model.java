package yaksok.dodream.com.yaksok_refactoring.model.InsertPill.SearchPill;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.SearchPill.PillSearchItem;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.SearchPill.Presenter_SearchPill;
import yaksok.dodream.com.yaksok_refactoring.vo.SearchPillListVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class SearchPill_Model implements SearchPill_PresenterToModel{

    private static UserService userService;
    private Presenter_SearchPill presenter_searchPill;
    Retrofit retrofit;
    private ArrayList<PillSearchItem> pillSearchItems = new ArrayList<PillSearchItem>();

    public SearchPill_Model(Presenter_SearchPill presenter_searchPill){
        this.presenter_searchPill = presenter_searchPill;
    }

    @Override
    public void searchpill(String dosagiName) {
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<SearchPillListVO> call = userService.getSearchPillList(dosagiName, "search");
        call.enqueue(new Callback<SearchPillListVO>() {
            @Override
            public void onResponse(Call<SearchPillListVO> call, Response<SearchPillListVO> response) {
                SearchPillListVO mVO = response.body();
                if (mVO.getStatus().equals("200")) {
                    pillSearchItems.clear();
                    for (int i = 0; i < mVO.getResult().size(); i++) {
                        System.out.println("@@@@@@" + mVO.getResult().get(i).getMedicineNo());
                        pillSearchItems.add(new PillSearchItem(mVO.getResult().get(i).getMedicineNo(), mVO.getResult().get(i).getName(), mVO.getResult().get(i).getElement()));
                       // adapter.addItem(mVO.getResult().get(i).getMedicineNo(), mVO.getResult().get(i).getName(), mVO.getResult().get(i).getElement());
                       // adapter.notifyDataSetChanged();
                    }
                    presenter_searchPill.getPillList(pillSearchItems);
                    presenter_searchPill.onSearchResponse(true);
                } else if (mVO.getStatus().equals("204")) {
                    presenter_searchPill.onSearchResponse(false);
                }
            }

            @Override
            public void onFailure(Call<SearchPillListVO> call, Throwable t) {
                System.out.println(t.fillInStackTrace().getMessage());
            }
        });
    }
}
