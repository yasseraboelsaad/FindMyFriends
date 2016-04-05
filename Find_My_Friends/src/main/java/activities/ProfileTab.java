package activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.User;
import Model.UserResponse;
import Remote.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.alt236.btlescan.R;
import util.JSONParser;

/**
 * Created by Yasser on 28/3/16.
 */
public class ProfileTab extends AppCompatActivity {
    EditText et_name;
    EditText et_email;
    EditText et_id;
    EditText et_password;
    EditText et_image;
    ImageView iv_image;
    Button btn_save;
    TextView txt_image;
    TextView txt_password;
    private Session session;
    Button btn_location;
    private ProgressDialog pDialog;
    String baseurl ="http://f7d352a4.ngrok.io";
    String url_update_user = baseurl+"/FindMyFriends/update_user.php";
    String url_add_friend = baseurl+"/FindMyFriends/create_friends.php";
    String url_delete_friend = baseurl+"/FindMyFriends/delete_friends.php";
    String url_update_friend = baseurl+"/FindMyFriends/update_friends.php";
    JSONParser jsonParser = new JSONParser();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_profile);
        session = new Session(this);
        et_name = (EditText) findViewById(R.id.editText);
        et_email = (EditText) findViewById(R.id.editText2);
        et_id = (EditText) findViewById(R.id.editText3);
        et_id.setKeyListener(null);
        et_password = (EditText) findViewById(R.id.editText4);
        et_image = (EditText) findViewById(R.id.editText5);
        btn_save = (Button) findViewById(R.id.button);
        txt_image = (TextView) findViewById(R.id.textView5);
        txt_password = (TextView) findViewById(R.id.textView4);
        iv_image = (ImageView) findViewById(R.id.imageView);
        btn_location = (Button) findViewById(R.id.btn_location);
        pDialog = new ProgressDialog(ProfileTab.this);
        pDialog.setMessage("Loading ..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        //Users profile
        if (session.getuserid().equals(session.getProfile())){
            et_name.setText(session.getuserName());
            et_email.setText(session.getuserEmail());
            et_id.setText(session.getuserid());
            et_password.setText(session.getuserPassword());
            et_image.setText(session.getuserImage());
            if (session.getuserImage().contains("http")){
                Picasso.with(ProfileTab.this).load(session.getuserImage()).into(iv_image);
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iv_image.setImageDrawable(getDrawable(R.drawable.default_image));
                }
            }
            btn_save.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    session.setuserName(et_name.getText().toString());
                    session.setuserEmail(et_email.getText().toString());
                    session.setuserid(et_id.getText().toString());
                    session.setuserPassword(et_password.getText().toString());
                    session.setuserImage(et_image.getText().toString());
                    new UpdateUser().execute();
                }
            });
        }
        //Not Friend
        else if (session.getPrivacy()==0){
            et_password.setVisibility(View.GONE);
            et_image.setVisibility(View.GONE);
            txt_image.setVisibility(View.GONE);
            txt_password.setVisibility(View.GONE);
            btn_save.setText("Add Friend");
            btn_location.setVisibility(View.GONE);
            UserAPI.Factory.getInstance().getUser(session.getProfile()).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    et_name.setText(response.body().getUsers().get(0).getName());
                    et_email.setText(response.body().getUsers().get(0).getEmail());
                    et_id.setText(response.body().getUsers().get(0).getId());
                    if (response.body().getUsers().get(0).getImage() != null && response.body().getUsers().get(0).getImage().contains("http")) {
                        Picasso.with(ProfileTab.this).load(response.body().getUsers().get(0).getImage()).into(iv_image);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            iv_image.setImageDrawable(getDrawable(R.drawable.default_image));
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AddFriend().execute();
                }
            });
        }
        //Friend
        else if (session.getPrivacy()==1){
            txt_image.setVisibility(View.GONE);
            txt_password.setVisibility(View.GONE);
            btn_save.setText("Remove Friend");
            et_password.setVisibility(View.GONE);
            et_image.setVisibility(View.GONE);
            UserAPI.Factory.getInstance().getUser(session.getProfile()).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    et_name.setText(response.body().getUsers().get(0).getName());
                    et_email.setText(response.body().getUsers().get(0).getEmail());
                    et_id.setText(response.body().getUsers().get(0).getId());
                    if (response.body().getUsers().get(0).getImage() != null && response.body().getUsers().get(0).getImage().contains("http")) {
                        Picasso.with(ProfileTab.this).load(response.body().getUsers().get(0).getImage()).into(iv_image);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            iv_image.setImageDrawable(getDrawable(R.drawable.default_image));
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new RemoveFriend().execute();
                }
            });
            btn_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent("android.intent.action.MAIN6"));
                }
            });
            //Request
        }else if (session.getPrivacy()==2){
            et_password.setVisibility(View.GONE);
            et_image.setVisibility(View.GONE);
            txt_image.setVisibility(View.GONE);
            txt_password.setVisibility(View.GONE);
            btn_save.setText("Accept");
            btn_location.setVisibility(View.GONE);
            UserAPI.Factory.getInstance().getUser(session.getProfile()).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    et_name.setText(response.body().getUsers().get(0).getName());
                    et_email.setText(response.body().getUsers().get(0).getEmail());
                    et_id.setText(response.body().getUsers().get(0).getId());
                    if (response.body().getUsers().get(0).getImage() != null && response.body().getUsers().get(0).getImage().contains("http")) {
                        Picasso.with(ProfileTab.this).load(response.body().getUsers().get(0).getImage()).into(iv_image);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            iv_image.setImageDrawable(getDrawable(R.drawable.default_image));
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UpdateFriend().execute();
                }
            });
        }
        pDialog.dismiss();
    }
    class UpdateUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileTab.this);
            pDialog.setMessage("Updating Information..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            //Get the bundle
            Bundle bundle = getIntent().getExtras();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Name", session.getuserName()));
            params.add(new BasicNameValuePair("email", session.getuserEmail()));
            params.add(new BasicNameValuePair("id", session.getuserid()));
            params.add(new BasicNameValuePair("password", session.getuserPassword()));
            params.add(new BasicNameValuePair("image", session.getuserImage()));

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
    class AddFriend extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileTab.this);
            pDialog.setMessage("Sending request..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            //Get the bundle
            Bundle bundle = getIntent().getExtras();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("User1", session.getuserid()));
            params.add(new BasicNameValuePair("User2", session.getProfile()));

            JSONObject json = jsonParser.makeHttpRequest(url_add_friend,
                    "POST", params);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            session.setProfile(session.getuserid());
        }

    }

    class RemoveFriend extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileTab.this);
            pDialog.setMessage("Sending request..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            //Get the bundle
            Bundle bundle = getIntent().getExtras();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("User1", session.getuserid()));
            params.add(new BasicNameValuePair("User2", session.getProfile()));
            JSONObject json = jsonParser.makeHttpRequest(url_delete_friend,
                    "POST", params);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            session.setProfile(session.getuserid());
        }

    }
    class UpdateFriend extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileTab.this);
            pDialog.setMessage("Accepting request..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            //Get the bundle
            Bundle bundle = getIntent().getExtras();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("User1", session.getProfile()));
            params.add(new BasicNameValuePair("User2", session.getuserid()));
            params.add(new BasicNameValuePair("status", "1"));

            JSONObject json = jsonParser.makeHttpRequest(url_update_friend,
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
