package com.herbal.herbalfax.customer.homescreen.products.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.herbal.herbalfax.customer.homescreen.products.fragments.CustomerProductFragment;
import com.herbal.herbalfax.customer.homescreen.products.fragments.CustomerProductReviewFragment;

public class MyCustomerProductDetailTabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    String productId;

    public MyCustomerProductDetailTabAdapter(Context context, FragmentManager fm, int totalTabs, String productId) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.productId = productId;

    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);

        switch (position) {

            case 0:
                CustomerProductFragment customerProductFragment = new CustomerProductFragment();
                customerProductFragment.setArguments(bundle);
                return customerProductFragment;
            case 1:
                CustomerProductReviewFragment customerProductReviewFragment = new CustomerProductReviewFragment();
                customerProductReviewFragment.setArguments(bundle);
                return customerProductReviewFragment;

            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}