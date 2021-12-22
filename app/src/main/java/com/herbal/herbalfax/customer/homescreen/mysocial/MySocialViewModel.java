package com.herbal.herbalfax.customer.homescreen.mysocial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MySocialViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MySocialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my social fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}