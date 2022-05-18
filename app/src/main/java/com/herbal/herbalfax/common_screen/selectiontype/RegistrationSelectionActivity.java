package com.herbal.herbalfax.common_screen.selectiontype;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.customer.signup.SignUpAsCustomerActivity;
import com.herbal.herbalfax.databinding.ActivityRegistrationSelectionBinding;
import com.herbal.herbalfax.signupnew.NewCustomerSignUpScOne;
import com.herbal.herbalfax.signupnewvendor.NewVendorSignUpScTwo;

public class RegistrationSelectionActivity extends AppCompatActivity {

    private ActivityRegistrationSelectionBinding binding;
    private int selectedType = -1;

    private CommonClass clsCommon;

    ImageView customerImg, vendorImg;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectionTypeViewModel viewModel = new SelectionTypeViewModel();

        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(RegistrationSelectionActivity.this, R.layout.activity_registration_selection);
        binding.setLifecycleOwner(this);
        binding.setSelectionTypeViewModel(viewModel);
        customerImg = findViewById(R.id.customerImg);
        vendorImg = findViewById(R.id.vendorImg);
        viewModel.OnCustomerClick().observe(this, click -> {
            selectedType = 1;
//          binding.customerCard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            binding.vendorLayout.setBackground(ContextCompat.getDrawable(RegistrationSelectionActivity.this,R.drawable.bg_roundrect_white));
            binding.customerLayout.setBackground(ContextCompat.getDrawable(RegistrationSelectionActivity.this,R.drawable.bg_roundrect_green));
            binding.customerTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            binding.VendorTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            binding.accCust.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            binding.accVendor.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
         //   binding.customerImg.setImageResource(R.drawable.ic_customer_white);
          //  binding.vendorImg.setImageResource(R.drawable.ic_vendor_black);
            binding.continueButton.setEnabled(true);
            binding.checkboxCustomer.setVisibility(View.VISIBLE);
            binding.checkboxSeller.setVisibility(View.GONE);
            binding.continueButton.setEnabled(true);
        });
        viewModel.OnSellerClick().observe(this, click -> {
            selectedType = 2;
            binding.continueButton.setEnabled(true);

            binding.customerLayout.setBackground(ContextCompat.getDrawable(RegistrationSelectionActivity.this,R.drawable.bg_roundrect_white));
            binding.vendorLayout.setBackground(ContextCompat.getDrawable(RegistrationSelectionActivity.this,R.drawable.bg_roundrect_green));
            binding.customerTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            binding.VendorTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
//          binding.customerImg.setImageResource(R.drawable.ic_customer_black);
//          binding.vendorImg.setImageResource(R.drawable.ic_vendor_white);
            binding.accCust.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            binding.accVendor.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            binding.checkboxCustomer.setVisibility(View.GONE);
            binding.checkboxSeller.setVisibility(View.VISIBLE);
            binding.continueButton.setEnabled(true);
        });


        viewModel.onContinueUser().observe(this, click -> {
            if (selectedType == -1) {
                clsCommon.showDialogMsg(RegistrationSelectionActivity.this, "HerbalFax", "Please select type", "Ok");
            } else if (selectedType == 1) {
                Intent intent = new Intent(RegistrationSelectionActivity.this, NewCustomerSignUpScOne.class);  // SignUpAsCustomerActivity
                intent.putExtra("selectedType", selectedType);
                // intent.putExtra("selectedType", selectedType == 0 ? "customer" : selectedType == 1 ? "seller" );
                startActivity(intent);
                Log.e("selectedType", "" + selectedType);
            }
            else{
                Intent intent = new Intent(RegistrationSelectionActivity.this, NewVendorSignUpScTwo.class);  // SignUpAsCustomerActivity
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