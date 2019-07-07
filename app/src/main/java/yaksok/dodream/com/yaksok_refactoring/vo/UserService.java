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


    
    //==User===
    //
    //[PUT] UpdateFCMToken
    //
    //변경 전 : PUT /users/fcmtokens
    //body -> {“id” : “..” , “fcmToken” : “..”}
    //
    //변경 후 :  PATCH /users/{userId}
    //body -> {“fcmToken” : “..”}
    //——————————————
    //
    //[PUT] UpdateUserPassword
    //
    //변경 전 : PUT /users/{id}/originpasswords/{opw}/newpasswords/{npw}
    //
    //변경 후 : PATCH /users/{userId}		-> 모바일쪽에서 기존패스워드일치하는지 로그인API사용해야함
    //body -> {“pw” : “..”}
    //
    //——————————————
    //
    //
    //[PUT] FindUserPassword
    //
    //변경 전 : PUT /users/{id}/emails/{email}/passwords
    //
    //변경 후 : PATCH /emails/{email}/users/{userId}
    //
    //——————————————-
    //
    //[DELETE] DeleteUser
    //
    //변경 전 : DELETE /users
    //body -> {“id” : “..” , “userType” : “..”}
    //
    //변경 후 : DELETE /users/{userId}/
    //
    //
    //
    //
    //
    //
    //
    //==Family===
    //
    //[GET] GetFamilyList
    //
    //변경 전 : GET /families/{userId}
    //
    //변경 후 : GET /users/{userId}/families
    //——————————————
    //
    //
    //[DELETE] DeleteFamily
    //
    //변경 전 : DELETE /families
    //body -> {"user_1” : ”.., "user_2” : ”..”}
    //
    //변경 후 : DELETE /users/{user_1}/families/{user_2}

    //==MyMedicine===
    //
    //[POST] RegisterMyMedicine
    //
    //변경 전 : POST /mymedicines?
    //body -> {
    //    "userId": “…”,
    //    "name": “…”,
    //    "dosagi": #,
    //    "timeList": [
    //        “….”,
    //        “….”
    //    ],
    //    “alarmList": [     //요기 변경
    //        “…”
    //    ]
    //}
    //
    //변경 후 : POST /my-medicines
    //body -> {
    //    "userId": “…”,
    //    "name": “…”,
    //    "dosagi": #,
    //    "timeList": [
    //        “….”,
    //        “….”
    //    ],
    //    "notificationList": [
    //        “…”
    //    ]
    //}
    //——————————————
    //
    //[GET] GetMyMedicineList
    //
    //변경 전 : GET /mymedicines/{id}
    //
    //변경 후 : GET /users/{userId}/my-medicines
    //——————————————
    //
    //[GET] GetNearTime
    //
    //변경 전 : GET /mymedicines/{userId}/neartime
    //
    //변경 후 : GET /users/{userId}/near-times
    //——————————————
    //
    //[PUT] TakinMedicine
    //
    //변경 전 :  PUT /mymedicines/takingmedicine
    //body -> {“givingUser” : “..”, “myMedicineNo” : “..”}
    //
    //변경 후 : PATCH /users/{givingUser}/my-medicines/{myMedicineNo}
    //
    //——————————————
    //
    //[DELETE] DeleteMyMedicine
    //
    //변경 전 : DELETE /mymedicines
    //body -> {“myMedicineNo” : “..”}
    //
    //변경 후 : DELETE /my-medicines/{myMedicineNo}
    //————————————————————————
    //
    //
    //
    //==Alarm  ->  Notification===
    //
    //[GET] GetChatingRoomList
    //
    //변경 전 : GET /alarms/{userId}
    //
    //변경 후 : GET /users/{userId}/notifications
    //————————————————————————
    //
    //[DELETE] DeleteAlarm -> DeleteNotification
    //
    //변경 전 :  DELETE /alarms
    //
    //body -> {“alarmNo” : “..”}
    //
    //
    //변경 후 : DELETE /notifications/{notificationNo}
    //
    //
    //
    //
    //——————————————
    @POST("/users")
    Call<BodyVO>postSignUp(@Body User_Info_Model userVO);

    @POST("/users/login")
    Call<BodyVO>postGneralLogin(@Body User_Info_Model userVO);

    @PUT("/users/fcmtokens")
    Call<BodyVO> putToken(@Body FcmTokenVO fcmTokenVO);

    //http://15.164.163.234:8080/users/jslee7120/originpasswords/e9e9632f01/newpasswords/1q2w3e4r
    @PUT(" /users/{id}/originpasswords/{opw}/newpasswords/{npw}")
    Call<BodyVO>putChangePW(@Path("id") String id, @Path("opw") String opw, @Path("npw") String npw );

    @GET("/mymedicines/{userId}")
    Call<MyPillVO>getMymediciens(@Path("userId")String userId);

    @GET("/mymedicines/{userId}/neartime")
    Call<NearTimePillVO>getNearTime(@Path("userId") String userId);

    @GET("medicines/{item}")
    Call<SearchPillListVO>getSearchPillList(@Path("item") String item, @Query("itemType") String itemtype);

    @POST("families/")
    Call<BodyVO>postRegisterFamily(@Body FamilyVO familyVO);

    @GET("/families/{userId}")
    Call<Connected_Family> getConnectedFamilyInfo(@Path("userId") String userId);

    //findUserPassword
    //http://15.164.163.234:8080/users/gam7325/emails/isp3087@gmail.com/passwords
    @PUT("/users/{id}/emails/{email}/passwords")
    Call<BodyVO>putFindUserPassword(@Path("id") String id,@Path("email") String email);


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
    // public static final String API_URL = "http://15.164.163.234:8080";


   // http://15.164.163.234:8080/users/01027250856?itemType=phoneNumber&behavior=searchFamily
    @GET("/users/{item}")
    Call<FindFamilyVO> getUserList(@Path("item") String item, @Query("itemType") String itemType,@Query("behavior") String searchFamily);


//
//    @PUT("/users/fcmtokens")
//    Call<BodyVO> putToken(@Body FcmTokenVO fcmTokenVO);
//
//    //js

//
    @POST("mymedicines/")
    Call<BodyVO> postMyInserttPill(@Body InsertPill_Item insertPill_item);
//
//    @GET("/mymedicines/{userId}")
//    Call<MyMedicineResponseTypeVO>getMymediciens(@Path("userId") String userId);
//

//
    @PUT("/mymedicines/takingmedicine")
    Call<BodyVO>putTakeMedicine(@Body TakeOk takeOk);

    @GET("/users/{item} ? itemType=\"\" & behavior=\"\"")
    Call<BodyVO>getOverlapId(@Path("item") String item, @Query("itemType") String itemType, @Query("behavior") String behavior);
    //[해당 아이디가 중복된 아이디인지 검사한다.]
    //HTTP GET /users/{item} ? itemType="" & behavior=""
    //itemType : id
    //behavior : confirmOverlapId
    //200 : OK (존재하지 않음으로 회원가입가능한 아이디)
    //400 : 잘못된 요청
    //403 : 중복되는 아이디 존재
    //500 : Server Error
    @GET("/users/{item} ? itemType=\"\" & behavior=\"\"")
    Call<BodyVO>GetOvelapPhoneNumber(@Path("item") String pn,@Query("itemType") String phoneNumber,@Query("behavior") String  confirmOverlapPhoneNum);
    //[전화번호를 사용하여 해당 핸드폰번호가 중복인지 값을 반환한다.]
    //HTTP GET /users/{item} ? itemType="" & behavior=""
    //itemType : phoneNumber
    //behavior : confirmOverlapPhoneNum
    //200 : OK (존재하지 않음으로 회원가입가능한 핸드폰번호)
    //400 : 잘못된 요청
    //403 : 중복되는 핸드폰번호 존재
    //500 : Server Error
}
