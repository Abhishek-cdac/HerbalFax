package com.herbal.herbalfax.customer.store.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.store.StoreRatingAndReviewActivity;
import com.herbal.herbalfax.customer.store.adapter.StoreReviewAdapter;
import com.herbal.herbalfax.customer.store.storeratingmodel.StoreRating;
import com.herbal.herbalfax.customer.store.storeratingmodel.StoreRatingsChart;
import com.herbal.herbalfax.customer.store.storeratingmodel.UserStoreRatingList;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreReviewFragment extends Fragment {
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerView storeReviewsRecycler;
    LinearLayoutManager HorizontalLayout;
    StoreReviewAdapter storeReViewsAdapter;
    Button writeReviewBtn;
    ImageView ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive;
    String storeId;
    TextView storeOwner, ratingCount, oneRatingTxt, twoRatingTxt, threeRatingTxt, fourRatingTxt, fiveRatingTxt;
    CommonClass clsCommon;
    LinearProgressIndicator progressFive, progressFour, progressThree, progressTwo, progressOne;

    public StoreReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_review_fragment, container, false);
        storeReviewsRecycler = root.findViewById(R.id.storeReviewsRecycler);
        clsCommon = CommonClass.getInstance();

        Bundle arguments = getArguments();
        if (arguments != null) {
            storeId = arguments.get("storeId").toString();
            Log.e("StoreReviewFragment", "" + storeId);
        }
        progressFive = root.findViewById(R.id.progressFive);
        progressFour = root.findViewById(R.id.progressFour);
        progressThree = root.findViewById(R.id.progressThree);
        progressTwo = root.findViewById(R.id.progressTwo);
        progressOne = root.findViewById(R.id.progressOne);
        ratingCount = root.findViewById(R.id.ratingCount);
        oneRatingTxt = root.findViewById(R.id.oneRatingTxt);
        twoRatingTxt = root.findViewById(R.id.twoRatingTxt);
        threeRatingTxt = root.findViewById(R.id.threeRatingTxt);
        fourRatingTxt = root.findViewById(R.id.fourRatingTxt);
        fiveRatingTxt = root.findViewById(R.id.fiveRatingTxt);
        storeOwner = root.findViewById(R.id.storeOwner);
        writeReviewBtn = root.findViewById(R.id.writeReviewBtn);
        ratingOne = root.findViewById(R.id.ratingOne);
        ratingTwo = root.findViewById(R.id.ratingTwo);
        ratingThree = root.findViewById(R.id.ratingThree);
        ratingFour = root.findViewById(R.id.ratingFour);
        ratingFive = root.findViewById(R.id.ratingFive);
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StoreRatingAndReviewActivity.class);
                intent.putExtra("storeId", storeId);
                startActivity(intent);
            }
        });

        callStoreRatingListAPI(storeId);

        return root;


    }

    private void callStoreRatingListAPI(String storeId) {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", storeId);
        hashMap.put("limit", "30");
        hashMap.put("offset", "0");

        Call<UserStoreRatingList> call = service.userStoreRatingList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<UserStoreRatingList>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<UserStoreRatingList> call, @NonNull Response<UserStoreRatingList> response) {
                // pd.cancel();
                if (response.code() == 200) {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {


                        ArrayList<StoreRatingsChart> lst_storeRatingChart = (ArrayList<StoreRatingsChart>) response.body().getData().getStoreRatingsChart();
                        if (lst_storeRatingChart == null) {
                            lst_storeRatingChart = new ArrayList<>();
                        }

                        oneRatingTxt.setText(lst_storeRatingChart.get(0).getRateCount() + "%");
                        twoRatingTxt.setText(lst_storeRatingChart.get(1).getRateCount() + "%");
                        threeRatingTxt.setText(lst_storeRatingChart.get(2).getRateCount() + "%");
                        fourRatingTxt.setText(lst_storeRatingChart.get(3).getRateCount() + "%");
                        fiveRatingTxt.setText(lst_storeRatingChart.get(4).getRateCount() + "%");

                      /*  try {
                            progressOne.setProgress(lst_storeRatingChart.get(0).getRateCount());
                            progressTwo.setProgress(lst_storeRatingChart.get(1).getRateCount());
                            progressThree.setProgress(lst_storeRatingChart.get(2).getRateCount());
                            progressFour.setProgress(lst_storeRatingChart.get(3).getRateCount());
                            progressFive.setProgress(lst_storeRatingChart.get(5).getRateCount());

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }*/


                        ArrayList<StoreRating> lst_storeRating = (ArrayList<StoreRating>) response.body().getData().getStoreRatings();
                        if (lst_storeRating == null) {
                            lst_storeRating = new ArrayList<>();
                        }

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        storeReviewsRecycler.setLayoutManager(RecyclerViewLayoutManager);
                        storeReViewsAdapter = new StoreReviewAdapter(lst_storeRating, getActivity());
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        //storeReviewsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        storeReviewsRecycler.setAdapter(storeReViewsAdapter);

                        //  storeOwner.setText(Math.toIntExact(response.body().getData().getStoreRatingsCount()));


                    } else {

                        clsCommon.showDialogMsgFrag(getActivity()
                                , "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(@NotNull Call<UserStoreRatingList> call, @NonNull Throwable t) {
                t.printStackTrace();
                //pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}