package com.herbal.herbalfax.common_screen.login;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class LoginViewModel extends ViewModel {

    public LoginViewModel() {
    }

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> ForgotClick = new MutableLiveData<>();


    private MutableLiveData<LoginUser> userMutableLiveData;

    public MutableLiveData<Boolean> getRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClickRegister(View view) {
        RegisterClick.setValue(true);
    }


    public void onClickLogin(View view) {

        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);
    }

    public void onClickForgotPass(View view) {
        ForgotClick.setValue(true);
    }

    public MutableLiveData<Boolean> getForgotClick() {

        if (ForgotClick == null) {
            ForgotClick = new MutableLiveData<>();
        }
        return ForgotClick;

    }

}
