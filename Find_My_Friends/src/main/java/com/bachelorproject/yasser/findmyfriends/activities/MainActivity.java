package com.bachelorproject.yasser.findmyfriends.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;

import com.bachelorproject.yasser.findmyfriends.R;

/**
 * Created by Yasser on 28/3/16.
 */
public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener {
    /** Called when the activity is first created. */
    TabHost tabHost;
    private Session session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);
        session.setPrivacy(0);
        // Get TabHost Refference
        tabHost = getTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent;

        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, TestActivity.class);
        spec = tabHost.newTabSpec("First").setIndicator("")
                .setContent(intent);

        //Add intent to tab
        tabHost.addTab(spec);

        /************* TAB2 ************/
        intent = new Intent().setClass(this, FriendsTab.class);
        spec = tabHost.newTabSpec("Second").setIndicator("")
                .setContent(intent);
        tabHost.addTab(spec);

        /************* TAB3 ************/
        intent = new Intent().setClass(this, ProfileTab.class);
        spec = tabHost.newTabSpec("Third").setIndicator("")
                .setContent(intent);
        tabHost.addTab(spec);

        /************* TAB4 ************/
        intent = new Intent().setClass(this, FindTab.class);
        spec = tabHost.newTabSpec("Forth").setIndicator("")
                .setContent(intent);
        tabHost.addTab(spec);

        /************* TAB5 ************/
        intent = new Intent().setClass(this, NotificationsActivity.class);
        spec = tabHost.newTabSpec("Fifth").setIndicator("")
                .setContent(intent);
        tabHost.addTab(spec);

        // Set drawable images to tab
        //tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_register);
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setText("Location");

        TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        tv2.setText("Find Friends");

        TextView tv3 = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        tv3.setText("My Profile");

        TextView tv4 = (TextView) tabHost.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
        tv4.setText("Search");

        TextView tv5 = (TextView) tabHost.getTabWidget().getChildAt(4).findViewById(android.R.id.title);
        tv5.setText("Notifications");
        // Set Tab1 as Default tab and change image
        tabHost.getTabWidget().setCurrentTab(0);
        //tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab1_over);


    }

    @Override
    public void onTabChanged(String tabId) {

        /************ Called when tab changed *************/

        //********* Check current selected tab and change according images *******/

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
//            if(i==0)
//                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_login);
//            else if(i==1)
//                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_register);
            if (i==2){
                session.setProfile(session.getuserid());
            }
        }


        Log.i("tabs", "CurrentTab: " + tabHost.getCurrentTab());

//        if(tabHost.getCurrentTab()==0)
//            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab1_over);
//        else if(tabHost.getCurrentTab()==1)
//            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab2_over);

    }
}
