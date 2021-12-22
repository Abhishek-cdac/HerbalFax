package com.herbal.herbalfax.customer.homescreen.parcel;

import android.text.Html;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParcelViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ParcelViewModel() {
        mText = new MutableLiveData<>();
        String first = "Your tracking Id is ";
        String next = "<font color='#000000'>#9B19C.</font>";
        mText.setValue(String.valueOf(Html.fromHtml(first + next)));
    }

    public LiveData<String> getText() {
        return mText;
    }
}