package com.herbal.herbalfax.signupnew;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herbal.herbalfax.customer.signup.RegisterUser;

public class SignUpNewAsCustomerViewModel extends ViewModel {

    public SignUpNewAsCustomerViewModel() {

    }

    public MutableLiveData<RegisterUser> userMutableLiveData;

    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> birthDate = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();
    public MutableLiveData<String> profession = new MutableLiveData<>();
    public MutableLiveData<String> city = new MutableLiveData<>();
    public MutableLiveData<String> zipCode = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> countryCode = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();

    public MutableLiveData<Boolean> LoginClick = new MutableLiveData<>();

    public MutableLiveData<RegisterUser> getRegisterUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            user = new RegisterUser( "", "", "", "", "", "", "");
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> onGalleryClick() {

        return Gallery;

    }

    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public MutableLiveData<Boolean> OnNextHereClick() {

        return NextClick;

    }
    public MutableLiveData<Boolean> OnLoginHereClick() {

        return LoginClick;

    }

    public void onLoginHere(View view) {
        LoginClick.setValue(true);
    }


    public void onNextHere(View view) {
        NextClick.setValue(true);
    }


    RegisterUser user;

    public void onClickRegister(View view) {
        user = new RegisterUser(fullName.getValue(),  city.getValue(), zipCode.getValue(), address.getValue(),  phoneNumber.getValue(), email.getValue(), password.getValue());
        userMutableLiveData.setValue(user);
    }


    public void clearFileds() {
        user = new RegisterUser("", "", "", "", "", "", "");
        userMutableLiveData.setValue(user);

    }

}
