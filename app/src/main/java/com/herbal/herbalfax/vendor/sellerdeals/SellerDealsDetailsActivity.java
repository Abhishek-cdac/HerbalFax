package com.herbal.herbalfax.vendor.sellerdeals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.sellerdeals.adapter.SelllerDealsDetailsAdapter;
import com.herbal.herbalfax.vendor.sellerdrivers.SellerMyDriverAdapter;

import java.util.ArrayList;

public class SellerDealsDetailsActivity extends AppCompatActivity {
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    RecyclerView customersdetails;
    SelllerDealsDetailsAdapter sellerAdapter;
    private LinearLayoutManager HorizontalLayout;
    private LinearLayoutManager RecyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_deals_details);
        customersdetails = findViewById(R.id.customersdetails);

        setUpCustomerRecycler();
    }

    private void setUpCustomerRecycler() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        customersdetails.setLayoutManager(RecyclerViewLayoutManager);
        sellerAdapter = new SelllerDealsDetailsAdapter(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        customersdetails.setAdapter(sellerAdapter);
    }
}