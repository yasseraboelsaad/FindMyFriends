package com.bachelorproject.yasser.findmyfriends.Remote;


import com.bachelorproject.yasser.findmyfriends.Model.UserResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Yasser on 28/3/16.
 */
public interface UserAPI {
    String baseurl ="http://fa4fcbe3.ngrok.io/FindMyFriends/";
    @GET("get_all_users.php")
    Call<UserResponse> getUser(@Query("id") String id);

    @GET("get_all_users.php")
    Call<UserResponse> getUsername(@Query("Name") String Name);

//    @POST("create_user.php")
//    Call<User> createUser(@Body User user);

    class Factory {
        private static UserAPI service;

        public static UserAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseurl).build();
                service = retrofit.create(UserAPI.class);
                return service;
            }else {
                return service;
            }
        }
    }
}
