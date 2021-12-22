package com.herbal.herbalfax.common_screen.verify_email_after_signup;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class VerifyCodeAfterSignUpViewModel extends ViewModel {
    public VerifyCodeAfterSignUpViewModel() {


    }

    private MutableLiveData<ConfirmOtp> userMutableLiveData;

    public MutableLiveData<String> OTP = new MutableLiveData<>();
    public MutableLiveData<Boolean> ConfirmClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> ResendClick = new MutableLiveData<>();

    public void onClickConfirm(View view) {

        ConfirmOtp confirmOtp = new ConfirmOtp(OTP.getValue());
        userMutableLiveData.setValue(confirmOtp);
    }


    public void onClickResend(View view) {
        ResendClick.setValue(true);
    }

    public MutableLiveData<Boolean> getResendClick() {

        if (ResendClick == null) {
            ResendClick = new MutableLiveData<>();
        }
        return ResendClick;

    }

    public MutableLiveData<ConfirmOtp> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }


}