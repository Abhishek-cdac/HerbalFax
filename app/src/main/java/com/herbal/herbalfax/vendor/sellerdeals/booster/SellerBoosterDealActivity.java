package com.herbal.herbalfax.vendor.sellerdeals.booster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.bottomsheet.MoreBottomSheet;

public class SellerBoosterDealActivity extends AppCompatActivity {
    Context mContext;
    String dealName, dealId, dealPrice, dealExpiry, dealLocation;
    TextView endDateTxt, productNameText, productLocation, validity_txt, priceTxt, personBought;
    Button buyBtn;
    ImageView back;

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
        buyBtn.setOnClickListener(v -> showBottomSheet(0));
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

    }

    private void showBottomSheet(int adapterPosition) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PayBottomSheet sheet = new PayBottomSheet(this, adapterPosition);
        sheet.show(fragmentManager, "comment bottom sheet");
    }
}