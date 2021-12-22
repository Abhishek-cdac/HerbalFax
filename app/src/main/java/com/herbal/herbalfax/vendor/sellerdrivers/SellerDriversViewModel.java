package com.herbal.herbalfax.vendor.sellerdrivers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellerDriversViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SellerDriversViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Seller Driver fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}