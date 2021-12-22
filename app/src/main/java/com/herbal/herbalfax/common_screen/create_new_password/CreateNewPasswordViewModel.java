package com.herbal.herbalfax.common_screen.create_new_password;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class CreateNewPasswordViewModel extends ViewModel {

    public CreateNewPasswordViewModel() {
    }

    public MutableLiveData<String> NewPassword = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> ResetClick = new MutableLiveData<>();


    private MutableLiveData<ChangePassword> userMutableLiveData;

    public MutableLiveData<Boolean> getRegisterUser() {

        if (ResetClick == null) {
            ResetClick = new MutableLiveData<>();
        }
        return ResetClick;

    }

    public MutableLiveData<ChangePassword> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClickRegister(View view) {
        ResetClick.setValue(true);
    }

    public void onClickReset(View view) {

        ChangePassword changePassword = new ChangePassword(Password.getValue(), NewPassword.getValue());
        userMutableLiveData.setValue(changePassword);
    }


}
