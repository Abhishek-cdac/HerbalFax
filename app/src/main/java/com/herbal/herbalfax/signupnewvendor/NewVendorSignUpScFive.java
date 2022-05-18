package com.herbal.herbalfax.signupnewvendor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityNewVendorSignUpScFiveBinding;

public class NewVendorSignUpScFive extends AppCompatActivity {
    CommonClass clsCommon;
    ActivityNewVendorSignUpScFiveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        clsCommon = new CommonClass();
        NewVendorSignUpScFiveViewModel newVendorSignUpScFiveViewModel = new NewVendorSignUpScFiveViewModel();
        binding = DataBindingUtil.setContentView(NewVendorSignUpScFive.this, R.layout.activity_new_vendor_sign_up_sc_five);
        binding.setLifecycleOwner(this);
        binding.setNewVendorSignUpScFiveViewModel(newVendorSignUpScFiveViewModel);


        newVendorSignUpScFiveViewModel.OnNextHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewVendorSignUpScFive.this, NewVendorSignUpScSix.class);
            startActivity(mIntent);
        });
        newVendorSignUpScFiveViewModel.OnBackClick().observe(this, nextUser -> {
            onBackPressed();
        });

    }
}