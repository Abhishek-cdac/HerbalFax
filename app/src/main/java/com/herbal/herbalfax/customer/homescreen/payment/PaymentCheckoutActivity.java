package com.herbal.herbalfax.customer.homescreen.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.homedashboard.LandingPageActivity;

public class PaymentCheckoutActivity extends AppCompatActivity {
    TextView orderID;
    Button continue_button;
String IdOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_checkout);
        orderID = findViewById(R.id.orderID);
        continue_button = findViewById(R.id.continue_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            IdOrders = extras.getString("IdOrders");

        }
        orderID.setText("#"+IdOrders);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}