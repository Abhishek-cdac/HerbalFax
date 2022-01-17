package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.DeleteDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CartItemAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.AddedCartModel;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model.CartList;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCartActivity extends AppCompatActivity {
    RecyclerView CartItemRecycler;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    CartItemAdapter cartItemAdapter;
    Onclick itemClick;
    TextView continue_button, subTotal;
    ArrayList<CartList> lst_cart_item;
    ImageView back;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        clsCommon = CommonClass.getInstance();

         back = findViewById(R.id.back);
        subTotal = findViewById(R.id.subTotal);
        continue_button = findViewById(R.id.continue_button);
        CartItemRecycler = findViewById(R.id.CartItemRecycler);

        callAddToCartAPI();
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckOutActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String productId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String productId) {
                if (i == 10) {

                } else if (i == 11) {
                    try {
                        DeleteDialog deleteDialog = new DeleteDialog(AddToCartActivity.this, productId);
                        deleteDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
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
                        subTotal.setText("$"+response.body().getData().getCart().getCartSubTotal());
                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        CartItemRecycler.setLayoutManager(RecyclerViewLayoutManager);
                        cartItemAdapter = new CartItemAdapter(lst_cart_item, getApplicationContext(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        CartItemRecycler.setAdapter(cartItemAdapter);

                    } else {
                        clsCommon.showDialogMsgFrag(AddToCartActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(AddToCartActivity.this, "HerbalFax", jObjError.getString("message"), "Ok")
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}