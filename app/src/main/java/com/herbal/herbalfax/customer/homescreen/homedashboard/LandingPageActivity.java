
package com.herbal.herbalfax.customer.homescreen.homedashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.profile.ProfileActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.AddToCartActivity;
import com.herbal.herbalfax.customer.homescreen.edit.EditProfileActivity;
import com.herbal.herbalfax.customer.homescreen.feed.FeedFragment;
import com.herbal.herbalfax.customer.homescreen.getusermodel.GetUserResponse;
import com.herbal.herbalfax.customer.homescreen.nearbystores.NearByActivity;
import com.herbal.herbalfax.customer.interfaces.OnInnerFragmentClicks;
import com.herbal.herbalfax.customer.post.AddPostActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPageActivity extends AppCompatActivity implements OnInnerFragmentClicks {
    private CommonClass clsCommon;

    private static final float END_SCALE = 0.85f;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private CoordinatorLayout contentView;
    private  MenuItem action_search,action_map,action_cart;
    Context mContext;
    SessionPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        mContext = getApplicationContext();
        clsCommon = CommonClass.getInstance();
        pref = SessionPref.getInstance(this);
        callGetUserAPI();
        initToolbar();
        initFab();
        initNavigation();
        //showBottomNavigation(false);
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
                        //  Toast.makeText(LandingPageActivity.this, "success!", Toast.LENGTH_SHORT).show();
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initFab() {

        ImageView fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), AddPostActivity.class);
            intent.putExtra("PostGroupId", "0");
            startActivity(intent);

//                Fragment newCase=new AddPostFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.nav_host_fragment,newCase); // give your fragment container id in first parameter
//                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                transaction.commit();

            //    ReplaceFrag(new OrdersFragment());

        });

    }

    private void initNavigation() {

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_events, R.id.nav_cardlist, R.id.nav_parcel,
                R.id.bottom_home, R.id.bottom_dashboard, R.id.bottom_notifications, R.id.bottom_deal, R.id.bottom_askfax, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        View headerView = navigationView.getHeaderView(0);
        ImageView closeImg = headerView.findViewById(R.id.closeImg);
        CardView editBTn = headerView.findViewById(R.id.editBTn);

        TextView profileName = headerView.findViewById(R.id.userName);
        TextView professionTv = headerView.findViewById(R.id.professionTv);
        LinearLayout nav_headerMAin = headerView.findViewById(R.id.nav_headerMAin);
        profileName.setText(pref.getStringVal(SessionPref.LoginUserfullName));
        // professionTv.setText();
        nav_headerMAin.setOnClickListener(view -> {
            Log.e("ProfileActivity....", "ProfileActivity");
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });
        closeImg.setOnClickListener(view -> drawer.closeDrawer(GravityCompat.START));
        editBTn.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                    startActivity(intent);
                }
        );
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_logout) {
                SessionPref.logout(mContext);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            } else if (id == R.id.nav_gallery) {
                //   ReplaceFrag(new NearByStoreFragment());
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        animateNavigationDrawer();
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

//        action_search= menu.findItem(R.id.action_search);
//        action_map= menu.findItem(R.id.action_map);
//        action_cart= menu.findItem(R.id.action_cart);


        return true;
    }

//    private void hideMenuItem()
//    {
//        action_search.setVisible(false);
//        action_map.setVisible(false);
//        action_cart.setVisible(false);
//    }
//
//    private void showMenuItem()
//    {
//        action_search.setVisible(true);
//        action_map.setVisible(true);
//        action_cart.setVisible(true);
//    }

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
/*


            case R.id.action_notification:
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
                return true;
*/
            case R.id.action_map:
                //   ReplaceFrag(new NearByStoreFragment());
                //    Intent intent1 = new Intent(getApplicationContext(), EventsDetailsActivity.class);
                //EditProfileActivity.class);
                Intent intent1 = new Intent(getApplicationContext(), NearByActivity.class);
                //EditProfileActivity.class);
                startActivity(intent1);
                return true;

            case R.id.action_cart:
                Intent intent2 = new Intent(getApplicationContext(), AddToCartActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
