package com.herbal.herbalfax.customer.homescreen.cart.addaddress;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.exoplayer2.util.Log;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.commonmodel.CommonResponse;
import com.herbal.herbalfax.databinding.ActivityAddNewAddressBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewAddressActivity extends AppCompatActivity {

    private ActivityAddNewAddressBinding binding;
    RadioGroup radioGroup;
    String idAddressTypes , isPickUpPoint;
    CommonClass clsCommon;
    AddNewAddressViewModel addNewAddressViewModel;
    CheckBox saveForLaterCheckBox, setAsDefaultCheckBox;
    String setAsDefault;

    Switch switchPickupPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.activity_add_new_address);
        clsCommon = new CommonClass();
        addNewAddressViewModel = new AddNewAddressViewModel();
        binding = DataBindingUtil.setContentView(AddNewAddressActivity.this, R.layout.activity_add_new_address);
        binding.setLifecycleOwner(this);
        binding.setAddNewAddressViewModel(addNewAddressViewModel);
        switchPickupPoint = findViewById(R.id.switchPickupPoint);
        setAsDefaultCheckBox = findViewById(R.id.setAsDefaultCB);

        radioGroup = findViewById(R.id.radioGroup);
        saveForLaterCheckBox = findViewById(R.id.saveForLaterCB);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().equals("WORK")) {
                        idAddressTypes = "1";
                        Log.e("WORK : ", idAddressTypes);
                    } else if (rb.getText().equals("HOME")) {
                        idAddressTypes = "2";
                        Log.e("HOME : ", idAddressTypes);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please select address type", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        switchPickupPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isPickUpPoint= "1";
                    // The toggle is enabled
                } else {
                    isPickUpPoint= "0";

                    // The toggle is disabled
                }
            }
        });
        addNewAddressViewModel.getAddAddressUser().observe(this, addAddressUser -> {

            int selectedId = radioGroup.getCheckedRadioButtonId();
            //  genderradioButton = findViewById(selectedId);
            if (TextUtils.isEmpty(Objects.requireNonNull(addAddressUser).getFullName())) {
                clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "Enter Full Name", "Ok");
            } else if (selectedId == -1) {
                clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "Select Address Type", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addAddressUser).getCity())) {
                clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "Enter City", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addAddressUser).getPinCode())) {
                clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "Enter Pincode", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addAddressUser).getAddress())) {
                clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "Enter Address", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addAddressUser).getCountry())) {
                clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "Enter Country", "Ok");
            } /*else if (!saveForLaterCheckBox.isChecked()) {
                clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "Please agree Term & conditions", "Ok");
            } */ else {
                callAddAddressAPI(addAddressUser, idAddressTypes);
            }
        });
    }

    private void callAddAddressAPI(AddAddressUser addAddressUser, String idAddressTypes) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        if (setAsDefaultCheckBox.isChecked()) {
            setAsDefault = "1";
        } else {
            setAsDefault = "0";
        }

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddNewAddressActivity.this);
        pd.show();

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("UAdd_FullName", addAddressUser.getFullName());
        hashMap.put("UAdd_Address", addAddressUser.getAddress());
//        hashMap.put("UAdd_Lat", latitute);
//        hashMap.put("UAdd_Long", longitude);
        hashMap.put("UAdd_City", addAddressUser.getCity());
        hashMap.put("UAdd_Type", idAddressTypes);//1- work , 2 - home
        hashMap.put("UAdd_Default", setAsDefault);
        hashMap.put("UAdd_Pincode", addAddressUser.getPinCode());
        hashMap.put("UAdd_Country", addAddressUser.getCountry());
        hashMap.put("UAdd_IsPickupPoint", isPickUpPoint);  //1 -> Yes , 0 -> No

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<CommonResponse> call = service.userCartAddDeliveryAddress("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "" + response.body().getMessage(), "Ok");
                        } else {
                            android.util.Log.e("Error", "" + response.body().getErrors().toString());
                            android.util.Log.e("Error", "" + response.body().getErrors());
                            clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        android.util.Log.e("ErrorElse", "" + jObjError.toString());

                        android.util.Log.e("ErrorElse", "" + jObjError.getString("errors"));
                        android.util.Log.e("ErrorElse", "" + jObjError.getString("message"));
                        clsCommon.showDialogMsg(AddNewAddressActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddNewAddressActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddNewAddressActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}