
package com.herbal.herbalfax.vendor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
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
import com.herbal.herbalfax.customer.homescreen.edit.EditProfileActivity;
import com.herbal.herbalfax.customer.homescreen.feed.FeedFragment;
import com.herbal.herbalfax.customer.homescreen.getusermodel.GetUserResponse;
import com.herbal.herbalfax.customer.interfaces.OnInnerFragmentClicks;
import com.herbal.herbalfax.customer.store.StoreDetailsActivity;
import com.herbal.herbalfax.vendor.sellerdeals.SellerDealsFragment;
import com.herbal.herbalfax.vendor.sellerdrivers.SellerDriverFragment;
import com.herbal.herbalfax.vendor.sellerorders.SellerOrderFragment;
import com.herbal.herbalfax.vendor.sellerproduct.SellerProductFragment;
import com.herbal.herbalfax.vendor.storelist.SellerStoreListFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerLandingPageActivity extends AppCompatActivity implements OnInnerFragmentClicks {
    private CommonClass clsCommon;

    private static final float END_SCALE = 0.85f;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private TextView headerTxt;
    private ImageView headerIcon;
    private List<String> drawerItem;
    private SellerDrawerAdapter drawerAdapter;
    private CoordinatorLayout contentView;
    LinearLayoutManager linearLayoutManager;
    private ImageView crossToolBarImage;
    private RecyclerView drawerRecylerView;
    Context mContext;

    SessionPref pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_landing_page);
        mContext = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        clsCommon = CommonClass.getInstance();
        drawer=findViewById(R.id.drawerLayout);
        headerIcon=findViewById(R.id.headerIcon);
        headerTxt=findViewById(R.id.headerTxt);
        crossToolBarImage = findViewById(R.id.crossToolBarImage);
        drawerRecylerView=findViewById(R.id.drawerRecylerView);
        pref = SessionPref.getInstance(this);
        callGetUserAPI();
        addDrawerLayoutItem();
        initToolbar();
        initNavigation();
        setAdapter();
        setOnClick();

    }

    private void setOnClick()
    {
        crossToolBarImage.setOnClickListener(v -> onDrawer());
    }


    public void addDrawerLayoutItem() {
        drawerItem = new ArrayList<>();
        drawerItem.add(null);
        drawerItem.add(getResources().getString(R.string.mySocial));
        drawerItem.add(getResources().getString(R.string.action_notification));
        drawerItem.add(getResources().getString(R.string.my_driver));
        drawerItem.add(getResources().getString(R.string.become_HB_choice));
        drawerItem.add(null);


    }

    public void setAdapter() {

        drawerAdapter = new SellerDrawerAdapter(SellerLandingPageActivity.this, drawerItem);
        linearLayoutManager = new LinearLayoutManager(SellerLandingPageActivity.this, LinearLayoutManager.VERTICAL, false);
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
                        Log.e("Seller ", "Seller..");
                    } else {
                        clsCommon.showDialogMsg(SellerLandingPageActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(SellerLandingPageActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(SellerLandingPageActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<GetUserResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(SellerLandingPageActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initNavigation() {
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);


        bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    ReplaceFrag(new SellerStoreListFragment());
                    headerTxt.setVisibility(View.GONE);
                    headerIcon.setVisibility(View.VISIBLE);

                    return true;
                case R.id.bottom_dashboard:
                    ReplaceFrag(new SellerProductFragment());
                    headerTxt.setVisibility(View.VISIBLE);
                    headerIcon.setVisibility(View.GONE);
                    headerTxt.setText(R.string.my_products);

                    return true;
                case R.id.bottom_notifications:

                    ReplaceFrag(new SellerOrderFragment());
                    headerTxt.setVisibility(View.VISIBLE);
                    headerIcon.setVisibility(View.GONE);
                    headerTxt.setText(R.string.my_orders);
                    return true;
                case R.id.bottom_deal:

                    ReplaceFrag(new SellerDealsFragment());
                    headerTxt.setVisibility(View.VISIBLE);
                    headerIcon.setVisibility(View.GONE);
                    headerTxt.setText(R.string.my_deal);
                    return true;
                case R.id.bottom_askfax:

                    ReplaceFrag(new SellerDriverFragment());
                    headerTxt.setVisibility(View.VISIBLE);
                    headerIcon.setVisibility(View.GONE);
                    headerTxt.setText(R.string.my_driver);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_notification:
//                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
//                startActivity(intent);
//                return true;
            case R.id.action_search:
//                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
//                startActivity(intent);
                return true;

            case R.id.action_map:
                Intent intent1 = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent1);
                return true;

            case R.id.action_cart:
                Intent intent2 = new Intent(getApplicationContext(), StoreDetailsActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
