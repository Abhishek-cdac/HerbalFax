package com.herbal.herbalfax.vendor.sellerdeals.booster;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.herbal.herbalfax.R;

public class SellerBoosterDealActivity extends AppCompatActivity {
    String dealName, dealId, dealPrice, dealExpiry, dealLocation;
    TextView endDateTxt, productNameText, productLocation, validity_txt, priceTxt, personBought;
    Button buyBtn;
    ImageView back;
    Button buybtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_booster_deal);
        Bundle extras = getIntent().getExtras();
        productNameText = findViewById(R.id.productNameText);
        productLocation = findViewById(R.id.location);
        validity_txt = findViewById(R.id.validity_txt);
        priceTxt = findViewById(R.id.priceTxt);
        personBought = findViewById(R.id.personBought);
        productLocation = findViewById(R.id.location);
        buyBtn = findViewById(R.id.buyBtn);
        endDateTxt = findViewById(R.id.endDateTxt);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (extras != null) {
            dealName = getIntent().getExtras().getString("dealName");
            dealId = getIntent().getExtras().getString("dealId");
            dealPrice = getIntent().getExtras().getString("dealPrice");
            dealExpiry = getIntent().getExtras().getString("dealExpiry");
            dealLocation = getIntent().getExtras().getString("dealLocation");
        }

        productNameText.setText(dealName);
        productLocation.setText(dealLocation);
        personBought.setText("0 Bought");
        priceTxt.setText("Price : $" + dealPrice);


        if (dealExpiry != null) {
            validity_txt.setText("Validity Till : " + dealExpiry);
            endDateTxt.setText("End Date : " + dealExpiry);
        }
        if (dealLocation == null) {
            productLocation.setText("No Address Found");
        } else {
            productLocation.setText(dealLocation);
        }
        buybtn = findViewById(R.id.buyBtn);
        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellerBoosterDealActivity.this,R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.buycustomview, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });


    }
}