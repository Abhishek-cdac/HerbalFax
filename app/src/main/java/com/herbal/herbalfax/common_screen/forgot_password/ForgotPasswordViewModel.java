package com.herbal.herbalfax.common_screen.forgot_password;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.herbal.herbalfax.common_screen.verify_email_after_signup.ConfirmOtp;

public class ForgotPasswordViewModel extends ViewModel {
    public ForgotPasswordViewModel() {


    }
    private MutableLiveData<ConfirmEmail> userMutableLiveData;
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<Boolean> ContinueClick = new MutableLiveData<>();

    public MutableLiveData<ConfirmEmail> getConfirmEmail() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }
public void onClickConfirm(View view) {

    ConfirmEmail confirmEmail = new ConfirmEmail(EmailAddress.getValue());
    userMutableLiveData.setValue(confirmEmail);
}
}