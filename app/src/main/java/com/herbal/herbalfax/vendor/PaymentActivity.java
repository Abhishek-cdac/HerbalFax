package com.herbal.herbalfax.vendor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.herbal.herbalfax.R;

public class PaymentActivity extends AppCompatActivity {
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView send = findViewById(R.id.add_newpayment);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getApplication(), AddPaymentMethod.class);
                startActivity(send);
            }
        });
        Button pay_now = findViewById(R.id.pay_now_button);
        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay_now = new Intent(getApplication(), PaymentSuccessfull.class);
                startActivity(pay_now);
            }
        });
    }
}