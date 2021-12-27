package com.herbal.herbalfax.customer.homescreen.deals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.NewDealsAdaptor;
import com.herbal.herbalfax.customer.homescreen.explorestrain.ExploreStrainViewModel;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.adapter.StoreDealsAdapter;

import java.util.ArrayList;
import java.util.List;


public class DealsFragment extends Fragment implements View.OnClickListener {
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private List<Interest> DealsCategory = new ArrayList<>();
    RecyclerView   storeDealsRecycler;
    LinearLayoutManager HorizontalLayout;
    StoreDealsAdapter storeDealsAdapter;
    RecyclerView dealsheadertxtrecycler;
    NewDealsAdaptor newDealsAdaptor;
    Button buyBtn;


    private DealsViewModel dealsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dealsViewModel =
                ViewModelProviders.of(this).get(DealsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_deals, container, false);
        storeDealsRecycler = root.findViewById(R.id.storeDealsRecycler);
        dealsheadertxtrecycler = root.findViewById(R.id.dealsheadertxtrecycler);
/*        buyBtn =  (Button) root.findViewById(R.id.fbLogin);*/

/*        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DealsFragment.this, DealsDetailsActivity.class);
                startActivity(intent);
            }
        });*/


        setUpDealsRecyclerView();
        /*prepareDealsCategory();*/
        setUpCategoriesView();
        return root;
    }
    private void setUpDealsRecyclerView() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        storeDealsRecycler.setLayoutManager(RecyclerViewLayoutManager);
        storeDealsAdapter = new StoreDealsAdapter((ArrayList<Interest>) DealsCategory, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        storeDealsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        storeDealsRecycler.setAdapter(storeDealsAdapter);
    }
    private void setUpCategoriesView() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        dealsheadertxtrecycler.setLayoutManager(RecyclerViewLayoutManager);
        newDealsAdaptor = new NewDealsAdaptor((ArrayList<Interest>) DealsCategory, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        dealsheadertxtrecycler.setLayoutManager(HorizontalLayout);
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        dealsheadertxtrecycler.setAdapter(newDealsAdaptor);
    }

    @Override
    public void onClick(View view) {

    }
}