package com.herbal.herbalfax.customer.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.commonmodel.CommonResponse;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.Store;


import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRatingAndReviewActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView storeLogo, ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive, backBtn;
    TextView storeNameTxt, StoreRatingAndReviewTxt;
    String rateStarStr = "0";
    CommonClass clsCommon;
    Button submit_button, cancel_button;
    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_rating_and_review);
        clsCommon = CommonClass.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeId = getIntent().getExtras().getString("storeId");
            Log.e("StoreRatingAndReview", "" + storeId);
        }

        cancel_button = findViewById(R.id.cancel_button);
        backBtn = findViewById(R.id.backBtn);
        storeLogo = findViewById(R.id.logo);
        storeNameTxt = findViewById(R.id.storeNameTxt);
        ratingOne = findViewById(R.id.ratingOne);
        ratingTwo = findViewById(R.id.ratingTwo);
        ratingThree = findViewById(R.id.ratingThree);
        ratingFour = findViewById(R.id.ratingFour);
        ratingFive = findViewById(R.id.ratingFive);
        submit_button = findViewById(R.id.submit_button);

        StoreRatingAndReviewTxt = findViewById(R.id.StoreRatingAndReviewTxt);

        ratingOne.setOnClickListener(view -> {
            rateStarStr = "1";
            ratingOne.setImageResource(R.drawable.ic_icon_rating);
            ratingTwo.setImageResource(R.drawable.ic_baseline_star_24);
            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
            /*ic_icon_rating*/
            Log.e("rateStarStr", "" + rateStarStr);
        });
        ratingTwo.setOnClickListener(view -> {
            rateStarStr = "2";
            ratingOne.setImageResource(R.drawable.ic_icon_rating);
            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            ratingThree.setImageResource(R.drawable.ic_baseline_star_24);
            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
            Log.e("rateStarStr", "" + rateStarStr);
        });
        ratingThree.setOnClickListener(view -> {
            rateStarStr = "3";
            ratingOne.setImageResource(R.drawable.ic_icon_rating);
            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            ratingThree.setImageResource(R.drawable.ic_icon_rating);
            ratingFour.setImageResource(R.drawable.ic_baseline_star_24);
            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
            Log.e("rateStarStr", "" + rateStarStr);
        });
        ratingFour.setOnClickListener(view -> {
            rateStarStr = "4";
            ratingOne.setImageResource(R.drawable.ic_icon_rating);
            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            ratingThree.setImageResource(R.drawable.ic_icon_rating);
            ratingFour.setImageResource(R.drawable.ic_icon_rating);
            ratingFive.setImageResource(R.drawable.ic_baseline_star_24);
            Log.e("rateStarStr", "" + rateStarStr);
        });
        ratingFive.setOnClickListener(view -> {
            rateStarStr = "5";
            ratingOne.setImageResource(R.drawable.ic_icon_rating);
            ratingTwo.setImageResource(R.drawable.ic_icon_rating);
            ratingThree.setImageResource(R.drawable.ic_icon_rating);
            ratingFour.setImageResource(R.drawable.ic_icon_rating);
            ratingFive.setImageResource(R.drawable.ic_icon_rating);

            Log.e("rateStarStr", "" + rateStarStr);
        });

        submit_button.setOnClickListener(view -> {
            if (rateStarStr.equals("0")) {
                clsCommon.showDialogMsgFrag(StoreRatingAndReviewActivity.this
                        , "HerbalFax", "Please Select rating Star", "Ok");
            } else {
                callUserStoreAddRatingAPI();
            }
        });
        cancel_button.setOnClickListener(view -> {
            onBackPressed();
        });
        backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callUserStoreAddRatingAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", storeId); //1
        hashMap.put("rate_stars", rateStarStr); //4
        hashMap.put("review", StoreRatingAndReviewTxt.getText().toString());

        Call<CommonResponse> call = service.userStoreAddRating("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                // pd.cancel();
                if (response.code() == 200) {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Intent intent= new Intent(getApplicationContext(), StoreDetailsActivity.class);
                        startActivity(intent);

                    } else {

                        clsCommon.showDialogMsgFrag(StoreRatingAndReviewActivity.this
                                , "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(StoreRatingAndReviewActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(StoreRatingAndReviewActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                //pd.cancel();
                Toast.makeText(StoreRatingAndReviewActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View view) {

    }
}