package com.herbal.herbalfax.customer.homescreen.products.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.products.model.AddToCartModel;
import com.herbal.herbalfax.vendor.sellerproduct.productdetail.productdetailsmodel.ProductDetailsResponse;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StorePhoto;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProductFragment extends Fragment {

    TextView productName, descTxt, PriceTxt, share, ratingTxt, totalReview, quantityTxt;
    ImageView remove, add, one, two, three, four, five;
    String productId;
    CommonClass clsCommon;
    int minteger = 0;
    LinearLayout addToCart;

    public CustomerProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_customer_product_detail, container, false);
        clsCommon = CommonClass.getInstance();

        Bundle arguments = getArguments();
        if (arguments != null) {
            productId = arguments.get("productId").toString();
            Log.e("StoreProductFragment", "" + productId);
        }
        addToCart = root.findViewById(R.id.addToCart);
        one = root.findViewById(R.id.one);
        two = root.findViewById(R.id.two);
        three = root.findViewById(R.id.three);
        four = root.findViewById(R.id.four);
        five = root.findViewById(R.id.five);
        PriceTxt = root.findViewById(R.id.PriceTxt);
        share = root.findViewById(R.id.share);
        add = root.findViewById(R.id.add);
        remove = root.findViewById(R.id.remove);
        descTxt = root.findViewById(R.id.descTxt);
        totalReview = root.findViewById(R.id.totalReview);
        ratingTxt = root.findViewById(R.id.ratingTxt);
        productName = root.findViewById(R.id.productName);
        quantityTxt = root.findViewById(R.id.quantityTxt);

        callProductDetails(productId);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minteger = minteger + 1;
                display(minteger);

            }
        });


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (minteger == 0) {
                    minteger = minteger;
                } else {
                    minteger = minteger - 1;
                }
                display(minteger);


            }
        });
        remove.setEnabled(true);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity = quantityTxt.getText().toString();
                callAddToCartApi(quantity, productId);
            }


        });
        return root;
    }

//    public void increaseInteger(View view) {
//
//    }
private void callAddToCartApi(String quantity, String productId) {
    try {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("ProductId", productId);
        hashMap.put("Qty", quantity);


        Call<AddToCartModel> call = service.addToCart("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("Added to cart", "  productId  " + productId +  "  quantity  "+ quantity);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
            public void onFailure(@NotNull Call<AddToCartModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }


}
    private void display(int number) {
        if (number >= 0) {
            remove.setClickable(true);
        } else {
            remove.setClickable(false);
        }

        quantityTxt.setText("" + number);
    }

//    public void decreaseInteger(View view) {
//
//    }

    private void callProductDetails(String productId) {

        try {
            SessionPref pref = SessionPref.getInstance(getActivity());

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

                            Log.e("customer ProductId", "" + productId);

                            ArrayList<StorePhoto> lst_product = (ArrayList<StorePhoto>) response.body().getData().getStoreProductPhotos();
                            if (lst_product == null) {
                                lst_product = new ArrayList<>();
                            }

                            productName.setText(response.body().getData().getStoreProduct().getSPName());
                            ratingTxt.setText(response.body().getData().getStoreProduct().getSPRating());
                            totalReview.setText("( " + response.body().getData().getStoreProduct().getSPReviews() + " Ratings )");
                            descTxt.setText(response.body().getData().getStoreProduct().getSPDesc());
                            PriceTxt.setText(response.body().getData().getStoreProduct().getSPRate() + "$");
                            if (response.body().getData().getStoreProduct().getSPRating().equals("0")) {
                                one.setImageResource(R.drawable.ic_icon_star_outline);
                                two.setImageResource(R.drawable.ic_icon_star_outline);
                                three.setImageResource(R.drawable.ic_icon_star_outline);
                                four.setImageResource(R.drawable.ic_icon_star_outline);
                                five.setImageResource(R.drawable.ic_icon_star_outline);

                            } else if (response.body().getData().getStoreProduct().getSPRating().equals("1")) {
                                one.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                two.setImageResource(R.drawable.ic_icon_star_outline);
                                three.setImageResource(R.drawable.ic_icon_star_outline);
                                four.setImageResource(R.drawable.ic_icon_star_outline);
                                five.setImageResource(R.drawable.ic_icon_star_outline);

                            } else if (response.body().getData().getStoreProduct().getSPRating().equals("2")) {
                                one.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                two.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                three.setImageResource(R.drawable.ic_icon_star_outline);
                                four.setImageResource(R.drawable.ic_icon_star_outline);
                                five.setImageResource(R.drawable.ic_icon_star_outline);

                            } else if (response.body().getData().getStoreProduct().getSPRating().equals("3")) {
                                one.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                two.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                three.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                four.setImageResource(R.drawable.ic_icon_star_outline);
                                five.setImageResource(R.drawable.ic_icon_star_outline);

                            } else if (response.body().getData().getStoreProduct().getSPRating().equals("4")) {
                                one.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                two.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                three.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                four.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                five.setImageResource(R.drawable.ic_icon_star_outline);

                            } else if (response.body().getData().getStoreProduct().getSPRating().equals("5")) {
                                one.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                two.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                three.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                four.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);
                                five.setImageResource(R.drawable.ic_icon_metro_star_full_lightgreen);

                            }


                            //    quantityTxt.setText(response.body().getData().getStoreProduct().getSPQty());
                            //   costTxt.setText(response.body().getData().getStoreProduct().getSPShippingCost());
                            // distanceTxt.setText(response.body().getData().getStoreProduct().getSPPerKM());
                            //   setSliderImages();

                            /*Slider view*/

                            //      SliderView sliderView = findViewById(R.id.slider);

//                            sliderDataArrayList.add(new SliderData(url1));
//                            sliderDataArrayList.add(new SliderData(url2));
//                            sliderDataArrayList.add(new SliderData(url3));

//                            SliderAdapter adapter = new SliderAdapter(ProductDetailsActivity.this, lst_product);
//                            sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//                            sliderView.setSliderAdapter(adapter);
//                            sliderView.setScrollTimeInSec(3);
//
//                            sliderView.setAutoCycle(true);
//
//                            sliderView.startAutoCycle();
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
                public void onFailure(@NotNull Call<ProductDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
