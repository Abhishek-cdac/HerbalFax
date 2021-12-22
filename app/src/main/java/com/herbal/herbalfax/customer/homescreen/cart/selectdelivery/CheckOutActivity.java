package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CheckoutListAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.SelectDeliveryAddressActivity;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {
    RecyclerView checkoutRecycler;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    CheckoutListAdapter checkoutListAdapter;
    Onclick itemClick;
    TextView continue_button;
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
RadioButton rbDelivery, rbPickup;
RadioGroup radioGroup;
ImageView back;
    Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        rbDelivery = findViewById(R.id.rbDelivery);
        rbPickup = findViewById(R.id.rbPickup);
        checkoutRecycler = findViewById(R.id.checkoutRecycler);
        continueButton = findViewById(R.id.continue_button);
        back = findViewById(R.id.back);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb =  group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    if (rb.getText().equals("PICK UP")) {
                      //  PostIsMedia = "1";
                        continueButton.setText("PICK UP");
                        //addquestionll.setVisibility(View.GONE);
                    } else if (rb.getText().equals("DELIVER")) {
                      //  PostIsMedia = "0";
                        continueButton.setText("CHECK OUT");
                       // addquestionll.setVisibility(View.VISIBLE);
                    }
//                    else {
//                        Toast.makeText(getApplicationContext(), "Please select post type", Toast.LENGTH_SHORT).show();
//
//                    }
                }


            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectDeliveryAddressActivity.class);
                startActivity(intent);
            }
        });
        itemClick= new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String blogId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String blogId) {

            }
        };
        setUpRecyclerView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        checkoutRecycler.setLayoutManager(RecyclerViewLayoutManager);
        checkoutListAdapter = new CheckoutListAdapter(lst_cart_item, getApplicationContext(), itemClick);
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        checkoutRecycler.setAdapter(checkoutListAdapter);

    }
}