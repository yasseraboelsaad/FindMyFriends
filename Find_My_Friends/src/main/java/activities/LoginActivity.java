package activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;

/**
 * Created by Yasser on 27/3/16.
 */
public class LoginActivity extends TabActivity implements TabHost.OnTabChangeListener {
    /** Called when the activity is first created. */
    TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get TabHost Refference
        tabHost = getTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent;

        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, LoginTab.class);
        spec = tabHost.newTabSpec("First").setIndicator("")
                .setContent(intent);

        //Add intent to tab
        tabHost.addTab(spec);

        /************* TAB2 ************/
        intent = new Intent().setClass(this, RegisterTab.class);
        spec = tabHost.newTabSpec("Second").setIndicator("")
                .setContent(intent);
        tabHost.addTab(spec);

        // Set drawable images to tab
        //tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_register);
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setText("Login");

        TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        tv2.setText("Register");
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

        }


        Log.i("tabs", "CurrentTab: " + tabHost.getCurrentTab());

//        if(tabHost.getCurrentTab()==0)
//            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab1_over);
//        else if(tabHost.getCurrentTab()==1)
//            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab2_over);

    }
}
