package com.herbal.herbalfax.common_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.herbal.herbalfax.BuildConfig;
import com.herbal.herbalfax.R;

import com.herbal.herbalfax.common_screen.landingpage.CommonLandingActivity;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;

public class SplashActivity extends AppCompatActivity {
    SessionPref session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Session class instance
        TextView versionTxt=findViewById(R.id.versionTxt);
        versionTxt.setText(BuildConfig.VERSION_NAME);
        session = SessionPref.getInstance(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String userType = session.getStringVal(SessionPref.LoginUserType);
                Log.e("splashUserType", "" + userType);
                if (session.getStringVal(SessionPref.LoginUserType) != null) {
                    if (userType.equals("1")) {
                        session.checkLogin();
                    } else {
                        session.checkVendorLogin();
                    }
                } else {
                    Intent intent = new Intent(SplashActivity.this, CommonLandingActivity.class); //CommonLandingActivity
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);

    }


}