package com.herbal.herbalfax.customer.homescreen.group.creategroup;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herbal.herbalfax.vendor.sellerdeals.adddeal.NewDeals;

public class CreateGroupsViewModel extends ViewModel {

    NewGroup newGroup;
    public MutableLiveData<NewGroup> userMutableLiveData;

    public MutableLiveData<String> groupName = new MutableLiveData<>();

    public MutableLiveData<String> groupDec = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();

    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public MutableLiveData<NewGroup> getRegisterGroup() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            newGroup = new NewGroup("",  "");

        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> onGalleryClick() {
        return Gallery;
    }


    public void onClickAddGroup(View view) {
        newGroup = new NewGroup(groupName.getValue(), groupDec.getValue());
        userMutableLiveData.setValue(newGroup);
    }
}
