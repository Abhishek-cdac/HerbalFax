
package com.herbal.herbalfax.customer.homescreen.homedashboard;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.askfax.AskFaxFragment;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.AddToCartActivity;
import com.herbal.herbalfax.customer.homescreen.deals.DealsFragment;
import com.herbal.herbalfax.customer.homescreen.feed.FeedFragment;
import com.herbal.herbalfax.customer.homescreen.getusermodel.GetUserResponse;
import com.herbal.herbalfax.customer.homescreen.group.GroupsFragment;
import com.herbal.herbalfax.customer.homescreen.nearbystores.NearByActivity;
import com.herbal.herbalfax.customer.homescreen.products.ProductsFragment;
import com.herbal.herbalfax.customer.interfaces.OnInnerFragmentClicks;
import com.herbal.herbalfax.customer.post.AddPostActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPageActivity extends AppCompatActivity implements OnInnerFragmentClicks,View.OnClickListener {
    private CommonClass clsCommon;

    private static final float END_SCALE = 0.85f;
    private DrawerLayout drawer;
    LinearLayoutManager linearLayoutManager;
    private RecyclerView drawerRecylerView;
    private ImageView crossToolBarImage;
    private ImageView fab;
    private DrawerAdapter drawerAdapter;
    private ImageView headerIcon,searchIcon,locationIcon,cardIcon;
    private TextView headerTxt,createGroup,addQuestionTxt;
    private List<String> drawerItem;
    private CoordinatorLayout contentView;
    Context mContext;
    SessionPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        mContext = getApplicationContext();
        clsCommon = CommonClass.getInstance();
        drawer=findViewById(R.id.drawerLayout);
        crossToolBarImage = findViewById(R.id.crossToolBarImage);
        drawerRecylerView=findViewById(R.id.drawerRecylerView);
        pref = SessionPref.getInstance(this);
        callGetUserAPI();
        initToolbar();
        initFab();
        initNavigation();
        addDrawerLayoutItem();
        setAdapter();
        setOnClick();
    }

    private void setOnClick()
    {
        crossToolBarImage.setOnClickListener(v -> onDrawer());
        cardIcon.setOnClickListener(this);
        locationIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
    }



    public void addDrawerLayoutItem() {
        drawerItem = new ArrayList<>();
        drawerItem.add(null);
        drawerItem.add(getResources().getString(R.string.menu_home));
        drawerItem.add(getResources().getString(R.string.nearbystore));
        drawerItem.add(getResources().getString(R.string.mySocial));
        drawerItem.add(getResources().getString(R.string.saved_dispensaries));
        drawerItem.add(getResources().getString(R.string.explore_strains));
        drawerItem.add(getResources().getString(R.string.orders));
        drawerItem.add(getResources().getString(R.string.event));
        drawerItem.add(getResources().getString(R.string.card_list));
        drawerItem.add(getResources().getString(R.string.parcels));
        drawerItem.add(getResources().getString(R.string.action_notification));
        drawerItem.add(null);


    }


    private void callGetUserAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<GetUserResponse> call = service.getUserprofile("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call, @NonNull Response<GetUserResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                    } else {
                        clsCommon.showDialogMsg(LandingPageActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(LandingPageActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(LandingPageActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<GetUserResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(LandingPageActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {
        headerIcon=findViewById(R.id.headerIcon);
        searchIcon=findViewById(R.id.searchIcon);
        locationIcon=findViewById(R.id.locationIcon);
        cardIcon=findViewById(R.id.cardIcon);
        headerTxt=findViewById(R.id.headerTxt);
        createGroup=findViewById(R.id.createGroup);
        addQuestionTxt=findViewById(R.id.addQuestionTxt);

    }

    private void initFab() {

        fab= findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), AddPostActivity.class);
            intent.putExtra("PostGroupId", "0");
            startActivity(intent);
        });

    }

    private void initNavigation() {
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);


        bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    ReplaceFrag(new FeedFragment());
                    headerTxt.setVisibility(View.GONE);
                    headerIcon.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.VISIBLE);
                    createGroup.setVisibility(View.GONE);
                    addQuestionTxt.setVisibility(View.GONE);
                    locationIcon.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    cardIcon.setVisibility(View.VISIBLE);
                    return true;
                case R.id.bottom_dashboard:
                    ReplaceFrag(new ProductsFragment());
                    headerTxt.setVisibility(View.VISIBLE);
                    headerTxt.setText(getResources().getString(R.string.product));
                    headerIcon.setVisibility(View.GONE);
                    createGroup.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    addQuestionTxt.setVisibility(View.GONE);
                    searchIcon.setVisibility(View.GONE);
                    locationIcon.setVisibility(View.GONE);
                    cardIcon.setVisibility(View.GONE);
                    return true;
                case R.id.bottom_notifications:
                    headerTxt.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);
                    headerTxt.setText(getResources().getString(R.string.groups));
                    headerIcon.setVisibility(View.GONE);
                    createGroup.setVisibility(View.VISIBLE);
                    ReplaceFrag(new GroupsFragment());
                    searchIcon.setVisibility(View.GONE);
                    addQuestionTxt.setVisibility(View.GONE);
                    locationIcon.setVisibility(View.GONE);
                    cardIcon.setVisibility(View.GONE);
                    return true;
                case R.id.bottom_deal:
                    headerTxt.setVisibility(View.VISIBLE);
                    headerTxt.setText(getResources().getString(R.string.deal));
                    headerIcon.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    createGroup.setVisibility(View.GONE);
                    addQuestionTxt.setVisibility(View.GONE);
                    ReplaceFrag(new DealsFragment());
                    searchIcon.setVisibility(View.GONE);
                    locationIcon.setVisibility(View.GONE);
                    cardIcon.setVisibility(View.VISIBLE);
                    return true;
                case R.id.bottom_askfax:
                    headerTxt.setVisibility(View.VISIBLE);
                    createGroup.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    headerTxt.setText(getResources().getString(R.string.ask_fax));
                    headerIcon.setVisibility(View.GONE);
                    ReplaceFrag(new AskFaxFragment());
                    addQuestionTxt.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.GONE);
                    locationIcon.setVisibility(View.GONE);
                    cardIcon.setVisibility(View.GONE);
                    return true;
            }
            return false;
        });
    }

    public void onDrawer() {
        drawer.openDrawer(GravityCompat.START);
        if (drawerAdapter != null) {
            drawerAdapter.notifyDataSetChanged();
        }
    }

    public void setAdapter() {

        drawerAdapter = new DrawerAdapter(LandingPageActivity.this, drawerItem);
        linearLayoutManager = new LinearLayoutManager(LandingPageActivity.this, LinearLayoutManager.VERTICAL, false);
        drawerRecylerView.setLayoutManager(linearLayoutManager);
        drawerRecylerView.setHasFixedSize(true);
        drawerRecylerView.setItemAnimator(new DefaultItemAnimator());
        drawerRecylerView.setAdapter(drawerAdapter);
        drawerRecylerView.setNestedScrollingEnabled(false);

        drawerAdapter.setOnCardItemClickListener((View view, int position) -> {
                    int id = view.getId();
            if (id == R.id.linear_layout_select) {
                onBackPressed();
            }
                }
        );
        drawerAdapter.setOnLogoutListener(() -> {
            SessionPref.logout(mContext);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

        drawerAdapter.setOnCancelListener(() -> {
            onBackPressed();

        });


    }


    private void animateNavigationDrawer() {
//        drawerLayout.setScrimColor(getResources().getColor(R.color.text_brown));
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }

    }


    @Override
    public void ReplaceFrag(@Nullable Fragment fragment) {
        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            if (fragmentManager.getFragments().size() > 0) {
                assert fragment != null;
                ft.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName());
            } else {
                assert fragment != null;
                ft.add(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName());
            }

            ft.commitAllowingStateLoss();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ReplaceFragWithStack(@Nullable Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.hide(new FeedFragment());
            assert fragment != null;
            ft.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName());
            ft.addToBackStack("tags");
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.locationIcon:
                Intent intent1 = new Intent(getApplicationContext(), NearByActivity.class);
                startActivity(intent1);
                break;
            case R.id.cardIcon:
                Intent intent2 = new Intent(getApplicationContext(), AddToCartActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
///*
//
//
//            case R.id.action_notification:
//                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
//                startActivity(intent);
//                return true;
//*/
//            case R.id.action_map:
//                //   ReplaceFrag(new NearByStoreFragment());
//                //    Intent intent1 = new Intent(getApplicationContext(), EventsDetailsActivity.class);
//                //EditProfileActivity.class);
//                Intent intent1 = new Intent(getApplicationContext(), NearByActivity.class);
//                //EditProfileActivity.class);
//                startActivity(intent1);
//                return true;
//
//            case R.id.action_cart:
//                Intent intent2 = new Intent(getApplicationContext(), AddToCartActivity.class);
//                startActivity(intent2);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
