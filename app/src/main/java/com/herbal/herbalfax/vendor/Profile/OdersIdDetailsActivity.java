package com.herbal.herbalfax.vendor.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.Profile.adapter.OrderIdDetailsAdaptar;
import com.herbal.herbalfax.vendor.sellerdeals.adapter.SelllerDealsDetailsAdapter;

import java.util.ArrayList;

public class OdersIdDetailsActivity extends AppCompatActivity {
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    private LinearLayoutManager RecyclerViewLayoutManager;
    RecyclerView oderdetailsRecycler;
    OrderIdDetailsAdaptar orderiddetailsAdaptar;
    private LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oders_id_details);
        oderdetailsRecycler = findViewById(R.id.ordersiddetails);

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