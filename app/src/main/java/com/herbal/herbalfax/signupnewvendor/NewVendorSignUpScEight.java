package com.herbal.herbalfax.signupnewvendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.Html;
import android.widget.RadioButton;
import android.widget.TextView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.bottomsheet.ShoppingDetailsBottomSheet;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.SelectDeliveryAddressActivity;

public class NewVendorSignUpScEight extends AppCompatActivity {
RadioButton receiveEmailRB;
TextView dummyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vendor_sign_up_sc_eight);
        receiveEmailRB= findViewById(R.id.receiveEmailRB);
        dummyTxt= findViewById(R.id.dummyTxt);

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

    }

    private void showBottomSheet() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        ShoppingDetailsBottomSheet sheet = new ShoppingDetailsBottomSheet(SelectDeliveryAddressActivity.this, SubTotal, Shipping, Tax, Total, IdAddress, Orders_Type);
//        sheet.show(fragmentManager, "comment bottom sheet");
    }

}