package com.herbal.herbalfax.customer.homescreen.deals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.NewDealsAdaptor;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.adapter.StoreDealsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DealsDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private List<Interest> DealsCategory = new ArrayList<>();
    RecyclerView storeDealsRecycler;
    LinearLayoutManager HorizontalLayout;
    StoreDealsAdapter storeDealsAdapter;

    Button fbLogin;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_details);

        
        storeDealsRecycler = (RecyclerView) findViewById(R.id.storeDealsRecycler);
        fbLogin =  (Button)findViewById(R.id.fbLogin);

        setUpDealsRecyclerView();
    }

    private void setUpDealsRecyclerView() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        storeDealsRecycler.setLayoutManager(RecyclerViewLayoutManager);
        storeDealsAdapter = new StoreDealsAdapter((ArrayList<Interest>) DealsCategory, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        storeDealsRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        storeDealsRecycler.setAdapter(storeDealsAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        String text =  parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}