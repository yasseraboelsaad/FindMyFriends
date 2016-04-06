package com.bachelorproject.yasser.findmyfriends.Remote;

import com.bachelorproject.yasser.findmyfriends.Model.BeaconResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Yasser on 26/3/16.
 */
public interface BeaconAPI {

    String baseurl ="http://fa4fcbe3.ngrok.io/FindMyFriends/";
    @GET("get_beacon_details.php")
    Call<BeaconResponse> getBeacon(@Query("UUID") String UUID);

class Factory {
    private static BeaconAPI service;

    public static BeaconAPI getInstance() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseurl).build();
            service = retrofit.create(BeaconAPI.class);
            return service;
        }else {
            return service;
        }
    }
}
}
