package com.herbal.herbalfax.vendor.sellerdeals.booster;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.feed.FeedAdapter;
import com.herbal.herbalfax.vendor.PaymentActivity;

public class PayBottomSheet extends BottomSheetDialogFragment {
    private final SellerBoosterDealActivity feedAdapter;
    private final int index;
    Button creditcard_btn;


    public PayBottomSheet(SellerBoosterDealActivity feedAdapter, int adapterPosition) {
        this.feedAdapter = feedAdapter;
        this.index = adapterPosition;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.buycustomview, container, false);
        creditcard_btn = view.findViewById(R.id.creditcard_btn);
        creditcard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(getActivity(), PaymentActivity.class);
                startActivity(send);
            }
        });

        return view;
    }
}