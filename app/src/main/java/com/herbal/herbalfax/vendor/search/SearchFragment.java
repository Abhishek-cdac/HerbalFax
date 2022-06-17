package com.herbal.herbalfax.vendor.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.CommonLandingActivity;
import com.herbal.herbalfax.common_screen.landingpage.PhotosDetailsActivity;
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
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.sellerorders.CompleteOrderAdapter;
import com.herbal.herbalfax.vendor.sellerorders.NewOrderAdapter;
import com.herbal.herbalfax.vendor.sellerorders.PendingOrderAdapter;
import com.herbal.herbalfax.vendor.sellerorders.SellerOrdersViewModel;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    RecyclerView recyclerView, photosRecyclerview, timelyRecyclerview, fruityRecyclerview, trendingRecyclerview, articleRecyclerview, eventsRecyclerview;
    ArrayList<String> source;
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
    TextView eventsViewMore;
    ImageView menu_nav;
    ImageView loginToolBar;
    private AppBarConfiguration mAppBarConfiguration;
    public DrawerLayout drawerLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.search_fragment, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView = root.findViewById(R.id.caterecyclerview);
        loginToolBar = root.findViewById(R.id.loginToolBar);
        menu_nav = root.findViewById(R.id.menu_nav);
        login_ll = root.findViewById(R.id.login_ll);
        fruityRecyclerview = root.findViewById(R.id.fruityrecyclerview);
        trendingRecyclerview = root.findViewById(R.id.trendingrecyclerview);
        articleRecyclerview = root.findViewById(R.id.articleRecyclerview);
        eventsRecyclerview = root.findViewById(R.id.eventsRecyclerview);
        photosRecyclerview = root.findViewById(R.id.photosRecyclerview);
        timelyRecyclerview = root.findViewById(R.id.timelyRecyclerview);
        photosViewMore = root.findViewById(R.id.photosviewmore);
        eventsViewMore = root.findViewById(R.id.eventsviewmore);

//        photosViewMore.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), PhotosDetailsActivity.class);
//            startActivity(intent);
//        });
//        loginToolBar.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//        });
//        eventsViewMore.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), EventsDetailsActivity.class);
//            startActivity(intent);
//        });

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        categoryAdapter = new CategoryAdapter(source, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(categoryAdapter);

        ArrayList<Interest> lst_fruityCannabis = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        fruityRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        findFruityCannabisAdapter = new FindFruityCannabisAdapter(lst_fruityCannabis, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        fruityRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        fruityRecyclerview.setAdapter(findFruityCannabisAdapter);


        ArrayList<Interest> lst_trending = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        trendingRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        trendingAdapter = new TrendingAdapter(lst_trending, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        trendingRecyclerview.setLayoutManager(HorizontalLayout);
        trendingRecyclerview.setAdapter(trendingAdapter);


        ArrayList<Interest> lst_article = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        articleRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        articlesAdapter = new ArticlesAdapter(lst_article, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        articleRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        articleRecyclerview.setAdapter(articlesAdapter);


        ArrayList<Interest> lst_events = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        eventsRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        eventsAdapter = new EventsAdapter(lst_events, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        eventsRecyclerview.setLayoutManager(HorizontalLayout);
        eventsRecyclerview.setAdapter(eventsAdapter);

        ArrayList<Interest> lst_photos = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        photosRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        photosAdapter = new PhotosAdapter(lst_photos, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        photosRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        photosRecyclerview.setAdapter(photosAdapter);


        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        timelyRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToTimeRecyclerViewArrayList();
        timeAdapter = new TimeAdapter(source, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        timelyRecyclerview.setLayoutManager(HorizontalLayout);
        timelyRecyclerview.setAdapter(timeAdapter);



        initNavigation();
        return root;
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




    private void initNavigation() {

//        drawerLayout = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view1);
////        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
//        contentView = findViewById(R.id.content_view);
//        menu_nav.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
//
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_events ,
//                R.id.nav_login)
//                .setDrawerLayout(drawerLayout)
//                .build();
//
//        View headerView = navigationView.getHeaderView(0);
//        ImageView closeImg = headerView.findViewById(R.id.closeImg);
//        closeImg.setOnClickListener(view -> drawerLayout.closeDrawer(GravityCompat.START));
//
//
//
//        navigationView.setNavigationItemSelectedListener(menuItem -> {
//            int id = menuItem.getItemId();
//            if (id == R.id.nav_login) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//            drawerLayout.closeDrawer(GravityCompat.START);
//            return true;
//        });
//
//        animateNavigationDrawer();
    }


    private void setUpFruityRecyclerView() {


    }

    private void setUpPhotosRecyclerView() {


    }

    private void setUpCategoryRecyclerView() {
//        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
//        AddItemsToRecyclerViewArrayList();
//        categoryAdapter = new CategoryAdapter(source, getApplicationContext());
//        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(HorizontalLayout);
//        recyclerView.setAdapter(categoryAdapter);

    }

    private void setUpTimeRecyclerView() {
//        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
//        timelyRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
//        AddItemsToTimeRecyclerViewArrayList();
//        timeAdapter = new TimeAdapter(source, getApplicationContext());
//        HorizontalLayout = new LinearLayoutManager(CommonLandingActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        timelyRecyclerview.setLayoutManager(HorizontalLayout);
//        timelyRecyclerview.setAdapter(timeAdapter);

    }

    private void setUpTrendingRecyclerView() {


    }

    private void setUpArticleRecyclerView() {


    }

    private void setUpEventsRecyclerView() {

    }


    private void setupUI() {

    }


    public void AddItemsToRecyclerViewArrayList() {
        source = new ArrayList<>();
        source.add("All");
        source.add("Maps");
        source.add("News");
        source.add("Shopping");
        source.add("Images");
        source.add("Videos");

    }

    public void AddItemsToTimeRecyclerViewArrayList() {
        source = new ArrayList<>();
        source.add("Today");
        source.add("Tomorrow");
        source.add("This Week");
        source.add("This Month");
    }

}