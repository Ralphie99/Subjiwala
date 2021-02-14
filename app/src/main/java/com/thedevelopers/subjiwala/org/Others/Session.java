package com.thedevelopers.subjiwala.org.Others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.Activities.MainActivity;

import java.util.HashMap;

public class Session {
    public static final String KEY_PRF = "sabjiwala_prf";
    public final SharedPreferences.Editor editor;
    public Context mContext;
    public SharedPreferences pref;
    public static final String MOBILE_KEY = "mobile";
    public static final String NAME_KEY = "name";
    public static final String USER_ID_KEY = "user_id";
    public static final String EMAIL_KEY = "email";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String ADD_KEY = "Address";
    public static final String PASS_KEY = "password";
    public static final String IS_FIRST_LAUNCH = "first_launch";


    public Session(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(KEY_PRF, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String uid,String mobile, String name, String email,String address,String pass) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_ID_KEY, uid);
        editor.putString(NAME_KEY, name);
        editor.putString(MOBILE_KEY, mobile);
        editor.putString(EMAIL_KEY, email);
        editor.putString(ADD_KEY, address);
        editor.putString(PASS_KEY,pass);
        editor.commit();
    }

    public void reset_Pass(String password)
    {
        editor.putString(PASS_KEY,password);
        editor.commit();
    }

    public void reset_Add(String password)
    {
        editor.putString(ADD_KEY,password);
        editor.commit();
    }

    public void reset_Email(String password)
    {
        editor.putString(EMAIL_KEY,password);
        editor.commit();
    }

    public void reset_Phone(String password)
    {
        editor.putString(MOBILE_KEY,password);
        editor.commit();
    }

    public void setIsFirstLauch(boolean flag){
        editor.putBoolean(IS_FIRST_LAUNCH,flag);
    }

    public boolean getIsFirstLauch(){
        return pref.getBoolean(IS_FIRST_LAUNCH,true);
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(mContext, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        } else {
//            Intent i = new Intent(mContext, AddVehicle_fromOutside.class);
            Intent i = new Intent(mContext, Dashboard_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(NAME_KEY, pref.getString(NAME_KEY, null));
        user.put(EMAIL_KEY, pref.getString(EMAIL_KEY, null));
        user.put(MOBILE_KEY, pref.getString(MOBILE_KEY, null));
        user.put(USER_ID_KEY, pref.getString(USER_ID_KEY, null));
        user.put(ADD_KEY, pref.getString(ADD_KEY, null));
        user.put(PASS_KEY, pref.getString(PASS_KEY, null));
        return user;
    }
    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(mContext, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }


}
