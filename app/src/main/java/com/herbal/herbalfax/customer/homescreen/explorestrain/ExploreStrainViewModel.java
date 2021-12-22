package com.herbal.herbalfax.customer.homescreen.explorestrain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExploreStrainViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExploreStrainViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is explore strain fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}