package yaksok.dodream.com.yaksok_refactoring.model.Register_Family;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.MyPill.Presenter_MyPill;
import yaksok.dodream.com.yaksok_refactoring.presenter.Register_Fam_Presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyDelVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Register_Fam_Model implements IRegister_Presenter_To_FamModel {
    private static final String TAG = "Register_Fam_Model";

    private Register_Fam_Presenter presenter = new Register_Fam_Presenter();
    private UserService userService;
    private boolean isAddedFamily = false;
    private FamilyFindAdapter adapter;
    private ArrayList<FamilyItem> familyItems = new ArrayList<>();
    private boolean isOkayForFamily = false;
    private String second_user_id;
    private ArrayList<FamilyItem> registered_Fam = new ArrayList<>();
    public Retrofit retrofit;
    public DeleteService deleteService;


    public Register_Fam_Model(Register_Fam_Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void findFam(final String pn) {
        if (pn.equals("")) {
             presenter.makeToastMessage("전화번호를 입력하세요");
        }
        else {

             retrofit = new Retrofit.Builder()
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
                                    second_user_id = findFamilyVO.getResult().get(i).getUserId();
                                    familyItems.add(new FamilyItem(findFamilyVO.getResult().get(i).getNickName()))/*"/"+findFamilyVO.getResult().get(i).getUserId())*/;
                            }
                            presenter.makeDialog(findFamilyVO.getResult().get(0).getNickName(),second_user_id);
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
    private void onRegisterFamily(final String finalUser2_id){


                final FamilyVO familyVO = new FamilyVO();
                familyVO.setUser_1(User_Id.getUser_Id());
                familyVO.setUser_2(finalUser2_id);

                final FamilyItem familyItem = new FamilyItem();
                //code
                //201 : OK
                //403 : 삽입시 중복
                //500 : Server Error
                Log.d("eeeeee",familyVO.getUser_1()+familyVO.getUser_2());
                Call<BodyVO> call = userService.postRegisterFamily(familyVO);
                call.enqueue(new Callback<BodyVO>() {
                    @Override
                    public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                        BodyVO bodyVO = response.body();
                        assert bodyVO != null;

                        switch (bodyVO.getStatus()) {
                            case "201":
                                /*Log.d("112","" +familyItems.get(0).getName());
                                //registered_Fam.add(new FamilyItem(finalUser2_id));
                                Log.d("eeeeee2",registered_Fam.get(0).getName());*/
                                familyItem.setName(finalUser2_id);
                                //presenter.sendArrayList(registered_Fam);
                                presenter.onResponse2(true,familyItem);
                                presenter.makeToastMessage( "가족 추가가 되었습니다.");
                                break;
                            case "403":
                                presenter.makeToastMessage( "삽입시 중복이 됩니다.");
                                break;
                            case "500":
                                presenter.makeToastMessage( "서버 에러");
                                //Log.d("eeeee3",finalUser2_id+User_Id.getUser_Id());
                                break;
                        }

                    }

                    @Override
                    public void onFailure(Call<BodyVO> call, Throwable t) {
                        presenter.makeToastMessage(t.getMessage());
                    }
                });
            }



    @Override
    public void setYesRegisterFam(boolean isOkay,String id) {
        isOkayForFamily = isOkay;
        onRegisterFamily(id);
    }

    @Override
    public void setPreviousRegistered() {

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);


        Call<FindFamilyVO> findFamilyVOCall = userService.getConnectedFamilyInfo(User_Id.getUser_Id());
        findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
            @Override
            public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                FindFamilyVO findFamilyVO = response.body();

                if (findFamilyVO.getStatus().equals("200")) {

                    for(int i = 0; i < findFamilyVO.getResult().size();i++){
                        familyItems.add(new FamilyItem(findFamilyVO.getResult().get(i).getUserId()));

                    }
                    Log.e(TAG, "onResponse: "+ familyItems.size() );
                    presenter.sendArrayList(familyItems);
                    presenter.onResponse(true);

                } else if (findFamilyVO.getStatus().equals("204")) {
                   presenter.makeToastMessage( "상대의 계정이 존재하지 않습니다.");
                } else if (findFamilyVO.getStatus().equals("400")) {
                   presenter.makeToastMessage("잘못된 요청입니다.");
                } else if (findFamilyVO.getStatus().equals("500")) {
                   presenter.makeToastMessage("서버 오루 입니다..");
                }

            }


            @Override
            public void onFailure(Call<FindFamilyVO> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public void deleteFam(boolean isOkay, String id, final int position) {

        retrofit = new Retrofit.Builder()
                .baseUrl(deleteService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deleteService = retrofit.create(DeleteService.class);

        FamilyDelVO familyDelVO = new FamilyDelVO();
        familyDelVO.setUser_1(User_Id.getUser_Id());
        familyDelVO.setUser_2(id);

        Log.d("dddddd5",familyDelVO.getUser_1()+" "+familyDelVO.getUser_2());

        Call<FamilyBodyVO> delectionCall = deleteService.deleteBody(familyDelVO);

        delectionCall.enqueue(new Callback<FamilyBodyVO>() {
            @Override
            public void onResponse(Call<FamilyBodyVO> call, Response<FamilyBodyVO> response) {
                FamilyBodyVO familyBodyVO = response.body();
                Log.d("dddddd2",familyBodyVO.getStatus());
                if(familyBodyVO.getStatus().equals("201")){


                   //presenter.deleteResponse(true);


                    familyItems.remove(position);
                    presenter.sendArrayList(familyItems);
                    Log.d("aaaaa",""+familyItems.size());
                   presenter.onResponse3(true);
//                    adapter.notifyDataSetInvalidated();



//                    presenter.makeToastMessage("삭제");
//                    presenter.onResponse3(true);




                    // alreadyConnectedFamily();
                }else if(familyBodyVO.getStatus().equals("500")){
                    //Toast.makeText(getApplicationContext(),"서버 에러입니다.",Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onFailure(Call<FamilyBodyVO> call, Throwable t) {

            }
        });
    }

    @Override
    public void getAdapter(FamilyFindAdapter familyFindAdapter) {
        adapter = familyFindAdapter;
    }


}
