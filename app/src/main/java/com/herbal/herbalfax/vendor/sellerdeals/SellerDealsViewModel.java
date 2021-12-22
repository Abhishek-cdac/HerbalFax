package com.herbal.herbalfax.vendor.sellerdeals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellerDealsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SellerDealsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Seller Deals fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}