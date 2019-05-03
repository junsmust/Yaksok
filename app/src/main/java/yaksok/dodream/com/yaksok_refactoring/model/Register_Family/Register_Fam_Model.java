package yaksok.dodream.com.yaksok_refactoring.model.Register_Family;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;
import yaksok.dodream.com.yaksok_refactoring.presenter.Register_Fam_Presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Register_Fam_Model implements IRegister_Presenter_To_FamModel {

    private Register_Fam_Presenter presenter = new Register_Fam_Presenter();
    private UserService userService;
    private boolean isAddedFamily = false;
    private FamilyFindAdapter adapter;
    private ArrayList<FamilyItem> familyItems = new ArrayList<>();
    private boolean isOkayForFamily = false;


    public Register_Fam_Model(Register_Fam_Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void findFam(final String pn) {
        if (pn.equals("")) {
             presenter.makeToastMessage("전화번호를 입력하세요");
        }
        else {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(userService.API_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            userService = retrofit.create(UserService.class);

            Call<FindFamilyVO> findFamilyVOCall = userService.getUserList(pn, "phoneNumber");
            findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
                @Override
                public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                    final FindFamilyVO findFamilyVO = response.body();

                    if (findFamilyVO.getStatus().equals("200")) {
                        isAddedFamily = true;
                        for (int i = 0; i < findFamilyVO.getResult().size(); i++) {

                            Log.d("ddddddd",findFamilyVO.getResult().get(i).getNickName()+findFamilyVO.getResult().get(i).getUserId());
                                    familyItems.add(new FamilyItem(findFamilyVO.getResult().get(i).getNickName()+"/"+findFamilyVO.getResult().get(i).getUserId()));
                            }
                            presenter.makeDialog(findFamilyVO.getResult().get(0).getNickName());
                            /*if(isOkayForFamily){
                                Log.d("112","" +familyItems.get(0).getName());
                                presenter.sendArrayList(familyItems);
                                presenter.onResponse(true);
                            }
                            else{
                                presenter.onResponse(false);
                            }*/

                        }
                    else if (findFamilyVO.getStatus().equals("204")) {
                        presenter.makeToastMessage( "상대의 계정이 존재하지 않습니다.");
                    } else if (findFamilyVO.getStatus().equals("400")) {
                        presenter.makeToastMessage( "잘못된 요청입니다.");
                    } else if (findFamilyVO.getStatus().equals("500")) {
                        presenter.makeToastMessage( "서버 오루 입니다..");
                    }

                    }


                @Override
                public void onFailure(Call<FindFamilyVO> call, Throwable t) {

                }
            });
    }
    }

    @Override
    public void adapterInit(FamilyFindAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void setYesRegisterFam(boolean isOkay) {
        isOkayForFamily = isOkay;
    }
}
