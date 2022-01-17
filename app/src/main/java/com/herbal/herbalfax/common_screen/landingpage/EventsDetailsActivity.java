package com.herbal.herbalfax.common_screen.landingpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.adapter.EventsItemsMenuAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.EventsMenuAdapter;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.NewDealsAdaptor;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.adapter.StoreDealsAdapter;

import java.util.ArrayList;
import java.util.List;

public class EventsDetailsActivity extends AppCompatActivity {

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private List<Interest> DealsCategory = new ArrayList<>();
    LinearLayoutManager HorizontalLayout;
    EventsMenuAdapter eventsMenuAdapter;
    RecyclerView   eventsviewRecycler;
    RecyclerView eventsitemsbarRecycler;
    TextView addEvents;
    EventsItemsMenuAdapter eventsItemsMenuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);
        eventsviewRecycler = findViewById(R.id.eventsmenubar);
        eventsitemsbarRecycler = findViewById(R.id.eventsitemsbar);
        addEvents = findViewById(R.id.addeventdetails);

        addEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(intent);
            }
        });
        setEventsMenuBar();
        setEventsDetailsBar();
    }

    private void setEventsMenuBar() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        eventsviewRecycler.setLayoutManager(RecyclerViewLayoutManager);
        eventsMenuAdapter = new EventsMenuAdapter((ArrayList<Interest>) DealsCategory, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        eventsviewRecycler.setLayoutManager(HorizontalLayout);
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        eventsviewRecycler.setAdapter(eventsMenuAdapter);
    }

    private void setEventsDetailsBar() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        eventsitemsbarRecycler.setLayoutManager(RecyclerViewLayoutManager);
        eventsItemsMenuAdapter = new EventsItemsMenuAdapter((ArrayList<Interest>) DealsCategory,getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        eventsitemsbarRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        eventsitemsbarRecycler.setAdapter(eventsItemsMenuAdapter);
    }
}