package com.herbal.herbalfax.vendor.sellerorders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class OdersIdDetailsActivity extends AppCompatActivity {
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    private LinearLayoutManager RecyclerViewLayoutManager;
    RecyclerView oderdetailsRecycler;
    OrderIdDetailsAdaptar orderiddetailsAdaptar;
    private LinearLayoutManager HorizontalLayout;
    Button selectDriverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oders_id_details);
        oderdetailsRecycler = findViewById(R.id.ordersiddetails);
        selectDriverBtn = findViewById(R.id.selectDriverBtn);
        selectDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderAssignDriveActivity.class);
                startActivity(intent);
            }
        });
        setUpOrderDetailsRecycler();
    }

    private void setUpOrderDetailsRecycler() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        oderdetailsRecycler.setLayoutManager(RecyclerViewLayoutManager);
        orderiddetailsAdaptar = new OrderIdDetailsAdaptar(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        oderdetailsRecycler.setAdapter(orderiddetailsAdaptar);
    }
}