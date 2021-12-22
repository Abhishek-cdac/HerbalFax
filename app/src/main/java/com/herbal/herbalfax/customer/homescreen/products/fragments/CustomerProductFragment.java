package com.herbal.herbalfax.customer.homescreen.products.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.herbal.herbalfax.R;

public class CustomerProductFragment extends Fragment {

    TextView productName,descTxt, PriceTxt,share, ratingTxt,totalReview;
    ImageView remove, add, one, two, three, four, five;

    public CustomerProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_customer_product_detail, container, false);

        one = root.findViewById(R.id.one);
        two = root.findViewById(R.id.two);
        three = root.findViewById(R.id.three);
        four = root.findViewById(R.id.four);
        five = root.findViewById(R.id.five);
        PriceTxt = root.findViewById(R.id.PriceTxt);
        share = root.findViewById(R.id.share);
        add = root.findViewById(R.id.add);
        remove = root.findViewById(R.id.remove);
        descTxt = root.findViewById(R.id.descTxt);
        totalReview = root.findViewById(R.id.totalReview);
        ratingTxt = root.findViewById(R.id.ratingTxt);
        productName = root.findViewById(R.id.productName);
        return root;
    }

}
