package yaksok.dodream.com.yaksok_refactoring.vo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Info_Model;


public interface UserService {
    public static final String API_URL = "http://15.164.163.234:8080";
    

    @POST("/users")
    Call<BodyVO>postSignUp(@Body User_Info_Model userVO);

    @POST("/users/login")
    Call<BodyVO>postGneralLogin(@Body User_Info_Model userVO);

    @PATCH("/users/{userId}")
    Call<BodyVO> putToken(@Path("userId") String userId, @Body FcmTokenVO fcmToken);

    @PATCH("/users/{userId}")
    Call<BodyVO>putChangePW(@Path("userId") String id, @Body User_Info_Model user_info_model);


    @GET("/users/{userId}/my-medicines")
    Call<MyPillVO>getMymediciens(@Path("userId")String userId);

    @GET("/users/{userId}/near-times")
    Call<NearTimePillVO>getNearTime(@Path("userId") String userId);


    @GET("medicines/{item}")
    Call<SearchPillListVO>getSearchPillList(@Path("item") String item, @Query("itemType") String itemtype);

    @POST("families/")
    Call<BodyVO>postRegisterFamily(@Body FamilyVO familyVO);

    @GET("/users/{userId}/families")
    Call<Connected_Family> getConnectedFamilyInfo(@Path("userId") String userId);


    @PATCH("/emails/{email}/users/{userId}")
    Call<BodyVO>putFindUserPassword(@Path("email") String email,@Path("userId") String id);


    @GET("/users/{item}")
    Call<FindFamilyVO> getUserList(@Path("item") String item, @Query("itemType") String itemType,@Query("behavior") String searchFamily);



    @POST("/my-medicines")
    Call<BodyVO> postMyInserttPill(@Body InsertPill_Item insertPill_item);

    @PATCH("/users/{givingUser}/my-medicines/{myMedicineNo}")
    Call<BodyVO>putTakeMedicine(@Path("givingUser") String givingUser,@Path("myMedicineNo") String myMedicineNo);



    @GET("/users/{item} ? itemType=\"\" & behavior=\"\"")
    Call<BodyVO>getOverlapId(@Path("item") String item, @Query("itemType") String itemType, @Query("behavior") String behavior);

    @GET("/users/{item} ? itemType=\"\" & behavior=\"\"")
    Call<BodyVO>GetOvelapPhoneNumber(@Path("item") String pn,@Query("itemType") String phoneNumber,@Query("behavior") String  confirmOverlapPhoneNum);

}
