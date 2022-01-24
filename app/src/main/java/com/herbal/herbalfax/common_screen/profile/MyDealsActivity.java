package com.herbal.herbalfax.common_screen.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.profile.Adapter.MydealsAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;
import java.util.List;

public class MyDealsActivity extends AppCompatActivity {

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private List<Interest> DealsCategory = new ArrayList<>();
    LinearLayoutManager HorizontalLayout;
    RecyclerView   mydealitemsbarRecycler;
    MydealsAdapter mydealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deals);
        mydealitemsbarRecycler = findViewById(R.id.mydealitemsbar);
        setMyDealsDetailsBar();
    }

    private void setMyDealsDetailsBar() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        mydealitemsbarRecycler.setLayoutManager(RecyclerViewLayoutManager);
        mydealsAdapter = new MydealsAdapter((ArrayList<Interest>) DealsCategory,getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mydealitemsbarRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        mydealitemsbarRecycler.setAdapter(mydealsAdapter);
    }
}