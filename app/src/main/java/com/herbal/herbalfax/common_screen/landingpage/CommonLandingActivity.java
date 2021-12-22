package com.herbal.herbalfax.common_screen.landingpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.adapter.ArticlesAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.CategoryAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.EventsAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.FindFruityCannabisAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.PhotosAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.TimeAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.TrendingAdapter;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.customer.homescreen.edit.EditProfileActivity;
import com.herbal.herbalfax.customer.notification.NotificationActivity;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.StoreDetailsActivity;

import java.util.ArrayList;

public class CommonLandingActivity extends AppCompatActivity {
    RecyclerView recyclerView, photosRecyclerview, timelyRecyclerview, fruityrecyclerview, trendingRecyclerview, articleRecyclerview, eventsRecyclerview;
    ArrayList<String> source;
    private ArrayList<Interest> lst_fruityCannabis;
    private ArrayList<Interest> lst_trending;
    private ArrayList<Interest> lst_article;
    private ArrayList<Interest> lst_events;
    private ArrayList<Interest> lst_photos;

    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    TrendingAdapter trendingAdapter;
    CategoryAdapter categoryAdapter;
    ArticlesAdapter articlesAdapter;
    EventsAdapter eventsAdapter;
    TimeAdapter timeAdapter;
    PhotosAdapter photosAdapter;
    FindFruityCannabisAdapter findFruityCannabisAdapter;
    LinearLayout login_ll;


    View ChildView;
    int RecyclerViewItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_landing);


        setupUI();
        setUpCategoryRecyclerView();
        setUpFruityRecyclerView();
        setUptredingRecyclerView();
        setUpArticleRecyclerView();
        setUpEventsRecyclerView();
        setUpPhotosRecyclerView();
        setUpTimeyRecyclerView();
    }


    private void setUpFruityRecyclerView() {
        lst_fruityCannabis = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        fruityrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        findFruityCannabisAdapter = new FindFruityCannabisAdapter(lst_fruityCannabis, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        fruityrecyclerview.setLayoutManager(new GridLayoutManager(CommonLandingActivity.this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        fruityrecyclerview.setAdapter(findFruityCannabisAdapter);

    }

    private void setUpPhotosRecyclerView() {
        lst_photos = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        photosRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        photosAdapter = new PhotosAdapter(lst_photos, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        photosRecyclerview.setLayoutManager(new GridLayoutManager(CommonLandingActivity.this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        photosRecyclerview.setAdapter(photosAdapter);

    }

    private void setUpCategoryRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        categoryAdapter = new CategoryAdapter(source, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(categoryAdapter);

    }

    private void setUpTimeyRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        timelyRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToTimeRecyclerViewArrayList();
        timeAdapter = new TimeAdapter(source, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        timelyRecyclerview.setLayoutManager(HorizontalLayout);
        timelyRecyclerview.setAdapter(timeAdapter);

    }

    private void setUptredingRecyclerView() {
        lst_trending = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        trendingRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        trendingAdapter = new TrendingAdapter(lst_trending, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.VERTICAL, false);
        trendingRecyclerview.setLayoutManager(HorizontalLayout);
        trendingRecyclerview.setAdapter(trendingAdapter);

    }

    private void setUpArticleRecyclerView() {
        lst_article = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        articleRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        articlesAdapter = new ArticlesAdapter(lst_article, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        articleRecyclerview.setLayoutManager(HorizontalLayout);
        articleRecyclerview.setAdapter(articlesAdapter);

    }

    private void setUpEventsRecyclerView() {
        lst_events = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        eventsRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        eventsAdapter = new EventsAdapter(lst_events, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        eventsRecyclerview.setLayoutManager(HorizontalLayout);
        eventsRecyclerview.setAdapter(eventsAdapter);

    }

    private void setupUI() {
        recyclerView = findViewById(R.id.caterecyclerview);
        login_ll = findViewById(R.id.login_ll);
        fruityrecyclerview = findViewById(R.id.fruityrecyclerview);
        trendingRecyclerview = findViewById(R.id.trendingrecyclerview);
        articleRecyclerview = findViewById(R.id.articleRecyclerview);
        eventsRecyclerview = findViewById(R.id.eventsRecyclerview);
        photosRecyclerview = findViewById(R.id.photosRecyclerview);
        timelyRecyclerview = findViewById(R.id.timelyRecyclerview);

        login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    public void AddItemsToRecyclerViewArrayList() {
        source = new ArrayList<>();
        source.add("All");
        source.add("Map");
        source.add("News");
        source.add("Shopping");
        source.add("Images");
        source.add("Video");

    }

    public void AddItemsToTimeRecyclerViewArrayList() {
        source = new ArrayList<>();
        source.add("Today");
        source.add("Tomorrow");
        source.add("This Week");
        source.add("This Month");
    }


}