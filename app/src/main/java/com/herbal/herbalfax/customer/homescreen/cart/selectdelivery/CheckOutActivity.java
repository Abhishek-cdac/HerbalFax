package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CartItemAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CheckoutListAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.AddedCartModel;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.CartList;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.SelectDeliveryAddressActivity;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {
    RecyclerView checkoutRecycler;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    CheckoutListAdapter checkoutListAdapter;
    Onclick itemClick;
    TextView  subTotal, tax, Total;
    ArrayList<CartList> lst_cart_item = new ArrayList<>();
    RadioButton rbDelivery, rbPickup;
    RadioGroup radioGroup;
    ImageView back;
    Button continueButton;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        clsCommon = CommonClass.getInstance();

        rbDelivery = findViewById(R.id.rbDelivery);
        Total = findViewById(R.id.Total);
        tax = findViewById(R.id.tax);
        subTotal = findViewById(R.id.subTotal);
        rbPickup = findViewById(R.id.rbPickup);
        checkoutRecycler = findViewById(R.id.checkoutRecycler);
        continueButton = findViewById(R.id.continue_button);
        back = findViewById(R.id.back);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    if (rb.getText().equals("PICK UP")) {
                        //  PostIsMedia = "1";
                        continueButton.setText("PICK UP");
                        //addquestionll.setVisibility(View.GONE);
                    } else if (rb.getText().equals("DELIVER")) {
                        //  PostIsMedia = "0";
                        continueButton.setText("CHECK OUT");
                        // addquestionll.setVisibility(View.VISIBLE);
                    }
//                    else {
//                        Toast.makeText(getApplicationContext(), "Please select post type", Toast.LENGTH_SHORT).show();
//
//                    }
                }


            }
        });
        callAddToCartAPI();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectDeliveryAddressActivity.class);
                startActivity(intent);
            }
        });
        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String blogId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String blogId) {

            }
        };
       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void callAddToCartAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<AddedCartModel> call = service.userCartList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<AddedCartModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<AddedCartModel> call, Response<AddedCartModel> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_cart_item = (ArrayList<CartList>) response.body().getData().getCartList();
                        if (lst_cart_item == null) {
                            lst_cart_item = new ArrayList<>();
                        }

                        try {
                            subTotal.setText("$"+Math.toIntExact(response.body().getData().getCart().getCartSubTotal()));

                            Total.setText("$"+Math.toIntExact(response.body().getData().getCart().getCartTotal()));
                            tax.setText("$"+Math.toIntExact(response.body().getData().getCart().getCartTax()));
                            RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                            checkoutRecycler.setLayoutManager(RecyclerViewLayoutManager);
                            checkoutListAdapter = new CheckoutListAdapter(lst_cart_item, getApplicationContext(), itemClick);
                            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            checkoutRecycler.setAdapter(checkoutListAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        clsCommon.showDialogMsgFrag(CheckOutActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(CheckOutActivity.this, "HerbalFax", jObjError.getString("message"), "Ok")
                        ;
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AddedCartModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}