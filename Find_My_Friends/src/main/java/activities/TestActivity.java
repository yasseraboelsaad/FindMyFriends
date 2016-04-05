package activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;

import Model.Beacon;
import Model.BeaconResponse;
import Remote.BeaconAPI;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.R;
import adapters.LeDeviceListAdapter;
import containers.BluetoothLeDeviceStore;
import util.BluetoothLeScanner;
import util.BluetoothUtils;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;
import util.GPSTracker;
import util.JSONParser;
import android.os.Handler;


public class TestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.tvBluetoothLe)
    protected TextView mTvBluetoothLeStatus;
    @Bind(R.id.tvBluetoothStatus)
    protected TextView mTvBluetoothStatus;
    @Bind(R.id.tvItemCount)
    protected TextView mTvItemCount;
    @Bind(android.R.id.list)
    protected ListView mList;


    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScanner mScanner;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothLeDeviceStore mDeviceStore;
    TextView location;
    String UUID;
    private Session session;

    double x0;
    double y0;
    double r0;
    double x1;
    double y1;
    double r1;
    double x2;
    double y2;
    double r2;

    GPSTracker gps;
    Boolean located = false;

    double gx,gy;
    String room;

    double xcoord,ycoord;

    String url_update_user = "http://f7d352a4.ngrok.io/FindMyFriends/update_user.php";
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;

    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
            mDeviceStore.addDevice(deviceLe);
            final EasyObjectCursor<BluetoothLeDevice> c = mDeviceStore.getDeviceCursor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.swapCursor(c);
                    updateItemCount(mLeDeviceListAdapter.getCount());
                }
            });

            if (mDeviceStore.getDeviceList().size() > 2) {
//                locateThreeBeacons();
            } else if (mDeviceStore.getDeviceList().size() == 2) {
//                locateTwoBeacons();
            } else if (mDeviceStore.getDeviceList().size() == 1) {
                locateOneBeacon();
            }
        }
    };
    public void locateNoBeacon(){
        located=true;
        mScanner.scanLeDevice(-1, false);
        invalidateOptionsMenu();
        xcoord=-1;
        ycoord=-1;
        location.setText("Your current location by gps is: "+gx+","+gy);
        new UpdateUser().execute();
    }

    public void locateOneBeacon() {
        located=true;
        mScanner.scanLeDevice(-1, false);
        invalidateOptionsMenu();
        final IBeaconDevice d1 = new IBeaconDevice(mDeviceStore.getDeviceList().get(0));
        r0 = Math.round(d1.getAccuracy());
        BeaconAPI.Factory.getInstance().getBeacon(d1.getUUID()).enqueue(new Callback<BeaconResponse>() {
            @Override
            public void onResponse(Call<BeaconResponse> call, Response<BeaconResponse> response) {
                x0 = Double.parseDouble(response.body().Beacons.get(0).getX());
                y0 = Double.parseDouble(response.body().Beacons.get(0).getY());
                room = response.body().Beacons.get(0).getLocation();
                session.setRoom(room);
                Log.d("#####THE Y VALUE", y0 + "");
                xcoord = x0;
                ycoord = y0;
                Log.d("###########closer to " + d1.getUUID(), "" + r0);
                location.setText("Your current location by beacon is: " + r0 + "m away from beacon (" + x0 + "," + y0 + ")");
                new UpdateUser().execute();
            }

            @Override
            public void onFailure(Call<BeaconResponse> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }

        });

    }

//    public void locateTwoBeacons() {
//        final IBeaconDevice d1 = new IBeaconDevice(mDeviceStore.getDeviceList().get(0));
//        final IBeaconDevice d2 = new IBeaconDevice(mDeviceStore.getDeviceList().get(1));
//
//        r0 = Math.round(d1.getAccuracy());
//        r1 = Math.round(d2.getAccuracy());
//
//
//        BeaconAPI.Factory.getInstance().getBeacon(d1.getUUID()).enqueue(new Callback<BeaconResponse>() {
//
//            @Override
//            public void onResponse(Call<BeaconResponse> call, Response<BeaconResponse> response) {
//                x0 = Double.parseDouble(response.body().Beacons.get(0).getX());
//                y0 = Double.parseDouble(response.body().Beacons.get(0).getY());
//
//
//                BeaconAPI.Factory.getInstance().getBeacon(d2.getUUID()).enqueue(new Callback<BeaconResponse>() {
//
//                    @Override
//                    public void onResponse(Call<BeaconResponse> call, Response<BeaconResponse> response) {
//                        x1 = Double.parseDouble(response.body().Beacons.get(0).getX());
//                        y1 = Double.parseDouble(response.body().Beacons.get(0).getY());
//
//                        double a, dx, dy, d, h, rx, ry;
//                        double point2_x, point2_y;
//                /* dx and dy are the vertical and horizontal distances between
//    * the circle centers.
//    */
//                        dx = x1 - x0;
//                        dy = y1 - y0;
//
//    /* Determine the straight-line distance between the centers. */
//                        d = Math.sqrt((dy * dy) + (dx * dx));
//
//    /* 'point 2' is the point where the line through the circle
//    * intersection points crosses the line between the circle
//    * centers.
//    */
//
//    /* Determine the distance from point 0 to point 2. */
//                        a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);
//
//    /* Determine the coordinates of point 2. */
//                        point2_x = x0 + (dx * a / d);
//                        point2_y = y0 + (dy * a / d);
//
//    /* Determine the distance from point 2 to either of the
//    * intersection points.
//    */
//                        h = Math.sqrt((r0 * r0) - (a * a));
//
//    /* Now determine the offsets of the intersection points from
//    * point 2.
//    */
//                        rx = -dy * (h / d);
//                        ry = dx * (h / d);
//
//    /* Determine the absolute intersection points. */
//                        double intersectionPoint1_x = point2_x + rx;
//                        double intersectionPoint2_x = point2_x - rx;
//                        double intersectionPoint1_y = point2_y + ry;
//                        double intersectionPoint2_y = point2_y - ry;
//                        xcoord=intersectionPoint1_x;
//                        ycoord=intersectionPoint1_y;
//                        new UpdateUser().execute();
//                        location.setText("Your location is either: (" + intersectionPoint1_x + "," + intersectionPoint1_y + ") or (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")");
//                    }
//
//                    @Override
//                    public void onFailure(Call<BeaconResponse> call, Throwable t) {
//
//                    }
//                });
//
//
//            }
//
//            @Override
//            public void onFailure(Call<BeaconResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void locateThreeBeacons() {
//        final IBeaconDevice b1 = new IBeaconDevice(mDeviceStore.getDeviceList().get(0));
//        final IBeaconDevice b2 = new IBeaconDevice(mDeviceStore.getDeviceList().get(1));
//        final IBeaconDevice b3 = new IBeaconDevice(mDeviceStore.getDeviceList().get(2));
//
//
//        r0 = Math.round(b1.getAccuracy());
//        r1 = Math.round(b2.getAccuracy());
//        r2 = Math.round(b3.getAccuracy());
//
//        BeaconAPI.Factory.getInstance().getBeacon(b1.getUUID()).enqueue(new Callback<BeaconResponse>() {
//            @Override
//            public void onResponse(Call<BeaconResponse> call, Response<BeaconResponse> response) {
//                x0 = Double.parseDouble(response.body().Beacons.get(0).getX());
//                y0 = Double.parseDouble(response.body().Beacons.get(0).getY());
//
//                BeaconAPI.Factory.getInstance().getBeacon(b2.getUUID()).enqueue(new Callback<BeaconResponse>() {
//                    @Override
//                    public void onResponse(Call<BeaconResponse> call, Response<BeaconResponse> response) {
//                        x1 = Double.parseDouble(response.body().Beacons.get(0).getX());
//                        y1 = Double.parseDouble(response.body().Beacons.get(0).getY());
//
//                        BeaconAPI.Factory.getInstance().getBeacon(b3.getUUID()).enqueue(new Callback<BeaconResponse>() {
//                            @Override
//                            public void onResponse(Call<BeaconResponse> call, Response<BeaconResponse> response) {
//                                x2 = Double.parseDouble(response.body().Beacons.get(0).getX());
//                                y2 = Double.parseDouble(response.body().Beacons.get(0).getY());
//
//                                double a, dx, dy, d, h, rx, ry;
//                                double point2_x, point2_y;
//
//    /* dx and dy are the vertical and horizontal distances between
//    * the circle centers.
//    */
//                                dx = x1 - x0;
//                                dy = y1 - y0;
//
//    /* Determine the straight-line distance between the centers. */
//                                d = Math.sqrt((dy * dy) + (dx * dx));
//
//
//    /* 'point 2' is the point where the line through the circle
//    * intersection points crosses the line between the circle
//    * centers.
//    */
//
//    /* Determine the distance from point 0 to point 2. */
//                                a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);
//
//    /* Determine the coordinates of point 2. */
//                                point2_x = x0 + (dx * a / d);
//                                point2_y = y0 + (dy * a / d);
//
//    /* Determine the distance from point 2 to either of the
//    * intersection points.
//    */
//                                h = Math.sqrt((r0 * r0) - (a * a));
//
//    /* Now determine the offsets of the intersection points from
//    * point 2.
//    */
//                                rx = -dy * (h / d);
//                                ry = dx * (h / d);
//
//    /* Determine the absolute intersection points. */
//                                double intersectionPoint1_x = point2_x + rx;
//                                double intersectionPoint2_x = point2_x - rx;
//                                double intersectionPoint1_y = point2_y + ry;
//                                double intersectionPoint2_y = point2_y - ry;
//                                TextView ip1 = (TextView) findViewById(R.id.tv_ip1);
//                                ip1.setText("(" + intersectionPoint1_x + "," + intersectionPoint1_y + ")");
//                                TextView ip2 = (TextView) findViewById(R.id.tv_ip2);
//                                ip2.setText("(" + intersectionPoint2_x + "," + intersectionPoint2_y + ")");
//                                TextView r3 = (TextView) findViewById(R.id.tv_r3);
//                                r3.setText(Math.round(b3.getAccuracy()) + "m");
//                                double cond1 = (intersectionPoint1_x - 2) * (intersectionPoint1_x - 2) + (intersectionPoint1_y - 2) * (intersectionPoint1_y - 2);
//                                double cond2 = (intersectionPoint2_x - 2) * (intersectionPoint2_x - 2) + (intersectionPoint2_y - 2) * (intersectionPoint2_y - 2);
//                                if (cond1 <= r2 * r2 + 2 && cond1 >= r2 * r2 - 2) {
//                                    location.setText("Your current location is: " + intersectionPoint1_x + "," + intersectionPoint1_y + ")");
//                                    xcoord=intersectionPoint1_x;
//                                    ycoord=intersectionPoint1_y;
//                                    new UpdateUser().execute();
//                                } else if (cond2 <= r2 * r2 + 2 && cond2 >= r2 * r2 - 2) {
//                                    location.setText("Your current location is: " + intersectionPoint2_x + "," + intersectionPoint2_y + ")");
//                                    xcoord=intersectionPoint2_x;
//                                    ycoord=intersectionPoint2_y;
//                                    new UpdateUser().execute();
//                                } else {
//                                    location.setText("Your current location could not be determined.");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<BeaconResponse> call, Throwable t) {
//                                Log.e("Failedz", t.getMessage());
//                            }
//
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(Call<BeaconResponse> call, Throwable t) {
//                        Log.e("Failedz", t.getMessage());
//                    }
//
//                });
//            }
//
//            @Override
//            public void onFailure(Call<BeaconResponse> call, Throwable t) {
//                Log.e("Failedz", t.getMessage());
//            }
//
//        });
//    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        room="null";
        session = new Session(this);
        ButterKnife.bind(this);
        mList.setOnItemClickListener(this);
        mDeviceStore = new BluetoothLeDeviceStore();
        mBluetoothUtils = new BluetoothUtils(this);
        mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
        updateItemCount(0);
        location = (TextView) findViewById(R.id.tv_loc);
        location.setText("Your current location is: ");

        gps = new GPSTracker(TestActivity.this);

        if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gx=latitude;
            gy=longitude;
            Toast.makeText(
                    getApplicationContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanner.isScanning()) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        }


        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        final BluetoothLeDevice device = mLeDeviceListAdapter.getItem(position);
        if (device == null) return;

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                startScan();
                break;
            case R.id.menu_stop:
                mScanner.scanLeDevice(-1, false);
                invalidateOptionsMenu();
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanner.scanLeDevice(-1, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();

        if (mIsBluetoothOn) {
            mTvBluetoothStatus.setText(R.string.on);
        } else {
            mTvBluetoothStatus.setText(R.string.off);
        }

        if (mIsBluetoothLePresent) {
            mTvBluetoothLeStatus.setText(R.string.supported);
        } else {
            mTvBluetoothLeStatus.setText(R.string.not_supported);
        }

        invalidateOptionsMenu();
    }

    private void startScan() {
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mDeviceStore.clear();
        updateItemCount(0);

        mLeDeviceListAdapter = new LeDeviceListAdapter(this, mDeviceStore.getDeviceCursor());
        mList.setAdapter(mLeDeviceListAdapter);

        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (mIsBluetoothOn && mIsBluetoothLePresent) {
            mScanner.scanLeDevice(-1, true);
            invalidateOptionsMenu();

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (!located)
                locateNoBeacon();
            }
        }, 5000);
    }

    private void updateItemCount(final int count) {
        mTvItemCount.setText(
                getString(
                        R.string.formatter_item_count,
                        String.valueOf(count)));
    }

    class UpdateUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TestActivity.this);
            pDialog.setMessage("Updating Location..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            //Get the bundle
            Bundle bundle = getIntent().getExtras();
            Log.d("###Name",session.getuserid());
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Name", session.getuserName()));
            params.add(new BasicNameValuePair("email", session.getuserEmail()));
            params.add(new BasicNameValuePair("id", session.getuserid()));
            params.add(new BasicNameValuePair("password", session.getuserPassword()));
            params.add(new BasicNameValuePair("xcoord", ""+xcoord));
            params.add(new BasicNameValuePair("ycoord", ""+ycoord));
            params.add(new BasicNameValuePair("image", session.getuserImage()));
            params.add(new BasicNameValuePair("gx", ""+gx));
            params.add(new BasicNameValuePair("gy", ""+gy));
            params.add(new BasicNameValuePair("room",room));

            JSONObject json = jsonParser.makeHttpRequest(url_update_user,
                    "POST", params);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

}