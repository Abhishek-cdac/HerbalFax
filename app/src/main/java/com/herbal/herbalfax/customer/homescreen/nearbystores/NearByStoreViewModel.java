package com.herbal.herbalfax.customer.homescreen.nearbystores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearByStoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NearByStoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is near by fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}