package com.herbal.herbalfax.signupnewvendor;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.herbal.herbalfax.R;

public class NewVendorSignUpScEight extends AppCompatActivity {
    RadioButton receiveEmailRB;
    TextView dummyTxt;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vendor_sign_up_sc_eight);
        receiveEmailRB = findViewById(R.id.receiveEmailRB);
        dummyTxt = findViewById(R.id.dummyTxt);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String first = "By continuing, you agree to Herbalfax ";
        String second = "<font color='#96C93D'>Business Terms \n</font>";
        String third = "and acknowledge our ";
        String fourth = "<font color='#96C93D'>Privacy Policy. \n</font>";
        dummyTxt.setText(Html.fromHtml(first + second + third + fourth));


        String next = "<font color='#96C93D'>Received an email \n</font>";
        String two = "We will send you an email on\n" +
                "(puffd9@gmail.com) with 4 digit\n" +
                "code.";

        receiveEmailRB.setText(Html.fromHtml(next + two));
        receiveEmailRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });

    }

    private void showBottomSheet() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ClaimBottomSheet sheet = new ClaimBottomSheet(NewVendorSignUpScEight.this);
        sheet.show(fragmentManager, "comment bottom sheet");
    }

}