package com.herbal.herbalfax.common_screen.landingpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.adapter.PhotosAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.PhotosAdapter1;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;
import java.util.List;


public class PhotosDetailsActivity extends AppCompatActivity {


    private List<Interest> DealsCategory = new ArrayList<>();
    RecyclerView photosrecycler;
    PhotosAdapter1 photosAdapter;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_details);
        photosrecycler = findViewById(R.id.photosview);
        
        setUpPhotosRecycler();
    }

    private void setUpPhotosRecycler() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        photosrecycler.setLayoutManager(RecyclerViewLayoutManager);
        photosAdapter = new PhotosAdapter1((ArrayList<Interest>) DealsCategory, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        photosrecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        photosrecycler.setAdapter(photosAdapter);
    }



}