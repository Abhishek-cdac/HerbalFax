package com.herbal.herbalfax.customer.homescreen.SavedDispensaries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedDispensariesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SavedDispensariesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Saved Dispensaries fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}