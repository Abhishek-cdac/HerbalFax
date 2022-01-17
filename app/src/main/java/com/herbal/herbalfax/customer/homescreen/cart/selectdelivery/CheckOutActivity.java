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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.bottomsheet.ShoppingDetailsBottomSheet;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CheckoutListAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.AddedCartModel;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.CartList;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.SelectDeliveryAddressActivity;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {
    RecyclerView checkoutRecycler;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    CheckoutListAdapter checkoutListAdapter;
    Onclick itemClick;
    TextView subTotal, tax, Total;
    ArrayList<CartList> lst_cart_item = new ArrayList<>();
    RadioButton rbDelivery, rbPickup;
    RadioGroup radioGroup;
    ImageView back;
    Button continueButton;
    private CommonClass clsCommon;
    String subTotalStr, shipping, taxStr, total;
    private String Orders_Type ="0";

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
                        Orders_Type = "1";
                        continueButton.setText("PICK UP");
                        //addquestionll.setVisibility(View.GONE);
                    } else if (rb.getText().equals("DELIVER")) {
                        Orders_Type = "2";
                        continueButton.setText("CHECK OUT");
                        // addquestionll.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please select address type", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });
        callAddToCartAPI();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Orders_Type.equals("2")) {
                    Intent intent = new Intent(getApplicationContext(), SelectDeliveryAddressActivity.class);
                    intent.putExtra("Orders_Type", Orders_Type);
                    intent.putExtra("SubTotal", subTotalStr);
                    intent.putExtra("Tax", taxStr);
                    intent.putExtra("Shipping", "0");
                    intent.putExtra("Total", total);
                    startActivity(intent);
                    finish();
                } else if(Orders_Type.equals("1")) {
                    showBottomSheet(subTotalStr, shipping, taxStr, total , Orders_Type);
                }
                else{
                    clsCommon.showDialogMsgFrag(CheckOutActivity.this, "HerbalFax","Please select address type", "Ok");
                }

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

    private void showBottomSheet(String subTotalStr, String shipping, String taxStr, String total, String orders_Type) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ShoppingDetailsBottomSheet sheet = new ShoppingDetailsBottomSheet(CheckOutActivity.this, subTotalStr, shipping, taxStr, total, orders_Type);
        sheet.show(fragmentManager, "comment bottom sheet");
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
            @SuppressLint("SetTextI18n")
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

                        subTotalStr = String.valueOf(response.body().getData().getCart().getCartSubTotal());
                        total = String.valueOf(response.body().getData().getCart().getCartTotal());
                        taxStr = String.valueOf(Objects.requireNonNull(response).body().getData().getCart().getCartTax());
                        try {
                            subTotal.setText("$" + response.body().getData().getCart().getCartSubTotal());

                            Total.setText("$" + response.body().getData().getCart().getCartTotal());
                            tax.setText("$" + response.body().getData().getCart().getCartTax());
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