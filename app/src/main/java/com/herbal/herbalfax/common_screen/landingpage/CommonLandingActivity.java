package com.herbal.herbalfax.common_screen.landingpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.adapter.ArticlesAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.CategoryAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.EventsAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.FindFruityCannabisAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.PhotosAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.TimeAdapter;
import com.herbal.herbalfax.common_screen.landingpage.adapter.TrendingAdapter;
import com.herbal.herbalfax.common_screen.landingpage.events.eventlist.EventsDetailsActivity;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class CommonLandingActivity extends AppCompatActivity {
    RecyclerView recyclerView, photosRecyclerview, timelyRecyclerview, fruityrecyclerview, trendingRecyclerview, articleRecyclerview, eventsRecyclerview;
    ArrayList<String> source;
    private ArrayList<Interest> lst_fruityCannabis;
    private ArrayList<Interest> lst_trending;
    private ArrayList<Interest> lst_article;
    private ArrayList<Interest> lst_events;
    private ArrayList<Interest> lst_photos;
    private LinearLayout contentView;

    private static final float END_SCALE = 0.85f;
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
    TextView photosViewMore;
    TextView eventsviewmore;
    ImageView menu_nav;
    private AppBarConfiguration mAppBarConfiguration;


    View ChildView;
    int RecyclerViewItemPosition;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_landing_nav);


        setupUI();
        setUpCategoryRecyclerView();
        setUpFruityRecyclerView();
        setUptredingRecyclerView();
        setUpArticleRecyclerView();
        setUpEventsRecyclerView();
        setUpPhotosRecyclerView();
        setUpTimeyRecyclerView();
        initNavigation();

    }


    private void animateNavigationDrawer() {
//        drawerLayout.setScrimColor(getResources().getColor(R.color.text_brown));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }

    }

    private void initNavigation() {

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view1);
//        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);
        menu_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_events , // R.id.nav_cardlist, R.id.nav_parcel,
                R.id.nav_login)
                .setDrawerLayout(drawerLayout)
                .build();
       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);



//        NavigationUI.setupWithNavController(navigationView, navController);
//        NavigationUI.setupWithNavController(bottomNavView, navController);

        View headerview = navigationView.getHeaderView(0);
        ImageView closeImg = headerview.findViewById(R.id.closeImg);
        closeImg.setOnClickListener(view -> drawerLayout.closeDrawer(GravityCompat.START));

        /*
         CardView editBTn = headerview.findViewById(R.id.editBTn);

        TextView profileName = headerview.findViewById(R.id.userName);
        TextView professionTv = headerview.findViewById(R.id.professionTv);
        LinearLayout nav_headerMAin = headerview.findViewById(R.id.nav_headerMAin);
        profileName.setText(pref.getStringVal(SessionPref.LoginUserfullName));
        // professionTv.setText();
        nav_headerMAin.setOnClickListener(view -> {
            Log.e("ProfileActivity....", "ProfileActivity");
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });
        editBTn.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                    startActivity(intent);
                }
        );*/

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_login) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

            animateNavigationDrawer();
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
        menu_nav = findViewById(R.id.menu_nav);
        login_ll = findViewById(R.id.login_ll);
        fruityrecyclerview = findViewById(R.id.fruityrecyclerview);
        trendingRecyclerview = findViewById(R.id.trendingrecyclerview);
        articleRecyclerview = findViewById(R.id.articleRecyclerview);
        eventsRecyclerview = findViewById(R.id.eventsRecyclerview);
        photosRecyclerview = findViewById(R.id.photosRecyclerview);
        timelyRecyclerview = findViewById(R.id.timelyRecyclerview);
        photosViewMore = findViewById(R.id.photosviewmore);
        eventsviewmore = findViewById(R.id.eventsviewmore);


//        login_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        photosViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhotosDetailsActivity.class);
                startActivity(intent);
            }
        });
        eventsviewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventsDetailsActivity.class);
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