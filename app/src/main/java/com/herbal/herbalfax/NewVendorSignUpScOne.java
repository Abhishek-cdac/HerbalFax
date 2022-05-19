package com.herbal.herbalfax;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.herbal.herbalfax.signupnewvendor.NewVendorSignUpScTwo;

public class NewVendorSignUpScOne extends AppCompatActivity {
    Button next_button;
    int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vendor_sign_up_sc_one);
        next_button = findViewById(R.id.next_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userType = getIntent().getExtras().getInt("selectedType");
            Log.e("data ", "" + userType);
        }
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewVendorSignUpScTwo.class);
                startActivity(intent);
            }
        });
    }
}