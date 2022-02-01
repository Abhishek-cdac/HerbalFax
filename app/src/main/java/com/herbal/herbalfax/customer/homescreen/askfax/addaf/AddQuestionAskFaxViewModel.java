package com.herbal.herbalfax.customer.homescreen.askfax.addaf;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herbal.herbalfax.common_screen.login.LoginUser;

public class AddQuestionAskFaxViewModel extends ViewModel {

    public AddQuestionAskFaxViewModel() {
    }

    public MutableLiveData<String> Question = new MutableLiveData<>();
    public MutableLiveData<Boolean> AddClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> CancelClick = new MutableLiveData<>();


    private MutableLiveData<AskfaxQue> userMutableLiveData;

    public MutableLiveData<Boolean> getAddQuestion() {

        if (AddClick == null) {
            AddClick = new MutableLiveData<>();
        }
        return AddClick;

    }

    public MutableLiveData<AskfaxQue> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }


    public void onClickAdd(View view) {

        AskfaxQue askfaxQue = new AskfaxQue(Question.getValue());
        userMutableLiveData.setValue(askfaxQue);
    }

    public void onClickCancel(View view) {
        CancelClick.setValue(true);
    }

    public MutableLiveData<Boolean> getCancelClick() {

        if (CancelClick == null) {
            CancelClick = new MutableLiveData<>();
        }
        return CancelClick;

    }

}
