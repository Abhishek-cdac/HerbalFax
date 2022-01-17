package com.herbal.herbalfax.customer.myorders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.profile.Adapter.MydealsAdapter;
import com.herbal.herbalfax.common_screen.profile.MyDealsActivity;
import com.herbal.herbalfax.customer.myorders.Adaptor.MyOrdersAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {


    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private List<Interest> DealsCategory = new ArrayList<>();
    LinearLayoutManager HorizontalLayout;
    RecyclerView   myordersitemsbarRecycler;
    MyOrdersAdapter myordersAdapter;
    TextView MyOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        MyOrderBtn = findViewById(R.id.downloadbtn);
        myordersitemsbarRecycler = findViewById(R.id.myordersitemsbar);

        MyOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrackOrderActivity.class);
                startActivity(intent);
            }
        });
        setMyOrdersDetailsBar();
    }

    private void setMyOrdersDetailsBar() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        myordersitemsbarRecycler.setLayoutManager(RecyclerViewLayoutManager);
        myordersAdapter = new MyOrdersAdapter((ArrayList<Interest>) DealsCategory,getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        myordersitemsbarRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        myordersitemsbarRecycler.setAdapter(myordersAdapter);
    }
}