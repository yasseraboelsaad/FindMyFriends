package activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.User;
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
    private Session session;
    private ProgressDialog pDialog;
    String url_update_user = "http://ed18867c.ngrok.io/FindMyFriends/update_user.php";
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
        if (session.getPrivacy()==0) {
            et_name.setText(session.getuserName());
            et_email.setText(session.getuserEmail());
            et_id.setText(session.getuserid());
            et_password.setText(session.getuserPassword());
            et_image.setText(session.getuserImage());
        }else if (session.getPrivacy()==1){
            et_name.setText(session.getuserName());
            et_email.setText(session.getuserEmail());
            et_id.setText(session.getuserid());
            et_name.setKeyListener(null);
            et_email.setKeyListener(null);
            et_id.setKeyListener(null);
            et_password.setVisibility(View.GONE);
            et_image.setVisibility(View.GONE);
            btn_save.setVisibility(View.GONE);
        }else if (session.getPrivacy()==2){
            et_name.setText(session.getuserName());
            et_email.setText(session.getuserEmail());
            et_id.setText(session.getuserid());
            et_name.setKeyListener(null);
            et_email.setKeyListener(null);
            et_id.setKeyListener(null);
            btn_save.setVisibility(View.GONE);
        }
        iv_image = (ImageView) findViewById(R.id.imageView);
        if (session.getuserImage().contains("http")){
            Picasso.with(this).load(session.getuserImage()).into(iv_image);

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

}
