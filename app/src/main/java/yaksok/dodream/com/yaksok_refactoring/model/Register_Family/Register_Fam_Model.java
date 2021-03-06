package yaksok.dodream.com.yaksok_refactoring.model.Register_Family;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.NullHostNameVerifier;
import yaksok.dodream.com.yaksok_refactoring.SSLUtil;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.presenter.family_register.Register_Fam_Presenter;
import yaksok.dodream.com.yaksok_refactoring.view.addFamily.Register_Family;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.Connected_Family;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyBodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyDelVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Register_Fam_Model implements IRegister_Presenter_To_FamModel {
    private static final String TAG = "Register_Fam_Model";

    private Register_Fam_Presenter presenter;
    private UserService userService;
    private boolean isAddedFamily = false;
    private FamilyFindAdapter adapter;
    private ArrayList<FamilyItem> familyItems = new ArrayList<>();
    private boolean isOkayForFamily = false;
    private String second_user_id;
    private ArrayList<FamilyItem> registered_Fam = new ArrayList<>();
    public Retrofit retrofit;
    public DeleteService deleteService;
    private String user_last_name,user_name,user_pn;
    private ArrayList<String> id_list = new ArrayList<>();
    private ArrayList<String> regi_list = new ArrayList<>();
    private int index;
    private boolean isAlreadyAdded = false;
    Context context;


    public Register_Fam_Model(Register_Fam_Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void findFam(final String pn) {
        if (pn.equals("")) {
             presenter.makeErrorDialog("전화번호를 입력하세요");
        }
        else {

             retrofit = new Retrofit.Builder()
                    .baseUrl(userService.API_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            userService = retrofit.create(UserService.class);

            Call<FindFamilyVO> findFamilyVOCall = userService.getUserList(pn, "phoneNumber","searchFamily");
            findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
                @Override
                public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                    final FindFamilyVO findFamilyVO = response.body();

                   // Log.e(TAG, "find"+findFamilyVO.getStatus()+findFamilyVO.getResult().getNickName()+"  "+findFamilyVO.getResult().getUserId());



                    if (findFamilyVO.getStatus().equals("200")) {
                        isAddedFamily = true;
                        second_user_id = findFamilyVO.getResult().getUserId();

                        if(findFamilyVO.getResult().getPhoneNumber().equals(User_Id.getPhone_No())){
                            presenter.makeErrorDialog("자신의 번호는"+"\n"+"등록할 수 없습니다.");
                            return;
                        }
                        /*else if(setPreviousRegistered2(User_Id.getUser_Id(),pn)){

                        }*/

                        else{
                            Log.e(TAG, "oncccccccc "+findFamilyVO.getResult().getNickName()+"("+findFamilyVO.getResult().getUserId()+")" +second_user_id);

                            presenter.makeDialog(findFamilyVO.getResult().getNickName(),second_user_id);
                            //familyItems.add(new FamilyItem(findFamilyVO.getResult().getNickName(),findFamilyVO.getResult().getNickName().substring(0,1),findFamilyVO.getResult().getPhoneNumber()));
                            user_last_name = findFamilyVO.getResult().getNickName().substring(0,1);
                            user_name = findFamilyVO.getResult().getNickName();
                            user_pn = findFamilyVO.getResult().getPhoneNumber().substring(0,3)+"-"+findFamilyVO.getResult().getPhoneNumber().substring(3,7)+"-"+findFamilyVO.getResult().getPhoneNumber().substring(7);


                            Log.d("ddddddd",findFamilyVO.getResult().getNickName()+findFamilyVO.getResult().getUserId());
                        }


                        }
                    else if (findFamilyVO.getStatus().equals("204")) {
                        presenter.makeErrorDialog( "상대의 계정이 "+"\n"+"존재하지 않습니다.");
                    } else if (findFamilyVO.getStatus().equals("400")) {
                        presenter.makeErrorDialog( "잘못된 번호입니다.");
                    } else if (findFamilyVO.getStatus().equals("500")) {
                        presenter.makeErrorDialog( "서버 오류 입니다..");
                    }

                    }


                @Override
                public void onFailure(Call<FindFamilyVO> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getMessage());
                }
            });
    }
    }
    private void onRegisterFamily(final String finalUser2_id, final String name){

        Log.d("@@@@@@@@",finalUser2_id);


                final FamilyVO familyVO = new FamilyVO();
                familyVO.setUser_1(User_Id.getUser_Id());
                familyVO.setUser_2(finalUser2_id);

                final FamilyItem familyItem = new FamilyItem();
                //code
                //201 : OK
                //403 : 삽입시 중복
                //500 : Server Error
        retrofit = new Retrofit.Builder()
                .baseUrl(UserService.POST_URL)
                .client( new OkHttpClient.Builder()
                        .sslSocketFactory(SSLUtil.getPinnedCertSslSocketFactory(context))  //ssl
                        .hostnameVerifier(new NullHostNameVerifier())                       //ssl HostName Pass
                        .build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

                Log.d("eeeeee",familyVO.getUser_1()+familyVO.getUser_2());
                Call<BodyVO> call = userService.postRegisterFamily(familyVO);
                call.enqueue(new Callback<BodyVO>() {
                    @Override
                    public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                        BodyVO bodyVO = response.body();
                        assert bodyVO != null;

                        switch (bodyVO.getStatus()) {
                            case "201":

                                Register_Family.none_register.setVisibility(View.GONE);
                                familyItem.setFirst_name(user_last_name);
                                familyItem.setName(name);
                                familyItem.setUser_pn(user_pn);

                                id_list.add(finalUser2_id);
//                                                    Log.d("setName",familyItem.getName());
                               presenter.onResponse2(true,familyItem);
                               presenter.makeErrorDialog( "가족등록 "+"\n"+"완료되었습니다.");
                               familyItems.add(familyItem);



                                break;
                            case "403":
                                presenter.makeErrorDialog( "전화번호 중복입니다.");
                                break;
                            case "500":
                                presenter.makeErrorDialog( "서버 에러입니다.");
                                //Log.d("eeeee3",finalUser2_id+User_Id.getUser_Id());
                                break;
                        }

                    }

                    @Override
                    public void onFailure(Call<BodyVO> call, Throwable t) {
                    }
                });
            }



    @Override
    public void setYesRegisterFam(boolean isOkay,String id,String name) {
        isOkayForFamily = isOkay;
        onRegisterFamily(id,name);
    }

    @Override
    public void setPreviousRegistered() {

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
                    Register_Family.none_register.setVisibility(View.GONE);
                    for(int i = 0; i < findFamilyVO.getResult().size();i++){
                        FamilyItem item = new FamilyItem(findFamilyVO.getResult().get(i).getNickName(),findFamilyVO.getResult().get(i).getNickName().substring(0,1),
                                findFamilyVO.getResult().get(i).getPhoneNumber().substring(0,3)+"-"+findFamilyVO.getResult().get(i).getPhoneNumber().substring(3,7)+"-"+findFamilyVO.getResult().get(i).getPhoneNumber().substring(7));

                        id_list.add(findFamilyVO.getResult().get(i).getUserId());
                        familyItems.add(item);
                    }
                    Log.e(TAG, "onResponse: "+ familyItems.size() );
                    presenter.sendArrayList(familyItems);
                    presenter.onResponse(true);
                } else if (findFamilyVO.getStatus().equals("204")) {
                   presenter.makeToastMessage("상대의 계정이 존재하지 않습니다.");
                } else if (findFamilyVO.getStatus().equals("400")) {
                   presenter.makeErrorDialog("잘못된 요청입니다.");
                } else if (findFamilyVO.getStatus().equals("500")) {
                   presenter.makeErrorDialog("서버 오루 입니다..");
                }

            }


            @Override
            public void onFailure(Call<Connected_Family> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


    @Override
    public void deleteFam(boolean isOkay, String id, final int position) {

        retrofit = new Retrofit.Builder()
                .baseUrl(UserService.POST_URL)
                .client( new OkHttpClient.Builder()
                        .sslSocketFactory(SSLUtil.getPinnedCertSslSocketFactory(context))  //ssl
                        .hostnameVerifier(new NullHostNameVerifier())                       //ssl HostName Pass
                        .build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deleteService = retrofit.create(DeleteService.class);
/*
        FamilyDelVO familyDelVO = new FamilyDelVO();
        familyDelVO.setUser_1(User_Id.getUser_Id());
        familyDelVO.setUser_2(id_list.get(position));*/

      /*  Log.d("dddddd5",familyDelVO.getUser_1()+" "+familyDelVO.getUser_2());*/

        Call<FamilyBodyVO> delectionCall = deleteService.deleteBody(User_Id.getUser_Id(),id_list.get(position));

        delectionCall.enqueue(new Callback<FamilyBodyVO>() {
            @Override
            public void onResponse(Call<FamilyBodyVO> call, Response<FamilyBodyVO> response) {
                FamilyBodyVO familyBodyVO = response.body();
                Log.d("dddddd2",familyBodyVO.getStatus());
                if(familyBodyVO.getStatus().equals("200")){




                    familyItems.remove(position);
                    Log.e(TAG, "delete "+id_list.size());
                    id_list.remove(position);
                    presenter.sendArrayList(familyItems);
                    Log.d("aaaaa",""+familyItems.size());
                   presenter.onResponse3(true);
                    Log.d("aaaaa3",""+familyItems.size());

                    if (id_list.size() == 0){
                        Register_Family.none_register.setVisibility(View.VISIBLE);
                    }

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

    @Override
    public void getArrayList(ArrayList<FamilyItem> familyItems) {
        this.familyItems = familyItems;
    }

    @Override
    public void getMyContext(Context context) {
        this.context = context;
    }


}
