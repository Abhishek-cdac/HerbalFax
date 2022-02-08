package com.herbal.herbalfax.vendor.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.notification.NotificationAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.Profile.adapter.SellerNotificationAdapter;
import com.herbal.herbalfax.vendor.sellerdeals.adapter.SelllerDealsDetailsAdapter;

import java.util.ArrayList;

public class SellerNotificationActivity extends AppCompatActivity {
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    RecyclerView notificationRecycler;
    SellerNotificationAdapter notificationAdapter;
    private LinearLayoutManager HorizontalLayout;
    private LinearLayoutManager RecyclerViewLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_notification);
        notificationRecycler = findViewById(R.id.notificationRecycler);

        setUpNotificationRecycler();
    }

    private void setUpNotificationRecycler(){
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationRecycler.setLayoutManager(RecyclerViewLayoutManager);
        notificationAdapter = new SellerNotificationAdapter(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        notificationRecycler.setAdapter(notificationAdapter);
    }
}