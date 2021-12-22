package com.herbal.herbalfax.customer.homescreen.products;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.customer.homescreen.products.adapter.MyCustomerProductDetailTabAdapter;
import com.herbal.herbalfax.customer.store.MyStoreDetailTabAdapter;

public class CustomerProductDetailsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    String productId;
    CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_product_details);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                productId = extras.getString("productId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clsCommon = CommonClass.getInstance();
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyCustomerProductDetailTabAdapter adapter = new MyCustomerProductDetailTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(), productId);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}