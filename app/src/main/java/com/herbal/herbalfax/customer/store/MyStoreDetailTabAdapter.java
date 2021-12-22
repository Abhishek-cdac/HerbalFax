package com.herbal.herbalfax.customer.store;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.herbal.herbalfax.customer.store.fragments.StoreDealsFragment;
import com.herbal.herbalfax.customer.store.fragments.StoreDetailsFragment;
import com.herbal.herbalfax.customer.store.fragments.StoreProductFragment;
import com.herbal.herbalfax.customer.store.fragments.StoreReviewFragment;

public class MyStoreDetailTabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    String storeId;

    public MyStoreDetailTabAdapter(Context context, FragmentManager fm, int totalTabs, String storeId) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.storeId = storeId;

    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("storeId", storeId);

        switch (position) {

            case 0:
                StoreProductFragment storeProductFragment = new StoreProductFragment();
                storeProductFragment.setArguments(bundle);
                return storeProductFragment;
            case 1:
                StoreDealsFragment storeDealsFragment = new StoreDealsFragment();
                storeDealsFragment.setArguments(bundle);

                return storeDealsFragment;
            case 2:
                StoreReviewFragment storeReviewFragment = new StoreReviewFragment();
                storeReviewFragment.setArguments(bundle);
                return storeReviewFragment;
            case 3:
                StoreDetailsFragment storeDetailsFragment = new StoreDetailsFragment();
                storeDetailsFragment.setArguments(bundle);
                return storeDetailsFragment;
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