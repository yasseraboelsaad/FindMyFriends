package com.bachelorproject.yasser.findmyfriends.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bachelorproject.yasser.findmyfriends.Model.UserResponse;

import com.bachelorproject.yasser.findmyfriends.Remote.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bachelorproject.yasser.findmyfriends.R;

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
    Boolean success = false;
    private Session session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_login);
        et_id= (EditText) findViewById(R.id.et_id_login);
        et_password= (EditText) findViewById(R.id.et_password_login);
        btn_login = (Button) findViewById(R.id.btn_login);

        session = new Session(this);

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
                        if (!response.body().getUsers().isEmpty()) {
                            if (password.equals(response.body().getUsers().get(0).getPassword())) {
                                pDialog.dismiss();
                                success = true;
                                String name = response.body().getUsers().get(0).getName();
                                String email = response.body().getUsers().get(0).getEmail();
                                String id = response.body().getUsers().get(0).getId();
                                String password = response.body().getUsers().get(0).getPassword();
                                String image = response.body().getUsers().get(0).getImage();

                                session.setuserid(id);
                                session.setuserName(name);
                                session.setuserEmail(email);
                                session.setuserPassword(password);
                                session.setuserImage(image);
                                session.setProfile(id);
                                startActivity(new Intent("android.intent.action.MAIN4"));
                            } else {
                                pDialog.dismiss();
                                Toast.makeText(LoginTab.this, "Login Failed.", Toast.LENGTH_LONG).show();
                                Log.d("Error", "Login failed");
                            }
                        }else {
                            pDialog.dismiss();
                            Toast.makeText(LoginTab.this, "Login Failed.", Toast.LENGTH_LONG).show();
                            Log.d("Error", "Login failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        pDialog.dismiss();
                        Log.e("Failed",t.getMessage());
                    }
                });
                if (success){
                    //Add the bundle to the intent

                }
            }
        });
    }

}
