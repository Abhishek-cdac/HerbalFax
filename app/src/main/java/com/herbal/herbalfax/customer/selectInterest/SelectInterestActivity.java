package com.herbal.herbalfax.customer.selectInterest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.herbal.herbalfax.R;

import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.homedashboard.LandingPageActivity;
import com.herbal.herbalfax.customer.selectInterest.getintrestmodel.AllInterest;
import com.herbal.herbalfax.customer.selectInterest.getintrestmodel.GetInterestResponse;
import com.herbal.herbalfax.customer.selectInterest.saveintrestmodel.SaveInterestResponse;
import com.herbal.herbalfax.databinding.ActivitySelectInterestBinding;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectInterestActivity extends AppCompatActivity {
    private ActivitySelectInterestBinding binding;
    private ArrayList<AllInterest> lst_interest;
    private InterestAdapter adapter;
    private Intent mIntent;
    private RecyclerView recyclerView;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        lst_interest= new ArrayList<>();
        InterestViewModel viewModel = new InterestViewModel();
        binding = DataBindingUtil.setContentView(SelectInterestActivity.this, R.layout.activity_select_interest);
        binding.setLifecycleOwner(this);

        binding.setInterestViewModel(viewModel);
        RelativeLayout rl_interest_bg = findViewById(R.id.rl_interest_bg);
        recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(SelectInterestActivity.this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mIntent = getIntent();
        getInterest();

        viewModel.OnNextClick().observe(SelectInterestActivity.this, aBoolean -> callSaveAPI());

//        binding.ivNext.setVisibility(View.VISIBLE);
//        adapter = new InterestAdapter(lst_interest);
//        recyclerView.setAdapter(adapter);
    }

    private void callSaveAPI() {

        if (null == lst_interest) {
            return;
        }
        StringBuilder selected = new StringBuilder();
        StringBuilder selectedText = new StringBuilder();
        int count = 0;
        for (int i = 0; i < lst_interest.size(); i++) {
            if (lst_interest.get(i).isSelected()) {
                count++;
                if (selected.length() == 0) {
                    selected = new StringBuilder(lst_interest.get(i).getIdinterests());
                    selectedText = new StringBuilder(lst_interest.get(i).getIntTitle());
                } else {
                    selected.append(",").append(lst_interest.get(i).getIdinterests());
                    selectedText.append(",").append(lst_interest.get(i).getIntTitle());
                }
            }
        }

        if (count == 0) {
            clsCommon.showDialogMsg(SelectInterestActivity.this, "HerbalFax", "Please select at least 1 interests", "Ok");
            return;
        }
//        else if (count < 2) {
//            clsCommon.showDialogMsg(SelectInterestActivity.this, "HerbalFax", "Please select at least 2 interests", "Ok");
//            return;
//        }
        else if (count > 10) {
            clsCommon.showDialogMsg(SelectInterestActivity.this, "HerbalFax", "You can select max 10 interests", "Ok");
            return;
        }

        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id" , pref.getStringVal(SessionPref.LoginUserID)); //pref.getStringVal(SessionPref.LoginUserID)
        hashMap.put("interests", selected.toString());
        Log.e("interests.......",""+selected.toString());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
//      SessionPref pref = SessionPref.getInstance(this);

        Call<SaveInterestResponse> call = service.saveInterests(hashMap);
        String finalSelected = selected.toString();
        String finalSelectedText = selectedText.toString();
        Log.e("selectedText.......",""+selectedText.toString());

        call.enqueue(new Callback<SaveInterestResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveInterestResponse> call, @NonNull Response<SaveInterestResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserinterested, finalSelectedText);
                        pref.saveStringKeyVal(SessionPref.LoginUserInterestsIDS, finalSelected);
//                        if (mIntent.getBooleanExtra("fromProfile", false)) {
//                            Intent mIntent = new Intent();
//                            setResult(409, mIntent);
//                            finish();
//                        } else {
//                            startActivity(new Intent(SelectInterestActivity.this, RestaurantActivity
//                                    .class));
//                        }
                        startActivity(new Intent(SelectInterestActivity.this, LandingPageActivity.class));
                    } else {
                        clsCommon.showDialogMsg(SelectInterestActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsg(SelectInterestActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(SelectInterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<SaveInterestResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(SelectInterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInterest() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);


        Call<GetInterestResponse> call = service.getInterests(hashMap);
        call.enqueue(new Callback<GetInterestResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetInterestResponse> call, @NonNull Response<GetInterestResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_interest = (ArrayList<AllInterest>) response.body().getData().getAllInterests();
                        if (lst_interest == null) {
                            lst_interest = new ArrayList<>();
                        }

                        if (mIntent.getBooleanExtra("fromProfile", false)) {

                            String[] interestList = pref.getStringVal(SessionPref.LoginUserInterestsIDS).split(",");

                            for (int i = 0; i < lst_interest.size(); i++) {
                                for (String s : interestList) {
                                    if (s.trim().equals(lst_interest.get(i).getIdinterests())) {
                                    lst_interest.get(i).setSelected(true);
                                    }
                                }
                            }
                        }
                        binding.ivNext.setVisibility(View.VISIBLE);
                        adapter = new InterestAdapter(lst_interest);
                        recyclerView.setAdapter(adapter);
                    } else {
                        clsCommon.showDialogMsg(SelectInterestActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsg(SelectInterestActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(SelectInterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<GetInterestResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(SelectInterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

}