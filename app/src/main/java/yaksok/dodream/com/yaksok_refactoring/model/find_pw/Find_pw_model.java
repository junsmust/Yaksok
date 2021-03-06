package yaksok.dodream.com.yaksok_refactoring.model.find_pw;

import android.content.Context;
import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.NullHostNameVerifier;
import yaksok.dodream.com.yaksok_refactoring.SSLUtil;
import yaksok.dodream.com.yaksok_refactoring.presenter.find_pw.Find_pw_presenter;
import yaksok.dodream.com.yaksok_refactoring.vo.BodyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Find_pw_model implements I_Find_pw_model {
    Find_pw_presenter pw_presenter;
    private static UserService userService;
    Context context;

    public Find_pw_model(Find_pw_presenter pw_presenter) {
        this.pw_presenter = pw_presenter;
    }

    @Override
    public void findPw(String id, final String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserService.POST_URL)
                .client( new OkHttpClient.Builder()
                        .sslSocketFactory(SSLUtil.getPinnedCertSslSocketFactory(context))  //ssl
                        .hostnameVerifier(new NullHostNameVerifier())                       //ssl HostName Pass
                        .build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Log.e( "findPw: ", id+email);

        Call<BodyVO> bodyVOCall = userService.putFindUserPassword(id,email);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                if(bodyVO.getStatus().equals("200")){
                    pw_presenter.onResponse(true,"임시 비밀번호가 "+"\n"+"전송되었습니다.");
                }else if(bodyVO.getStatus().equals("403")){
                    pw_presenter.onResponse(false,"가입되지 않은 회원입ㅇ니다.");
                }else{
                   pw_presenter.onResponse(false,"서버에러 입니다.");
                }

            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });


    }

    @Override
    public void getMyContext(Context context) {
        this.context = context;
    }
}
