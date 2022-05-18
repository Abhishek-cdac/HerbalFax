package com.herbal.herbalfax.signupnewvendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityNewVendorSignUpScSevenBinding;

public class NewVendorSignUpScSeven extends AppCompatActivity {
CommonClass clsCommon;
ActivityNewVendorSignUpScSevenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        NewVendorSignUpScSevenViewModel newVendorSignUpScSevenViewModel = new NewVendorSignUpScSevenViewModel();
        binding = DataBindingUtil.setContentView(NewVendorSignUpScSeven.this, R.layout.activity_new_vendor_sign_up_sc_seven);
        binding.setLifecycleOwner(this);
        binding.setNewVendorSignUpScSevenViewModel(newVendorSignUpScSevenViewModel);


        newVendorSignUpScSevenViewModel.OnNextHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewVendorSignUpScSeven.this, NewVendorSignUpScEight.class);
            startActivity(mIntent);
        });
        newVendorSignUpScSevenViewModel.OnBackClick().observe(this, nextUser -> {
            onBackPressed();
        });

    }
}