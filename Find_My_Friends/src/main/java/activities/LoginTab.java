package activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Model.BeaconResponse;
import Model.UserResponse;
import Remote.BeaconAPI;
import Remote.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.alt236.btlescan.R;

/**
 * Created by Yasser on 27/3/16.
 */
public class LoginTab extends AppCompatActivity {
    /** Called when the activity is first created. */
    EditText et_id;
    EditText et_password;
    Button btn_login;
    String id,password;
    private ProgressDialog pDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_login);
        et_id= (EditText) findViewById(R.id.et_id_login);
        et_password= (EditText) findViewById(R.id.et_password_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pDialog = new ProgressDialog(LoginTab.this);
                pDialog.setMessage("Logging in..");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                id = et_id.getText().toString();
                password = et_password.getText().toString();
                UserAPI.Factory.getInstance().getUser(id).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        pDialog.dismiss();
                        startActivity(new Intent("android.intent.action.MAIN2"));
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        pDialog.dismiss();
                        Log.e("Failed",t.getMessage());
                    }
                });
            }
        });
    }
}
