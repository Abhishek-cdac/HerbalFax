package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CartItemAdapter;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.CheckOutActivity;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class AddToCartActivity extends AppCompatActivity {
    RecyclerView CartItemRecycler;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    CartItemAdapter cartItemAdapter;
    Onclick itemClick;
    TextView continue_button;
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        back = findViewById(R.id.back);
        continue_button = findViewById(R.id.continue_button);
        CartItemRecycler = findViewById(R.id.CartItemRecycler);


        setUpRecyclerView();
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), CheckOutActivity.class);
                startActivity(intent);
            }
        });  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String productId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String productId) {
             /*   if (i == 10) {

                } else if (i == 11) {
                    try {
                        DeleteDialog deleteDialog = new DeleteDialog(AddToCartActivity.this, productId);
                        deleteDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        CartItemRecycler.setLayoutManager(RecyclerViewLayoutManager);
        cartItemAdapter = new CartItemAdapter(lst_cart_item, getApplicationContext(), itemClick);
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //storeReviewsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        CartItemRecycler.setAdapter(cartItemAdapter);

    }
}