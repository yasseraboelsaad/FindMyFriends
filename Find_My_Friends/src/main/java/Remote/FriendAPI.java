package Remote;

import Model.Friend;
import Model.FriendResponse;
import Model.User;
import Model.UserResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Yasser on 29/3/16.
 */
public interface FriendAPI {
    String baseurl ="http://f21a97da.ngrok.io/FindMyFriends/";
    @GET("get_all_friends.php")
    Call<UserResponse> getFriends1(@Query("User1") String User1);

    @GET("get_all_friends.php")
    Call<FriendResponse> getFriends2(@Query("User1") String User1, @Query("User2") String User2);

    class Factory {
        private static FriendAPI service;

        public static FriendAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseurl).build();
                service = retrofit.create(FriendAPI.class);
                return service;
            }else {
                return service;
            }
        }
    }
}
