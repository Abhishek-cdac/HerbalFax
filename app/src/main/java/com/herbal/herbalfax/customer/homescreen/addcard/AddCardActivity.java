package com.herbal.herbalfax.customer.homescreen.addcard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.addcard.model.AddCardModel;
import com.herbal.herbalfax.customer.homescreen.addcard.ordersubmitmodel.OrderSubmitModel;
import com.herbal.herbalfax.customer.homescreen.payment.PaymentCheckoutActivity;
import com.herbal.herbalfax.databinding.ActivityAddCardBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends AppCompatActivity {
    AddCardViewModel addCardViewModel;

    private ActivityAddCardBinding binding;

    TextView nameCard;
    Switch switchSave;
    Button addCardBtn;
    CommonClass clsCommon;
    String credit, CardName, cardType, orders_Type, idAddress, AddressID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addCardViewModel = new AddCardViewModel();
        binding = DataBindingUtil.setContentView(AddCardActivity.this, R.layout.activity_add_card);
        binding.setLifecycleOwner(this);
        binding.setAddCardViewModel(addCardViewModel);
        clsCommon = new CommonClass();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orders_Type = extras.getString("orders_Type");
            credit = extras.getString("CardType");
            CardName = extras.getString("CardName");
            idAddress = extras.getString("idAddress");
        }
        if (idAddress != null) {
            AddressID = idAddress;
        } else {
            AddressID = "";
        }

        nameCard = findViewById(R.id.nameCard);
        nameCard.setText(CardName);
        switchSave = findViewById(R.id.switchSave);

        if (credit.equals("1")) {
            cardType = "1";
        } else {
            cardType = "2";
        }
        Log.e("cardType", cardType);


        addCardViewModel.getPayNow().observe(this, addCardUser -> {


            if (TextUtils.isEmpty(Objects.requireNonNull(addCardUser).getName())) {
                clsCommon.showDialogMsg(AddCardActivity.this, "HerbalFax", "Enter Full Name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addCardUser).getCardNumber())) {
                clsCommon.showDialogMsg(AddCardActivity.this, "HerbalFax", "Enter Card Number", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addCardUser).getCardMM())) {
                clsCommon.showDialogMsg(AddCardActivity.this, "HerbalFax", "Enter Expiry month", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addCardUser).getCardYY())) {
                clsCommon.showDialogMsg(AddCardActivity.this, "HerbalFax", "Enter Expiry year", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addCardUser).getCardCVV())) {
                clsCommon.showDialogMsg(AddCardActivity.this, "HerbalFax", "Enter CVV", "Ok");
            } else {
                callAddCardAPI(addCardUser, cardType);
            }


        });
    }

    private void callAddCardAPI(AddCardUser addCardUser, String cardType) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddCardActivity.this);
        pd.show();

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("UCards_Type", cardType);
        hashMap.put("UCards_HolderName", addCardUser.getName());
        hashMap.put("UCards_Number", addCardUser.getCardNumber());
        hashMap.put("UCards_ExpMonth", addCardUser.getCardMM());
        hashMap.put("UCards_ExpYear", addCardUser.getCardYY());
        hashMap.put("UCards_CVV", addCardUser.getCardCVV());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<AddCardModel> call = service.userCartAddCard("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<AddCardModel>() {
            @Override
            public void onResponse(@NonNull Call<AddCardModel> call, @NonNull Response<AddCardModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        String cardId = response.body().getData().getUserCards().get(0).getIduserCards();

                        CallSubmitOrderApI(AddressID, cardId, orders_Type);

                    } else {
                        clsCommon.showDialogMsg(AddCardActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddCardModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddCardActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void CallSubmitOrderApI(String idAddress, String cardId, String orders_type) {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddCardActivity.this);
        pd.show();

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Orders_IdCard", cardId);
        hashMap.put("Orders_IdAddress", idAddress);
        hashMap.put("Orders_Type", orders_type);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<OrderSubmitModel> call = service.userCartOrderSubmit("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<OrderSubmitModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderSubmitModel> call, @NonNull Response<OrderSubmitModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        Intent intent = new Intent(getApplicationContext(), PaymentCheckoutActivity.class);
                        intent.putExtra("IdOrders", response.body().getData().getOrder().getIdorders());
                        startActivity(intent);
                        finish();
                    } else {
                        clsCommon.showDialogMsg(AddCardActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<OrderSubmitModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddCardActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

}