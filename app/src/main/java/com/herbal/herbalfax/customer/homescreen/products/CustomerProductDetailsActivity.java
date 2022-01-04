package com.herbal.herbalfax.customer.homescreen.products;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.products.adapter.MyCustomerProductDetailTabAdapter;
import com.herbal.herbalfax.vendor.sellerproduct.productdetail.productdetailsmodel.ProductDetailsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProductDetailsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    String productId;
    CommonClass clsCommon;
    ImageView productImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_product_details);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                productId = extras.getString("productId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clsCommon = CommonClass.getInstance();
        productImg = findViewById(R.id.productImg);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyCustomerProductDetailTabAdapter adapter = new MyCustomerProductDetailTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(), productId);
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
        callProductDetails(productId);

    }

    private void callProductDetails(String productId) {

        try {
            SessionPref pref = SessionPref.getInstance(getApplicationContext());

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("ProductId", productId);


            Call<ProductDetailsResponse> call = service.userStoreProductDetails("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
            call.enqueue(new Callback<ProductDetailsResponse>() {
                @Override
                public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            if (!response.body().getData().getStoreProduct().getSPPPath().equals("")) {
                                Picasso.get()
                                        .load(String.valueOf(response.body().getData().getStoreProduct().getSPPPath()))
                                        .into(productImg);
                            } else {
//    Picasso.get()
//            .load(R.drawable.profileimg)
//            .into(productImg);
                            }


                        }
                    } else {
                        try {
                            assert response.errorBody() != null;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            clsCommon.showDialogMsgFrag(CustomerProductDetailsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ProductDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}