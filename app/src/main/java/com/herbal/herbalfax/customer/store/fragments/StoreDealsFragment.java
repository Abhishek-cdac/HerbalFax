package com.herbal.herbalfax.customer.store.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.adapter.StoreDealsAdapter;
import com.herbal.herbalfax.customer.store.adapter.StoreProductsAdapter;

import java.util.ArrayList;

public class StoreDealsFragment extends Fragment {
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private ArrayList<Interest> lst_deals;
    RecyclerView   storeDealsRecycler;
    LinearLayoutManager HorizontalLayout;
    StoreDealsAdapter storeDealsAdapter;

    public StoreDealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_deals_fragment, container, false);
        storeDealsRecycler = root.findViewById(R.id.storeDealsRecycler);

        setUpDealsRecyclerView();

        return root;
    }

    private void setUpDealsRecyclerView() {
        lst_deals = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        storeDealsRecycler.setLayoutManager(RecyclerViewLayoutManager);
        storeDealsAdapter = new StoreDealsAdapter(lst_deals, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        storeDealsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        storeDealsRecycler.setAdapter(storeDealsAdapter);


    }

}