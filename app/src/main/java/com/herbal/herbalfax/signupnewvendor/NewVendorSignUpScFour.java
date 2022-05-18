package com.herbal.herbalfax.signupnewvendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityNewVendorSignUpScFourBinding;

public class NewVendorSignUpScFour extends AppCompatActivity {
CommonClass clsCommon;
ActivityNewVendorSignUpScFourBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        NewVendorSignUpScFourViewModel  newVendorSignUpScFourViewModel= new NewVendorSignUpScFourViewModel();
        binding = DataBindingUtil.setContentView(NewVendorSignUpScFour.this, R.layout.activity_new_vendor_sign_up_sc_four);
        binding.setLifecycleOwner(this);
        binding.setNewVendorSignUpScFourViewModel(newVendorSignUpScFourViewModel);


        newVendorSignUpScFourViewModel.OnNextHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewVendorSignUpScFour.this, NewVendorSignUpScFive.class);
            startActivity(mIntent);
        });
        newVendorSignUpScFourViewModel.OnBackClick().observe(this, nextUser -> {
            onBackPressed();
        });

    }
}