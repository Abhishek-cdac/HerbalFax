package com.herbal.herbalfax.customer.store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.adapter.StorePhotosAdapter;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.Store;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreBusinessHr;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreDetailResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDetailsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TextView HeadingTxt, addressTxt, ratingTxt, likeTxt, viewTxt, headerTxt;
    ImageView imgprofile;
    ViewPager viewPager;
    StorePhotosAdapter storePhotosAdapter;
    private ArrayList<Interest> lst_photos;
    RelativeLayout contact_owner_rl;
    ConstraintLayout tab_constraint;
    TextView contact_owner_Txt, locateOnMapTxt, storePhotoTxt;
    RecyclerView photosRecyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    String storeId;
    ImageView backBtn;
    private CommonClass clsCommon;
    ImageView ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                storeId = extras.getString("storeId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clsCommon = CommonClass.getInstance();

        viewTxt = findViewById(R.id.viewTxt);
        likeTxt = findViewById(R.id.likeTxt);
        addressTxt = findViewById(R.id.addressTxt);
        ratingTxt = findViewById(R.id.ratingTxt);
        HeadingTxt = findViewById(R.id.HeadingTxt);
        imgprofile = findViewById(R.id.imgprofile);
        backBtn = findViewById(R.id.backBtn);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        contact_owner_rl = findViewById(R.id.contact_owner_rl);
        tab_constraint = findViewById(R.id.tab_constraint);
        locateOnMapTxt = findViewById(R.id.locateOnMapTxt);
        contact_owner_Txt = findViewById(R.id.contact_owner_Txt);
        storePhotoTxt = findViewById(R.id.storePhotoTxt);
        photosRecyclerview = findViewById(R.id.photosRecyclerview);
        ratingOne = findViewById(R.id.ratingOne);
        ratingTwo = findViewById(R.id.ratingTwo);
        ratingThree = findViewById(R.id.ratingThree);
        ratingFour = findViewById(R.id.ratingFour);
        ratingFive = findViewById(R.id.ratingFive);
        headerTxt = findViewById(R.id.headerTxt);

        callUserStoreDetailAPI(storeId);
        backBtn.setOnClickListener(view -> onBackPressed());
        storePhotoTxt.setOnClickListener(view -> {
            tab_constraint.setVisibility(View.GONE);
            photosRecyclerview.setVisibility(View.VISIBLE);
            contact_owner_rl.setVisibility(View.GONE);

        });
        locateOnMapTxt.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), StoreLocateActivity.class);
            startActivity(intent);
        });

        contact_owner_Txt.setOnClickListener(view -> {
            photosRecyclerview.setVisibility(View.GONE);
            tab_constraint.setVisibility(View.GONE);
            contact_owner_rl.setVisibility(View.VISIBLE);
        });

        setUpPhotosRecyclerView();

        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Deals"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyStoreDetailTabAdapter adapter = new MyStoreDetailTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(), storeId);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpPhotosRecyclerView() {
        lst_photos = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        photosRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        storePhotosAdapter = new StorePhotosAdapter(lst_photos, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(StoreDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        photosRecyclerview.setLayoutManager(new GridLayoutManager(StoreDetailsActivity.this, 3));
        photosRecyclerview.setAdapter(storePhotosAdapter);
    }

    private void callUserStoreDetailAPI(String storeId) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", storeId);

//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getApplicationContext());
//        pd.show();
        Call<StoreDetailResponse> call = service.userStoreDetails("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<StoreDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<StoreDetailResponse> call, @NonNull Response<StoreDetailResponse> response) {
                // pd.cancel();
                if (response.code() == 200) {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        final Store store = response.body().getData().getStore();

                        HeadingTxt.setText(store.getStoreName());
                        headerTxt.setText(store.getStoreName());

                        if (store.getStoreLogo() != null) {
                            if (!store.getStoreLogo().equals("")) {
                                Picasso.get().load(store.getStoreLogo())
                                        .into(imgprofile);
                            }
                        }

                        ratingTxt.setText(store.getStoreRating());
                        addressTxt.setText(store.getStoreLocation());
                        likeTxt.setText(store.getStoreFavs());
                        viewTxt.setText(store.getStoreViews());
                        //desc.setText(store.getStoreDesc());
                        if (store.getStoreRating().equals("0")) {
                            ratingOne.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

                        } else if (store.getStoreRating().equals("1")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_rating);
                            ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

                        } else if (store.getStoreRating().equals("2")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_rating);
                            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
                            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

                        } else if (store.getStoreRating().equals("3")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_rating);
                            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
                            ratingThree.setImageResource(R.drawable.ic_icon_rating);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

                        } else if (store.getStoreRating().equals("4")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_rating);
                            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
                            ratingThree.setImageResource(R.drawable.ic_icon_rating);
                            ratingFour.setImageResource(R.drawable.ic_icon_rating);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);

                        } else if (store.getStoreRating().equals("5")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_rating);
                            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
                            ratingThree.setImageResource(R.drawable.ic_icon_rating);
                            ratingFour.setImageResource(R.drawable.ic_icon_rating);
                            ratingFive.setImageResource(R.drawable.ic_icon_rating);
                        }


                        ArrayList<StoreBusinessHr> lst_store_business_hrs = (ArrayList<StoreBusinessHr>) response.body().getData().getStoreBusinessHrs();


                    } else {
                        clsCommon.showDialogMsgFrag(StoreDetailsActivity.this
                                , "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(StoreDetailsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(StoreDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(@NotNull Call<StoreDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                //pd.cancel();
                Toast.makeText(StoreDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}