package com.herbal.herbalfax.vendor.sellerproduct;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellerProductViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SellerProductViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Seller product fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}