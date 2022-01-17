package com.herbal.herbalfax.customer.homescreen.addcard;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddCardViewModel extends ViewModel {

    public AddCardViewModel() {

    }

    public MutableLiveData<AddCardUser> userMutableLiveData;

    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> cardNumber = new MutableLiveData<>();
    public MutableLiveData<String> cardMM = new MutableLiveData<>();
    public MutableLiveData<String> cardYY = new MutableLiveData<>();
    public MutableLiveData<String> cardCVV = new MutableLiveData<>();

    AddCardUser addCardUser;
    public MutableLiveData<AddCardUser> getPayNow() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            addCardUser = new AddCardUser( "", "", "", "", "");
        }
        return userMutableLiveData;
    }

    public void onClickPayNow(View view) {
        addCardUser = new AddCardUser(fullName.getValue(),  cardNumber.getValue(), cardMM.getValue(), cardYY.getValue(),  cardCVV.getValue());
        userMutableLiveData.setValue(addCardUser);
    }


}
