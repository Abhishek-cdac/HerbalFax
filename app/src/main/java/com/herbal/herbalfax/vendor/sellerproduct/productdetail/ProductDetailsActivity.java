package com.herbal.herbalfax.vendor.sellerproduct.productdetail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.DeleteDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.vendor.sellerproduct.productdetail.productdetailsmodel.ProductDetailsResponse;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StorePhoto;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    // Urls of our images.
    String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
    String url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp";
    String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";
    String productId;
    private CommonClass clsCommon;
    TextView costTxt, distanceTxt, productName, ratingTxt, totalReview, descTxt, priceTxt, quantityTxt;
    CardView deleteCard, editCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle extras = getIntent().getExtras();
        clsCommon = CommonClass.getInstance();
        if (extras != null) {
            productId = getIntent().getExtras().getString("ProductId");
        }
        deleteCard = findViewById(R.id.deleteCard);
        productName = findViewById(R.id.productName);
        totalReview = findViewById(R.id.totalReview);
        ratingTxt = findViewById(R.id.ratingTxt);
        priceTxt = findViewById(R.id.priceTxt);
        costTxt = findViewById(R.id.costTxt);
        descTxt = findViewById(R.id.descTxt);
        quantityTxt = findViewById(R.id.quantityTxt);
        distanceTxt = findViewById(R.id.distanceTxt);

        callProductDetails(productId);
        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DeleteDialog deleteDialog = new DeleteDialog(ProductDetailsActivity.this, productId);
                    deleteDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

                            Log.e("ProductId data ", "" + productId);

                            ArrayList<StorePhoto> lst_product = (ArrayList<StorePhoto>) response.body().getData().getStoreProductPhotos();
                            if (lst_product == null) {
                                lst_product = new ArrayList<>();
                            }
                            productName.setText(response.body().getData().getStoreProduct().getSPName());
                            ratingTxt.setText(response.body().getData().getStoreProduct().getSPRating());
                            totalReview.setText(response.body().getData().getStoreProduct().getSPReviews());
                            descTxt.setText(response.body().getData().getStoreProduct().getSPDesc());
                            priceTxt.setText(response.body().getData().getStoreProduct().getSPRate());
                            quantityTxt.setText(response.body().getData().getStoreProduct().getSPQty());
                            costTxt.setText(response.body().getData().getStoreProduct().getSPShippingCost());
                            distanceTxt.setText(response.body().getData().getStoreProduct().getSPPerKM());
                            //   setSliderImages();

                            /*Slider view*/

                            SliderView sliderView = findViewById(R.id.slider);

//                            sliderDataArrayList.add(new SliderData(url1));
//                            sliderDataArrayList.add(new SliderData(url2));
//                            sliderDataArrayList.add(new SliderData(url3));

                            SliderAdapter adapter = new SliderAdapter(ProductDetailsActivity.this, lst_product);
                            sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                            sliderView.setSliderAdapter(adapter);
                            sliderView.setScrollTimeInSec(3);

                            sliderView.setAutoCycle(true);

                            sliderView.startAutoCycle();
                        }
                    } else {
                        try {
                            assert response.errorBody() != null;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            clsCommon.showDialogMsgFrag(ProductDetailsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
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

    private void setSliderImages() {
   /*     ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        SliderView sliderView = findViewById(R.id.slider);

        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();*/
    }
}