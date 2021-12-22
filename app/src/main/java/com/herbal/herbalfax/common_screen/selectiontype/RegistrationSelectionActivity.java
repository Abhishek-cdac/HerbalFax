package com.herbal.herbalfax.common_screen.selectiontype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.customer.signup.SignUpAsCustomerActivity;
import com.herbal.herbalfax.databinding.ActivityRegistrationSelectionBinding;

public class RegistrationSelectionActivity extends AppCompatActivity {

    private ActivityRegistrationSelectionBinding binding;
    private int selectedType = -1;
    private CommonClass clsCommon;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SelectionTypeViewModel viewModel = new SelectionTypeViewModel();

        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(RegistrationSelectionActivity.this, R.layout.activity_registration_selection);
        binding.setLifecycleOwner(this);
        binding.setSelectionTypeViewModel(viewModel);

        viewModel.OnCustomerClick().observe(this, click -> {
            selectedType = 1;
            binding.customerCard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            binding.customerTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            binding.VendorTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            binding.sellerCard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            binding.checkboxCustomer.setVisibility(View.VISIBLE);
            binding.checkboxSeller.setVisibility(View.GONE);
            binding.continueButton.setEnabled(true);
        });
        viewModel.OnSellerClick().observe(this, click -> {
            selectedType = 2;
            binding.customerTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            binding.VendorTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            binding.customerCard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            binding.sellerCard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            binding.checkboxCustomer.setVisibility(View.GONE);
            binding.checkboxSeller.setVisibility(View.VISIBLE);
            binding.continueButton.setEnabled(true);
        });


        viewModel.onContinueUser().observe(this, click -> {
            if (selectedType == -1) {
                clsCommon.showDialogMsg(RegistrationSelectionActivity.this, "HerbalFax", "Please select type", "Ok");
            } else {
                String userType;
                Intent intent = new Intent(RegistrationSelectionActivity.this, SignUpAsCustomerActivity.class);
                intent.putExtra("selectedType", selectedType);
                // intent.putExtra("selectedType", selectedType == 0 ? "customer" : selectedType == 1 ? "seller" );
                startActivity(intent);
                Log.e("selectedType", "" + selectedType);
            }
        });
        viewModel.OnLoginHereClick().observe(this, click -> {
            Intent intent1 = new Intent(RegistrationSelectionActivity.this, LoginActivity.class);
            startActivity(intent1);
        });

    }
}