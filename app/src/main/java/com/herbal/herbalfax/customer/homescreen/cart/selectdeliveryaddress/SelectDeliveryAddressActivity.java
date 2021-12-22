package com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.cart.addaddress.AddNewAddressActivity;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CheckoutListAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.adapter.SelectDeliveryAddressAdapter;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class SelectDeliveryAddressActivity extends AppCompatActivity {
    RecyclerView addressRecyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    SelectDeliveryAddressAdapter selectDeliveryAddressAdapter;
    Onclick itemClick;
    ImageView back;
    ArrayList<Interest> lst_address_item = new ArrayList<>();
    TextView addNewAddressTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_address);
        addressRecyclerView = findViewById(R.id.addressRecyclerView);
        back = findViewById(R.id.back);

        addNewAddressTxt = findViewById(R.id.addNewAddressTxt);
        addNewAddressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), AddNewAddressActivity.class);
                startActivity(intent);
            }
        });  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        
        setUpAddressRecyclerView();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpAddressRecyclerView() {

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        addressRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
        selectDeliveryAddressAdapter = new SelectDeliveryAddressAdapter(lst_address_item, getApplicationContext(), itemClick);
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        addressRecyclerView.setAdapter(selectDeliveryAddressAdapter);

        
    }
}