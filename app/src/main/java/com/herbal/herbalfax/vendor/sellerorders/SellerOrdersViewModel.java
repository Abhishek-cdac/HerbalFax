package com.herbal.herbalfax.vendor.sellerorders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellerOrdersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SellerOrdersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Seller orders fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}