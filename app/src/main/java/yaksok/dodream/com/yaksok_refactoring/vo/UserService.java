package yaksok.dodream.com.yaksok_refactoring.vo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import yaksok.dodream.com.yaksok_refactoring.model.User_Info_Model;


public interface UserService {
    public static final String API_URL = "http://54.180.81.180:8080";

    @POST("/users/login")
    Call<BodyVO>postGneralLogin(@Body User_Info_Model userVO);

//    @POST("/users")
//    Call<BodyVO>postSignUp(@Body UserVO userVO);
//
//    @POST("/users/login")
//    Call<BodyVO>postSnsLogin(@Body UserVO userVO);
//
//
//
//    @POST("mymedicines/")
//    Call<BodyVO> postQRCode(@Body PillVO pillVO);
//
//    @GET("/users/{item}")
//    Call<FindFamilyVO> getUserList(@Path("item") String item, @Query("itemType") String itemType);
//
//    @POST("families/")
//    Call<BodyVO>postRegisterFamily(@Body FamilyVO familyVO);
//
//    @GET("/families/{userId}")
//    Call<FindFamilyVO> getConnectedFamilyInfo(@Path("userId") String userId);
//
//    @PUT("/users/fcmtokens")
//    Call<BodyVO> putToken(@Body FcmTokenVO fcmTokenVO);
//
//    //js
//    @GET("medicines/{item}")
//    Call<MedicineVOList>getSearchPillList(@Path("item") String item, @Query("itemType") String itemtype);
//
//    @POST("mymedicines/")
//    Call<StatusVO> postMyInserttPill(@Body InsertPillList insertPillList);
//
//    @GET("/mymedicines/{userId}")
//    Call<MyMedicineResponseTypeVO>getMymediciens(@Path("userId") String userId);
//
//    @GET("/mymedicines/{userId}/neartime")
//    Call<NearTimeMedicineVO>getNearTime(@Path("userId") String userId);
//
//    @PUT("/mymedicines/takingmedicine")
//    Call<StatusVO>putTakeMedicine(@Body TakeOk takeOk);


    //=====토큰 업데이트======
    //HTTP PUT   /users/fcmtokens
    //Host:  http://54.180.81.180:8080
    //
    //request
    //BODY{
    //	“id” : string
    //	“fcmToken” : string
    //}
    //
    //
    //response
    //BODY{
    //	“status” : “code”
    //}
    //
    //code
    //201 : OK
    //500 : Server Error

}
