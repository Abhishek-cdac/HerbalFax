package com.herbal.herbalfax.customer.homescreen.deals;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.deals.adaptor.RelatedDealsAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.sellerproduct.productdetail.productdetailsmodel.ProductDetailsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealsDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView storeDealsRecycler;
    LinearLayoutManager HorizontalLayout;
    RelatedDealsAdapter relatedDealsAdapter;
    String productId;
    CommonClass clsCommon;
    Button buyBtn;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    ImageView productImg,back;
    TextView headerTxt,productNameText, location, validity_txt, priceTxt, para_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_details);
        Bundle extras = getIntent().getExtras();
        clsCommon = CommonClass.getInstance();

        if (extras != null) {
            productId = getIntent().getExtras().getString("productId");
            Log.e("productId", "" + productId + "  " + productId);
        }

        storeDealsRecycler = (RecyclerView) findViewById(R.id.storeDealsRecycler);
        buyBtn = findViewById(R.id.buyBtn);
        productNameText = findViewById(R.id.productNameText);
        productImg = findViewById(R.id.productImg);
        validity_txt = findViewById(R.id.validity_txt);
        priceTxt = findViewById(R.id.priceTxt);
        para_txt = findViewById(R.id.para_txt);
        headerTxt = findViewById(R.id.headerTxt);
        location = findViewById(R.id.location);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        callProductDetails(productId);
        setUpDealsRecyclerView();
    }

    private void setUpDealsRecyclerView() {
        ArrayList<Interest> dealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        storeDealsRecycler.setLayoutManager(RecyclerViewLayoutManager);
        relatedDealsAdapter = new RelatedDealsAdapter(dealsCategory, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        storeDealsRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        storeDealsRecycler.setAdapter(relatedDealsAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void callProductDetails(String productId) {

        try {
            SessionPref pref = SessionPref.getInstance(getApplicationContext());

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("ProductId", productId);


            Call<ProductDetailsResponse> call = service.userStoreProductDetails("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
            call.enqueue(new Callback<ProductDetailsResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ProductDetailsResponse> call, @NonNull Response<ProductDetailsResponse> response) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            headerTxt.setText(response.body().getData().getStoreProduct().getSPName());
                            productNameText.setText(response.body().getData().getStoreProduct().getSPName());
                            if(response.body().getData().getStoreProduct().getsP_Expiry() != null){
                                validity_txt.setText("Validity Till: "+response.body().getData().getStoreProduct().getsP_Expiry());
                            }
                            if (response.body().getData().getStoreProduct().getsP_Location()==null) {
                                location.setText("No Address Found");
                            }else {
                                location.setText(response.body().getData().getStoreProduct().getsP_Location());
                            }
                             priceTxt.setText("$"+response.body().getData().getStoreProduct().getSPRate());
                            para_txt.setText(response.body().getData().getStoreProduct().getSPDesc());
                            if (!response.body().getData().getStoreProduct().getSPPPath().equals("")) {
                                Picasso.get()
                                        .load(String.valueOf(response.body().getData().getStoreProduct().getSPPPath()))
                                        .into(productImg);
                            }


                        }
                    } else {
                        try {
                            assert response.errorBody() != null;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            clsCommon.showDialogMsg(DealsDetailsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ProductDetailsResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}