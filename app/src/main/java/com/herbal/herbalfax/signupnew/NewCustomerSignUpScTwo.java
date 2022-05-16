package com.herbal.herbalfax.signupnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.terms.TermAndConditionActivity;

public class NewCustomerSignUpScTwo extends AppCompatActivity {
    TextView term;
    ImageView back;
    LinearLayout loginHere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_sign_up_sc_two);
/*
        clsCommon = new CommonClass();
        SignUpNewAsCustomerViewModel signUpAsCustomerViewModel = new SignUpNewAsCustomerViewModel();
        binding = DataBindingUtil.setContentView(NewCustomerSignUpScOne.this, R.layout.activity_new_customer_sign_up_sc_two);
        binding.setLifecycleOwner(this);
        binding.setSignUpNewAsCustomerViewModel(signUpAsCustomerViewModel);
*/

        loginHere = findViewById(R.id.loginHere);
        term = findViewById(R.id.term);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String first = "I agree to the legal ";
        String next = "<font color='#96C93D'>Terms and Conditions\n</font>";
        String two = "of this program, and that I am signing up to\n" +
                "receive emails from HerbalFax &amp; More ";

        term.setText(Html.fromHtml(first + next + two));

        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCustomerSignUpScTwo.this, TermAndConditionActivity.class);
                startActivity(intent);
            }
        });
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCustomerSignUpScTwo.this, LoginActivity.class);
                startActivity(intent);

            }
        });


    }
}