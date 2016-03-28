package Remote;

import java.util.List;

import Model.Beacon;
import Model.BeaconResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uk.co.alt236.btlescan.R;

/**
 * Created by Yasser on 26/3/16.
 */
public interface BeaconAPI {

    String baseurl ="http://ed18867c.ngrok.io/FindMyFriends/";
    //@GET("get_beacon_details.php?UUID=3f009ed7-ebbf-568e-a2a2-a4bfe51e64d1")
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
