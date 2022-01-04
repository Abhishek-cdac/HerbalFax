package com.herbal.herbalfax.customer.homescreen.cart.addaddress;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herbal.herbalfax.customer.signup.RegisterUser;

public class AddNewAddressViewModel extends ViewModel {

    public AddNewAddressViewModel() {

    }

    public MutableLiveData<AddAddressUser> userMutableLiveData;
    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> pinCode = new MutableLiveData<>();
    public MutableLiveData<String> country = new MutableLiveData<>();
    public MutableLiveData<String> city = new MutableLiveData<>();

    public MutableLiveData<AddAddressUser> getAddAddressUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            user = new AddAddressUser( "", "", "", "", "");
        }
        return userMutableLiveData;
    }
    AddAddressUser user;

    public void onClickAddAddress(View view) {
        user = new AddAddressUser(fullName.getValue(),  city.getValue(), pinCode.getValue(), address.getValue(),  country.getValue());
        userMutableLiveData.setValue(user);
    }

}
