package com.herbal.herbalfax.customer.store.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.herbal.herbalfax.R;

public class StoreDetailsFragment extends Fragment {


    public StoreDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_details_fragment, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String storeId = arguments.get("storeId").toString();
            Log.e("StoreDetailsFragment", "" + storeId);
        }
        return root;

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//    }
}