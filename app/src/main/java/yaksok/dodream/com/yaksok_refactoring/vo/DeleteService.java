package yaksok.dodream.com.yaksok_refactoring.vo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface DeleteService {
    public static String API_URL = "http://15.164.163.234:8080";


    @HTTP(method = "DELETE",path = "/users/{user_1}/families/{user_2}")
    Call<FamilyBodyVO> deleteBody(@Path("user_1") String user_1,@Path("user_2") String user_2);


    @HTTP(method = "DELETE",path = "/users/{userId}/")
    Call<FamilyBodyVO> deleteUser(@Path("userId") String item);



    @HTTP(method = "DELETE",path = "/my-medicines/{myMedicineNo}")
    Call<FamilyBodyVO> deleteMyMedicine(@Body DeleteMyMeidicineVO myMeidicineVO);


}
