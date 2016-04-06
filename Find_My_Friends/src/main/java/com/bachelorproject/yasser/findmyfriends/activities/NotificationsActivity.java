package com.bachelorproject.yasser.findmyfriends.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.bachelorproject.yasser.findmyfriends.Model.User;
import com.bachelorproject.yasser.findmyfriends.Model.UserResponse;
import com.bachelorproject.yasser.findmyfriends.Remote.FriendAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bachelorproject.yasser.findmyfriends.R;

/**
 * Created by Yasser on 1/4/16.
 */
public class NotificationsActivity extends AppCompatActivity {

    private List<User> friendslist = new ArrayList<User>();
    ListView listView ;
    Session session;
    private ProgressDialog pDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        session = new Session(this);
        pDialog = new ProgressDialog(NotificationsActivity.this);
        pDialog.setMessage("Loading ..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        populateFriendsList();
    }

    private void populateListView() {
        ArrayAdapter<User> adapter = new MyListAdapter();
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }

    private void populateFriendsList() {

                FriendAPI.Factory.getInstance().getFriends2(session.getuserid()).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        for (int i = 0; i < response.body().getUsers().size(); i++) {
                            Log.d("###Friends", response.body().getUsers().get(i).getName());
                            if (Integer.parseInt(response.body().getUsers().get(i).getstatus()) == 0) {
                                Log.d("###Friends", response.body().getUsers().get(i).getName());
                                String name = response.body().getUsers().get(i).getName();
                                String email = response.body().getUsers().get(i).getEmail();
                                String id = response.body().getUsers().get(i).getId();
                                String image = response.body().getUsers().get(i).getPassword();
                                friendslist.add(new User(name, email, id, "", image));
                            }
                        }
                        populateListView();
                        registerClickCallback();
                        pDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });
//                Log.d("Friends list", friendslist.get(0).getName());
    }

    private void registerClickCallback() {
        ListView list= (ListView)findViewById(R.id.listView2);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                User clickedUser = friendslist.get(position);
                //go to profile activity
                Toast.makeText(NotificationsActivity.this, "You clicked " + position + " which is " + clickedUser.getId(), Toast.LENGTH_LONG).show();
                session.setProfile(clickedUser.getId());
                session.setPrivacy(2);
                startActivity(new Intent("android.intent.action.MAIN5"));
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<User>{

        public MyListAdapter() {
            super(NotificationsActivity.this,R.layout.list_item_friend,friendslist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.list_item_friend,parent,false);
            }
            User currentUser = friendslist.get(position);

            ImageView image = (ImageView) itemView.findViewById(R.id.device_icon);
            if (!currentUser.getImage().contains("http")){
                image.setImageDrawable(getDrawable(R.drawable.default_image));
            }else {
                Picasso.with(NotificationsActivity.this).load(currentUser.getImage()).into(image);
            }
            TextView nameText = (TextView)itemView.findViewById(R.id.item_tv_name);
            nameText.setText(currentUser.getName());

            TextView emailText = (TextView)itemView.findViewById(R.id.item_tv_email);
            emailText.setText(currentUser.getEmail());

            TextView idText = (TextView)itemView.findViewById(R.id.item_tv_id);
            idText.setText(currentUser.getId());

            return itemView;
        }
    }
}
