package com.herbal.herbalfax.vendor.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.YourGroupAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.Profile.adapter.MyCompletedAdaptar;
import com.herbal.herbalfax.vendor.Profile.adapter.MyOngoingAdaptar;
import com.herbal.herbalfax.vendor.Profile.adapter.MyPendingAdaptar;
import com.herbal.herbalfax.vendor.Profile.adapter.OrderIdDetailsAdaptar;

import java.util.ArrayList;

public class MySellerOrderActivity extends AppCompatActivity {
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    private RecyclerView recyclerViewPending;
    private RecyclerView recyclerViewOngoing;
    private RecyclerView recyclerViewCompleted;
    private TextView pending;
    private TextView ongoing;
    private TextView complete;
    private MyPendingAdaptar mypendingadaptar;
    private MyOngoingAdaptar myongoingadaptar;
    private MyCompletedAdaptar mycompletedadaptar;
    private LinearLayoutManager RecyclerViewLayoutManager;
    private LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_seller_order);
        recyclerViewPending = findViewById(R.id.recycler_view_pending);
        recyclerViewOngoing = findViewById(R.id.recycler_view_ongoing);
        recyclerViewCompleted = findViewById(R.id.recycler_view_completed);
        pending = findViewById(R.id.pendingbtn);
        ongoing = findViewById(R.id.ongoingbtn);
        complete = findViewById(R.id.completedbtn);

        ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending.setTextColor(getResources().getColor(R.color.black));
                complete.setTextColor(getResources().getColor(R.color.black));
                ongoing.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                pending.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                complete.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                ongoing.setTextColor(getResources().getColor(R.color.white));
                recyclerViewOngoing.setVisibility(View.VISIBLE);
                recyclerViewPending.setVisibility(View.GONE);
                recyclerViewCompleted.setVisibility(View.GONE);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complete.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee)); 
                complete.setTextColor(getResources().getColor(R.color.black));
                pending.setTextColor(getResources().getColor(R.color.white));
                ongoing.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                pending.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                ongoing.setTextColor(getResources().getColor(R.color.black));
                recyclerViewOngoing.setVisibility(View.GONE);
                recyclerViewPending.setVisibility(View.VISIBLE);
                recyclerViewCompleted.setVisibility(View.GONE);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complete.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                complete.setTextColor(getResources().getColor(R.color.white));
                pending.setTextColor(getResources().getColor(R.color.black));
                ongoing.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                pending.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                ongoing.setTextColor(getResources().getColor(R.color.black));
                recyclerViewOngoing.setVisibility(View.GONE);
                recyclerViewPending.setVisibility(View.GONE);
                recyclerViewCompleted.setVisibility(View.VISIBLE);
            }
        });

        setPendingAdaptar();
        setOngoingAdaptar();
        setCompleteAdaptar();
    }

    private void setPendingAdaptar() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewPending.setLayoutManager(RecyclerViewLayoutManager);
        mypendingadaptar = new MyPendingAdaptar(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPending.setAdapter(mypendingadaptar);
    }

    private void setOngoingAdaptar() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOngoing.setLayoutManager(RecyclerViewLayoutManager);
        myongoingadaptar = new MyOngoingAdaptar(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewOngoing.setAdapter(myongoingadaptar);
    }

    private void setCompleteAdaptar() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCompleted.setLayoutManager(RecyclerViewLayoutManager);
        mycompletedadaptar = new MyCompletedAdaptar(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCompleted.setAdapter(mycompletedadaptar);
    }

}