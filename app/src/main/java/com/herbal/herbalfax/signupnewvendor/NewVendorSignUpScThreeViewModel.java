package com.herbal.herbalfax.signupnewvendor;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

public class NewVendorSignUpScThreeViewModel {
    public MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();

    public MutableLiveData<Boolean> OnBackClick() {

        return BackClick;

    }
    public MutableLiveData<Boolean> OnNextHereClick() {

        return NextClick;

    }

    public void onNextHere(View view) {
        NextClick.setValue(true);
    }


    public void onBackHere(View view) {
        BackClick.setValue(true);
    }


}
