package com.herbal.herbalfax.signupnewvendor;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

public class NewVendorSignUpScTwoViewModel {
    public MutableLiveData<Boolean> ContactUsClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> LoginClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> BackClick = new MutableLiveData<>();

    public MutableLiveData<Boolean> OnNextHereClick() {

        return ContactUsClick;

    }
    public MutableLiveData<Boolean> OnBackClick() {

        return BackClick;

    }
    public MutableLiveData<Boolean> OnLoginHereClick() {

        return LoginClick;

    }

    public void onLoginHere(View view) {
        LoginClick.setValue(true);
    }


    public void onNextHere(View view) {
        ContactUsClick.setValue(true);
    }
    public void onBackHere(View view) {
        BackClick.setValue(true);
    }


}
