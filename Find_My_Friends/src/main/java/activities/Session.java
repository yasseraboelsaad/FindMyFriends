package activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Yasser on 28/3/16.
 */
public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setuserid(String id) {
        prefs.edit().putString("id", id).commit();

    }
    public void setuserName(String id) {
        prefs.edit().putString("Name", id).commit();

    }
    public void setuserEmail(String id) {
        prefs.edit().putString("email", id).commit();

    }
    public void setuserPassword(String id) {
        prefs.edit().putString("password", id).commit();

    }
    public void setuserImage(String id) {
        prefs.edit().putString("image", id).commit();

    }
    public void setPrivacy(int id) {
        prefs.edit().putString("privacy",""+ id).commit();

    }

    public String getuserid() {
        String id = prefs.getString("id","");
        return id;
    }
    public String getuserName() {
        String id = prefs.getString("Name","");
        return id;
    }
    public String getuserEmail() {
        String id = prefs.getString("email","");
        return id;
    }
    public String getuserPassword() {
        String id = prefs.getString("password","");
        return id;
    }
    public String getuserImage() {
        String id = prefs.getString("image","");
        return id;
    }
    public int getPrivacy() {
        int id = Integer.parseInt(prefs.getString("privacy",""));
        return id;
    }
}