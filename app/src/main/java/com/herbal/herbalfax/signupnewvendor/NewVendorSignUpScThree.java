package com.herbal.herbalfax.signupnewvendor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityNewVendorSignUpScThreeBinding;

public class NewVendorSignUpScThree extends AppCompatActivity {
    private CommonClass clsCommon;
    ActivityNewVendorSignUpScThreeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        NewVendorSignUpScThreeViewModel   newVendorSignUpScThreeViewModel= new NewVendorSignUpScThreeViewModel();
        binding = DataBindingUtil.setContentView(NewVendorSignUpScThree.this, R.layout.activity_new_vendor_sign_up_sc_three);
        binding.setLifecycleOwner(this);
        binding.setNewVendorSignUpScThreeViewModel(newVendorSignUpScThreeViewModel);


        newVendorSignUpScThreeViewModel.OnNextHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewVendorSignUpScThree.this, NewVendorSignUpScFour.class);
            startActivity(mIntent);
        });
        newVendorSignUpScThreeViewModel.OnBackClick().observe(this, nextUser -> {
            onBackPressed();
        });

    }
}