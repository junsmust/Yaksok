package yaksok.dodream.com.yaksok_refactoring.model.InsertPill;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Presenter_InsertPill;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.SearchPill.Presenter_SearchPill;
import yaksok.dodream.com.yaksok_refactoring.presenter.InsertPill.Sel_AlarmRecive.Presenter_Sel_AlarmRecive;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.Connected_Family;
import yaksok.dodream.com.yaksok_refactoring.vo.InsertPill_Item;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class InsertPill_Model implements InsertPill_PresenterToModel {
    private Presenter_InsertPill presenter_insertPill;
    private static UserService userService;
    private Presenter_Sel_AlarmRecive presenter_alarmRecive;
    List<String> familyList = new ArrayList<String>();
    List<String> familyList_id = new ArrayList<String>();
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

    @Override
    public void getFamilyList() {
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<Connected_Family> findFamilyVOCall = userService.getConnectedFamilyInfo(User_Id.getUser_Id());
        findFamilyVOCall.enqueue(new Callback<Connected_Family>() {
            @Override
            public void onResponse(Call<Connected_Family> call, Response<Connected_Family> response) {
                Connected_Family findFamilyVO = response.body();

                if (findFamilyVO.getStatus().equals("200")) {
                    Log.d("가족등록","실행됨" + findFamilyVO.getResult().size());
                    for(int i = 0; i < findFamilyVO.getResult().size();i++){
                        Log.d("f_id",findFamilyVO.getResult().get(i).getNickName()+"/"+findFamilyVO.getResult().get(i).getUserId());
                        // items.add(findFamilyVO.getResult().get(i).getNickName());
                        //   items_id.add(findFamilyVO.getResult().get(i).getUserId());
                        familyList.add(findFamilyVO.getResult().get(i).getNickName());
                        familyList_id.add(findFamilyVO.getResult().get(i).getUserId());
                    }
                    presenter_insertPill.myFamilyList(familyList,familyList_id);
                    presenter_insertPill.onFamilyResponce(true);

                    //  adapter.notifyDataSetChanged();
                } else if (findFamilyVO.getStatus().equals("204")) {
                    presenter_insertPill.onFamilyResponce((false));
                } else if (findFamilyVO.getStatus().equals("400")) {
                    presenter_insertPill.onFamilyResponce((false));
                } else if (findFamilyVO.getStatus().equals("500")) {
                    presenter_insertPill.onFamilyResponce((false));
                }

            }


            @Override
            public void onFailure(Call<Connected_Family> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
