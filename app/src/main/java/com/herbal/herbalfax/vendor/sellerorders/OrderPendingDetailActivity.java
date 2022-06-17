package com.herbal.herbalfax.vendor.sellerorders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.herbal.herbalfax.R;

public class OrderPendingDetailActivity extends AppCompatActivity {
    Button assignDriver_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingdetail);
        assignDriver_btn = findViewById(R.id.assignDriver_btn);
        assignDriver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPendingDetailActivity.this, OrderAssignDriveActivity.class);
                startActivity(intent);
            }
        });
    }
}