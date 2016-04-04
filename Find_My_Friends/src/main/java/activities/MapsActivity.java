package activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import Model.UserResponse;
import Remote.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.alt236.btlescan.R;
import util.GPSTracker;


/**
 * Created by Yasser on 31/3/16.
 */


public class MapsActivity extends Activity {

    private GoogleMap map;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_maps_api);
        session = new Session(this);
        map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        UserAPI.Factory.getInstance().getUser(session.getProfile()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                final LatLng LOCATION_C = new LatLng(response.body().getUsers().get(0).getGx(),response.body().getUsers().get(0).getGy());
                if (response.body().getUsers().get(0).getRoom().contains(".")) {
                    map.addMarker(new MarkerOptions().position(LOCATION_C).title(response.body().getUsers().get(0).getRoom()));
                }else {
                    map.addMarker(new MarkerOptions().position(LOCATION_C).title("Your friend is here!"));
                }
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_C, 15));

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });






    }
}