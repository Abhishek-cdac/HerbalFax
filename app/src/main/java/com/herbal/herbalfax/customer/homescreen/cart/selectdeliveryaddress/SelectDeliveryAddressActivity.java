package com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.herbal.herbalfax.customer.homescreen.cart.addaddress.AddNewAddressActivity;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.adapter.SelectDeliveryAddressAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.model.SelectDeliveryAddressModel;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.model.UserAddress;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDeliveryAddressActivity extends AppCompatActivity {
    RecyclerView addressRecyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    SelectDeliveryAddressAdapter selectDeliveryAddressAdapter;
    Onclick itemClick;
    ImageView back;
    ArrayList<UserAddress> lst_address_item;
    TextView addNewAddressTxt, continue_button;
    private CommonClass clsCommon;

    String Orders_Type, SubTotal, Tax, Shipping, Total , IdAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_address);
        addressRecyclerView = findViewById(R.id.addressRecyclerView);
        back = findViewById(R.id.back);
        clsCommon = CommonClass.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Orders_Type = extras.getString("Orders_Type");
            SubTotal = extras.getString("SubTotal");
            Tax = extras.getString("Tax");
            Shipping = extras.getString("Shipping");
            Total = extras.getString("Total");
        }
        addNewAddressTxt = findViewById(R.id.addNewAddressTxt);
        continue_button = findViewById(R.id.continue_button);


        callSelectDeliveryAddress();
        addNewAddressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewAddressActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });

        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String addressId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String addressId) {
                if (i == 7) {
                    IdAddress = addressId;
                }
            }
        };


    }

    private void showBottomSheet() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ShoppingDetailsBottomSheet sheet = new ShoppingDetailsBottomSheet(SelectDeliveryAddressActivity.this, SubTotal, Shipping, Tax, Total, IdAddress, Orders_Type);
        sheet.show(fragmentManager, "comment bottom sheet");
    }

    private void callSelectDeliveryAddress() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<SelectDeliveryAddressModel> call = service.userCartDeliveryAddress("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<SelectDeliveryAddressModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<SelectDeliveryAddressModel> call, Response<SelectDeliveryAddressModel> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_address_item = (ArrayList<UserAddress>) response.body().getData().getUserAddresses();
                        if (lst_address_item == null) {
                            lst_address_item = new ArrayList<>();
                        }


                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        addressRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
                        selectDeliveryAddressAdapter = new SelectDeliveryAddressAdapter(lst_address_item, getApplicationContext(), itemClick);
                        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        addressRecyclerView.setAdapter(selectDeliveryAddressAdapter);

                    } else {
                        clsCommon.showDialogMsgFrag(SelectDeliveryAddressActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(SelectDeliveryAddressActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<SelectDeliveryAddressModel> call, Throwable t) {
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