package com.herbal.herbalfax.signupnewvendor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityNewVendorSignUpScTwoBinding;
import com.herbal.herbalfax.signupnew.NewCustomerSignUpScTwo;

public class NewVendorSignUpScTwo extends AppCompatActivity {
    int userType;
    ActivityNewVendorSignUpScTwoBinding binding;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        NewVendorSignUpScTwoViewModel newVendorSignUpScTwoViewModel = new NewVendorSignUpScTwoViewModel();
        binding = DataBindingUtil.setContentView(NewVendorSignUpScTwo.this, R.layout.activity_new_vendor_sign_up_sc_two);
        binding.setLifecycleOwner(this);
        binding.setNewVendorSignUpScTwoViewModel(newVendorSignUpScTwoViewModel);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userType = getIntent().getExtras().getInt("selectedType");
            Log.e("data ", "" + userType);
        }


        newVendorSignUpScTwoViewModel.OnNextHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewVendorSignUpScTwo.this, NewVendorSignUpScThree.class);
            startActivity(mIntent);
        });
        newVendorSignUpScTwoViewModel.OnBackClick().observe(this, nextUser -> {
            onBackPressed();
        });
        newVendorSignUpScTwoViewModel.OnLoginHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewVendorSignUpScTwo.this, NewVendorSignUpScThree.class);
            startActivity(mIntent);
        });
    }
}