package com.herbal.herbalfax.signupnewvendor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityNewVendorSignUpScSixBinding;

public class NewVendorSignUpScSix extends AppCompatActivity {
    CommonClass clsCommon;
    ActivityNewVendorSignUpScSixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        NewVendorSignUpScSixViewModel newVendorSignUpScSixViewModel = new NewVendorSignUpScSixViewModel();
        binding = DataBindingUtil.setContentView(NewVendorSignUpScSix.this, R.layout.activity_new_vendor_sign_up_sc_six);
        binding.setLifecycleOwner(this);
        binding.setNewVendorSignUpScSixViewModel(newVendorSignUpScSixViewModel);


        newVendorSignUpScSixViewModel.OnNextHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewVendorSignUpScSix.this, NewVendorSignUpScSeven.class);
            startActivity(mIntent);
        });
        newVendorSignUpScSixViewModel.OnBackClick().observe(this, nextUser -> {
            onBackPressed();
        });

    }
}