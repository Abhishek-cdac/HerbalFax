package com.herbal.herbalfax.customer.homescreen.askfax;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AskFaxViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AskFaxViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ask Fax fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}