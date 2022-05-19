package com.herbal.herbalfax.signupnewvendor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityNewVendorSignUpScTwoBinding;

public class NewVendorSignUpScTwo extends AppCompatActivity {
    int userType;
    ActivityNewVendorSignUpScTwoBinding binding;
    private CommonClass clsCommon;
    TextView dummyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        NewVendorSignUpScTwoViewModel newVendorSignUpScTwoViewModel = new NewVendorSignUpScTwoViewModel();
        binding = DataBindingUtil.setContentView(NewVendorSignUpScTwo.this, R.layout.activity_new_vendor_sign_up_sc_two);
        binding.setLifecycleOwner(this);
        binding.setNewVendorSignUpScTwoViewModel(newVendorSignUpScTwoViewModel);


        dummyTxt = findViewById(R.id.dummyTxt);
        String one = "Is that you? You are currently logged in with \n";
        String next = "<font color='#96C93D'>puffd9@gmail.com. \n</font>";
        String two = "Try logging in using a different account.";

        dummyTxt.setText(Html.fromHtml(one+next + two));


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