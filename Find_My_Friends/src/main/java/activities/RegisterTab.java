package activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
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
 * Created by Yasser on 8/3/16.
 */
public class RegisterTab extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    EditText et_name;
    EditText et_email;
    EditText et_id;
    EditText et_password;
    Button btn_createuser;
    String name;
    String email;
    String id;
    String password;
    private ProgressDialog pDialog;

    private String url_create_user;
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_register);
        url_create_user = getResources().getString(R.string.localhost_link)+"/FindMyFriends/create_user.php";
        et_name= (EditText) findViewById(R.id.et_name);
        et_email= (EditText) findViewById(R.id.et_email);
        et_id= (EditText) findViewById(R.id.et_id);
        et_password= (EditText) findViewById(R.id.et_password);
        btn_createuser = (Button) findViewById(R.id.btn_signup);
        btn_createuser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                name = et_name.getText().toString();
                email = et_email.getText().toString();
                id = et_id.getText().toString();
                password = et_password.getText().toString();
                User user = new User (name,email,id,password);
                new CreateNewUser().execute();
            }
        });
    }

    class CreateNewUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterTab.this);
            pDialog.setMessage("Creating User..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("password", password));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "POST", params);

            // check log cat fro response
            //Log.d("Create Response", json.toString());

            // check for success tag
//            try {
//                int success = json.getInt(TAG_SUCCESS);
//
//               if (success == 1) {
//                    // successfully created product
                  startActivity(new Intent("android.intent.action.MAIN2"));
//                    // closing this screen
//                }
            //else {
//                    // failed to create product
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

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
