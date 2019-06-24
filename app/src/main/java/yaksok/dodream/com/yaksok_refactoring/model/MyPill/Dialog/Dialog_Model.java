package yaksok.dodream.com.yaksok_refactoring.model.MyPill.Dialog;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Dialog.Presenter_Dialog;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteMyMeidicineVO;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.MyPillVO;

public class Dialog_Model implements Dialog_PresenterToModel{

    Retrofit retrofit;
    public MyPillVO myPillVO = null;
    public DeleteService deleteService;

    Presenter_Dialog presenter_dialog;

    public Dialog_Model(Presenter_Dialog presenter_dialog){this.presenter_dialog = presenter_dialog;}

    @Override
    public void myPillDelete(int pillNo) {
        retrofit = new Retrofit.Builder()
                .baseUrl(deleteService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deleteService = retrofit.create(DeleteService.class);

        DeleteMyMeidicineVO myMedicineNoVO1 = new DeleteMyMeidicineVO();
        Log.d("약 pillNo",String.valueOf(pillNo));
        myMedicineNoVO1.setMyMedicineNo(pillNo);

        Call<FamilyBodyVO> myMedicineNoVOCall = deleteService.deleteMyMedicine(myMedicineNoVO1);
        Log.d("약 넘버",String.valueOf(myMedicineNoVO1.getMyMedicineNo()));
        myMedicineNoVOCall.enqueue(new Callback<FamilyBodyVO>() {
            @Override
            public void onResponse(Call<FamilyBodyVO> call, Response<FamilyBodyVO> response) {
                FamilyBodyVO meDiDelete = response.body();

                if(meDiDelete.getStatus().equals("201")){
                    presenter_dialog.onMyPillDeleteRespoce(true);
                    Log.d("약 삭제 상태", "True");
                }
                else if(meDiDelete.getStatus().equals("500")){
                    presenter_dialog.onMyPillDeleteRespoce(false);
                    Log.d("약 삭제 상태", "False");
                }
            }

            @Override
            public void onFailure(Call<FamilyBodyVO> call, Throwable t) {

            }
        });
    }
}
