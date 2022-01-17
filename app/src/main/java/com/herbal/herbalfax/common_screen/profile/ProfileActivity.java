package com.herbal.herbalfax.common_screen.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.EventsDetailsActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView MyDealsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        MyDealsBtn = findViewById(R.id.mydealsbtn);

        MyDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyDealsActivity.class);
                startActivity(intent);
            }
        });
    }
}