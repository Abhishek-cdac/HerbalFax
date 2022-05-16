package com.herbal.herbalfax.common_screen.utils.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.herbal.herbalfax.common_screen.landingpage.CommonLandingActivity;
import com.herbal.herbalfax.customer.homescreen.homedashboard.LandingPageActivity;
import com.herbal.herbalfax.vendor.SellerLandingPageActivity;


public class SessionPref {
    @SuppressLint("StaticFieldLeak")
    static SessionPref ref;
    public static final String USER_SESSIONNAME = "user_loginSession";
    Context mContext;

    //app
    public static final String SHARED_PREF_KEY = "herbalfax_shared_pref";
    public static final int SHARED_PREF_MODE = 0;  // 0 - for private mode


    // Constructor
    public SessionPref(Context context){
        this.mContext = context;
//        pref = mContext.getSharedPreferences(SHARED_PREF_KEY, SHARED_PREF_MODE);
//        editor = pref.edit();
    }

    public static SessionPref getInstance(Context mContext) {
        if (ref == null) {
            ref = new SessionPref(mContext);
            init(mContext);
        }
        return ref;
    }




    static SharedPreferences pref;
    static SharedPreferences.Editor editor;

    @SuppressLint("WrongConstant")
    private static void init(Context mContext) {
        pref = mContext.getSharedPreferences(SHARED_PREF_KEY, SHARED_PREF_MODE); // 0 - for private mode
        editor = pref.edit();
    }




    @SuppressLint("WrongConstant")
    public static void logout(Context mContext) {
        pref = mContext.getSharedPreferences(SHARED_PREF_KEY, SHARED_PREF_MODE); // 0 - for private mode
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }


    public void saveStringKeyVal(String key, String val) {
        editor.putString(key, val);
        editor.commit();
    }


    public void saveLOngKeyLattitude(String key, double val) {
        editor.putLong(key, Double.doubleToRawLongBits(val));
        editor.commit();
    }

    public double getLattitude(String key) {
        return Double.longBitsToDouble(pref.getLong(key, 0));
    }

    public void saveLongKeyLongitude(String key, double val) {
        editor.putLong(key, Double.doubleToRawLongBits(val));
        editor.commit();
    }

    public double getLongitude(String key) {
        return Double.longBitsToDouble(pref.getLong(key, 0));
    }

    public void saveBoolKeyVal(String key, boolean val) {
        editor.putBoolean(key, val);
        editor.commit();
    }

    public void saveIntKeyVal(String key, int val) {
        editor.putInt(key, val);
        editor.commit();
    }

    public String getStringVal(String key) {
        return pref.getString(key, null);
    }

    public boolean getBoolVal(String key) {
        return pref.getBoolean(key, false);
    }

    public int getIntVal(String key) {
        return pref.getInt(key, 0);
    }


    public static String LoginUserID = "id";
    public static String LoginUserFCMID = "fcm_id";
    public static String LoginUserEmailVerified = "emailVerified";
    public static String LoginUserType = "userType";
    public static String LoginUserfullName = "fullName";
    public static String LoginUseremail = "email";
    public static String LoginUserCountryCode = "countryCode";
    public static String LoginUserphoneNo = "phoneNo";
    public static String LoginUserCity = "city";
    public static String LoginUsertoken = "token";
    public static String LoginUsergender = "gender";
    public static String LoginUserbirthDate = "birthDate";
    public static String LoginUserActive = "active";
    public static String LoginUserprofilePic = "profilePic";
    public static String LoginUserInterestsIDS = "InterestsID";
    public static String LoginUserinterested = "Interests";
    public static String LoginJwtoken = "jwt_token";
  public static String LoginProfession = "ProfTitle";

    private static final String IS_LOGIN = "IsLoggedIn";

    public void saveLoginUser(String id,
                              String userType,
                              String fullName,
                              String birthDate,
                              String gender,
                              String city,
                              String email,
                              String emailVerified,
                              String countryCode,
                              String phoneNo,
                              String profilePic,
                              String active,
                              String token,
                              String jwt_token,
                              String ProfTitle
    ) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(LoginUserID, id);
        editor.putString(LoginUserType, userType);
        editor.putString(LoginUserfullName, fullName);
        editor.putString(LoginUseremail, email);
        editor.putString(LoginUserEmailVerified, emailVerified);
        editor.putString(LoginUserCountryCode, countryCode);
        editor.putString(LoginUserphoneNo, phoneNo);
        editor.putString(LoginUserCity, city);
        editor.putString(LoginUsertoken, token);
        editor.putString(LoginUsergender, gender);
        editor.putString(LoginUserbirthDate, birthDate);
        editor.putString(LoginUserprofilePic, profilePic);
        editor.putString(LoginUserActive, active);
        editor.putString(LoginJwtoken, jwt_token);
        editor.putString(LoginProfession, ProfTitle);
        editor.commit();
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, CommonLandingActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            mContext.startActivity(i);
        }
        else {
            Intent i = new Intent(mContext, LandingPageActivity.class);
            mContext.startActivity(i);
        }

    }

    public void checkVendorLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, CommonLandingActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            mContext.startActivity(i);
        }
        else {
            Intent i = new Intent(mContext, SellerLandingPageActivity.class);
            mContext.startActivity(i);
        }

    }
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
