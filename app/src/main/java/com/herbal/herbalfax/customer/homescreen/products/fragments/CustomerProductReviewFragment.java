package com.herbal.herbalfax.customer.homescreen.products.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.products.adapter.CustProductReviewAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.adapter.StoreReviewAdapter;

import java.util.ArrayList;

public class CustomerProductReviewFragment extends Fragment {
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerView productReviewsRecycler;
    LinearLayoutManager HorizontalLayout;
    CustProductReviewAdapter custProductReviewAdapter;
ArrayList<Interest> lst_productRating = new ArrayList<>();
    public CustomerProductReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_customer_product_review, container, false);

        productReviewsRecycler = root.findViewById(R.id.productReviewsRecycler);

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        productReviewsRecycler.setLayoutManager(RecyclerViewLayoutManager);
        custProductReviewAdapter = new CustProductReviewAdapter(lst_productRating, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //storeReviewsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        productReviewsRecycler.setAdapter(custProductReviewAdapter);

        return root;
    }

}
