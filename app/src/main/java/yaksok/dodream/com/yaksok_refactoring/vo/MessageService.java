package yaksok.dodream.com.yaksok_refactoring.vo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MessageService {
    public static final String API_URL = "http://15.164.163.234:8080";



    @GET("/users/{user_1}/families/{user_2}/messages?")
    Call<MessageBodyVO>getTheChatting(@Path("user_1") String userId,@Path("user_2") String user2,@Query("limit") int limit, @Query("offset") int offset);

    @POST("/messages")
    Call<MessageResultBodyVO>sendAmeesage(@Body SendMessageVO messageVO);

}
