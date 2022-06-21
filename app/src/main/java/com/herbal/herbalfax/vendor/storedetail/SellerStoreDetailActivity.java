package com.herbal.herbalfax.vendor.storedetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.store.AddStoreActivity;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.Store;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreBusinessHr;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreDetailResponse;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreRatings;
/*import com.squareup.picasso.Picasso;*/

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerStoreDetailActivity extends AppCompatActivity {
    String storeId;
    ArrayList<Interest> source;
    private CommonClass clsCommon;
    TextView reviewll, checkinll;
    RecyclerView StoreDetailrecyclerview, Checkinrecyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    SellerStoreReviewAdapter storeReviewAdapter;
    StoreCheckinAdapter storeCheckinAdapter;
    TextView headerTxt, title, ratingTxt, storeLocation, storeFav, storeView, desc, placeholder, workingdays;
    ImageView storeLogoImg, edit, delete;
    ImageView ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive;
ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_store_detail);
        clsCommon = CommonClass.getInstance();
        StoreDetailrecyclerview = findViewById(R.id.StoreDetailrecyclerview);
        Checkinrecyclerview = findViewById(R.id.Checkinrecyclerview);
        backBtn = findViewById(R.id.backBtn);
        reviewll = findViewById(R.id.reviewll);
        checkinll = findViewById(R.id.checkinll);
        /*edit = findViewById(R.id.edit);*/
       /* delete = findViewById(R.id.delete);*/
        placeholder = findViewById(R.id.placeholder);

        ratingOne = findViewById(R.id.one);
        ratingTwo = findViewById(R.id.two);
        ratingThree = findViewById(R.id.three);
        ratingFour = findViewById(R.id.four);
        ratingFive = findViewById(R.id.five);
        headerTxt = findViewById(R.id.headerTxt);
        storeLogoImg = findViewById(R.id.storeLogoImg);
        title = findViewById(R.id.title);
        ratingTxt = findViewById(R.id.ratingTxt);
        storeLocation = findViewById(R.id.storeLocation);
        storeFav = findViewById(R.id.storeFav);
        storeView = findViewById(R.id.storeView);
        desc = findViewById(R.id.desc);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reviewll.setOnClickListener(view -> {
            StoreDetailrecyclerview.setVisibility(View.VISIBLE);
            Checkinrecyclerview.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
            reviewll.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            checkinll.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            reviewll.setTextColor(getResources().getColor(R.color.white));
            checkinll.setTextColor(getResources().getColor(R.color.black));

        });

        checkinll.setOnClickListener(view -> {
            StoreDetailrecyclerview.setVisibility(View.GONE);
            Checkinrecyclerview.setVisibility(View.VISIBLE);
            placeholder.setVisibility(View.GONE);
            reviewll.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            checkinll.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            checkinll.setTextColor(getResources().getColor(R.color.white));
            reviewll.setTextColor(getResources().getColor(R.color.black));

        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeId = getIntent().getExtras().getString("storeId");
            Log.e("storeId data ", "" + storeId);
        }
        setUpCheckinRecyclerView();

  /*      edit.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddStoreActivity.class);
            intent.putExtra("type", "edit");
            intent.putExtra("storeId", storeId);
            startActivity(intent);
        });
        delete.setOnClickListener(view -> {

        });
*/
        callSellerStoreDetailAPI(storeId);
        workingdays = findViewById(R.id.workingdays);
        workingdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellerStoreDetailActivity.this,R.style.BuyAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.workingdaysalter, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getWindow().setLayout(500, 600);
                alertDialog.getWindow().setGravity(Gravity.CENTER);
            }
        });


    }

    private void setUpCheckinRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        Checkinrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        storeCheckinAdapter = new StoreCheckinAdapter(source, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(SellerStoreDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        Checkinrecyclerview.setLayoutManager(HorizontalLayout);
        Checkinrecyclerview.setAdapter(storeCheckinAdapter);
    }


    private void callSellerStoreDetailAPI(String storeId) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", storeId);

//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getApplicationContext());
//        pd.show();
        Call<StoreDetailResponse> call = service.storeDetails("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<StoreDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<StoreDetailResponse> call, @NonNull Response<StoreDetailResponse> response) {
                // pd.cancel();
                if (response.code() == 200) {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        final Store store = response.body().getData().getStore();

                        headerTxt.setText(store.getStoreName());
                        title.setText(store.getStoreName());
                        if (store.getStoreLogo() != null) {
                            if (!store.getStoreLogo().equals("")) {
                               /* Picasso.get().load(store.getStoreLogo())
                                        .into(storeLogoImg);*/
                            }
                        }

                        ratingTxt.setText(store.getStoreRating());
                        storeLocation.setText(store.getStoreLocation());
                        storeFav.setText(store.getStoreFavs());
                        storeView.setText(store.getStoreViews());
                        desc.setText(store.getStoreDesc());

                        if (store.getStoreRating().equals("0")) {
                            ratingOne.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
                        } else if (store.getStoreRating().equals("1")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
                        } else if (store.getStoreRating().equals("2")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
                        } else if (store.getStoreRating().equals("3")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingThree.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
                        } else if (store.getStoreRating().equals("4")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingThree.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingFour.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
                        } else if (store.getStoreRating().equals("5")) {
                            ratingOne.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingTwo.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingThree.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingFour.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                            ratingFive.setImageResource(R.drawable.ic_icon_metro_star_full_yellow);
                        }

                        ArrayList<StoreBusinessHr> lst_store_business_hrs = (ArrayList<StoreBusinessHr>) response.body().getData().getStoreBusinessHrs();

                        Log.e("lst_store_detail", "inside");
                        if (lst_store_business_hrs == null) {
                            lst_store_business_hrs = new ArrayList<>();
                        }

                        ArrayList<StoreRatings> lst_store_ratings = (ArrayList<StoreRatings>) response.body().getData().getStoreRatings();


                        if (lst_store_ratings == null) {
                            lst_store_ratings = new ArrayList<>();
                        }

                        if (lst_store_ratings.size() == 0) {
                            placeholder.setVisibility(View.VISIBLE);
                            StoreDetailrecyclerview.setVisibility(View.GONE);
                        } else {
                            placeholder.setVisibility(View.GONE);
                            StoreDetailrecyclerview.setVisibility(View.VISIBLE);
                            RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                            StoreDetailrecyclerview.setLayoutManager(RecyclerViewLayoutManager);
                            storeReviewAdapter = new SellerStoreReviewAdapter(lst_store_ratings, getApplicationContext());
                            HorizontalLayout = new LinearLayoutManager(SellerStoreDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                            StoreDetailrecyclerview.setLayoutManager(HorizontalLayout);
                            StoreDetailrecyclerview.setAdapter(storeReviewAdapter);
                        }


                    } else {

                        clsCommon.showDialogMsgFrag(SellerStoreDetailActivity.this
                                , "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(SellerStoreDetailActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(SellerStoreDetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(@NotNull Call<StoreDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                //pd.cancel();
                Toast.makeText(SellerStoreDetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}