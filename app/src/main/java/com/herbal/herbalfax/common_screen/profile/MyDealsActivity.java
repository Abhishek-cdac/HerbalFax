package com.herbal.herbalfax.common_screen.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.profile.Adapter.MydealsAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class MyDealsActivity extends AppCompatActivity {

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    RecyclerView   myDealItemsBarRecycler;
    MydealsAdapter mydealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deals);
        myDealItemsBarRecycler = findViewById(R.id.mydealitemsbar);
        setMyDealsDetailsBar();
    }

    private void setMyDealsDetailsBar() {
        ArrayList<Interest> dealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        myDealItemsBarRecycler.setLayoutManager(RecyclerViewLayoutManager);
        mydealsAdapter = new MydealsAdapter(dealsCategory,getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        myDealItemsBarRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        myDealItemsBarRecycler.setAdapter(mydealsAdapter);
    }
}