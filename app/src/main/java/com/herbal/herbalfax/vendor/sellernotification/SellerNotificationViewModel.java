package com.herbal.herbalfax.vendor.sellernotification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellerNotificationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SellerNotificationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Seller Notification fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}