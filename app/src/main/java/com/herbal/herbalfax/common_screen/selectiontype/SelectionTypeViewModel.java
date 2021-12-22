package com.herbal.herbalfax.common_screen.selectiontype;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class SelectionTypeViewModel extends ViewModel {

    public SelectionTypeViewModel() {
    }


    public MutableLiveData<Boolean> CustomerType = new MutableLiveData<>();
    public MutableLiveData<Boolean> SellerType = new MutableLiveData<>();
    public MutableLiveData<Boolean> ContinueClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> LoginClick = new MutableLiveData<>();



    public MutableLiveData<Boolean> onContinueUser() {

        if (ContinueClick == null) {
            ContinueClick = new MutableLiveData<>();
        }
        return ContinueClick;

    }

    public MutableLiveData<Boolean> OnSellerClick() {

        return SellerType;

    }

    public MutableLiveData<Boolean> OnCustomerClick() {

        return CustomerType;

    }


    public MutableLiveData<Boolean> OnLoginHereClick() {

        return LoginClick;

    }




    public void OnCustomer(View view) {
        CustomerType.setValue(true);
    }

    public void onSeller(View view) {
        SellerType.setValue(true);
    }

    public void onLoginHere(View view) {
        LoginClick.setValue(true);
    }

    public void OnContinue(View view) {
        ContinueClick.setValue(true);
    }



}
