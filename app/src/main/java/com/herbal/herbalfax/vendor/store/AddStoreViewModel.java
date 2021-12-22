package com.herbal.herbalfax.vendor.store;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class AddStoreViewModel extends ViewModel {
    public MutableLiveData<NewStore> userMutableLiveData;

    public AddStoreViewModel() {

    }

    NewStore newStore;
    public MutableLiveData<String> storefullName = new MutableLiveData<>();
    public MutableLiveData<String> storetype = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> storedistance = new MutableLiveData<>();
    public MutableLiveData<String> storelicense = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();
    public MutableLiveData<Boolean> LogoGallery = new MutableLiveData<>();
    public MutableLiveData<Boolean> StoreImageGallery = new MutableLiveData<>();

    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public void OnLogoGallery(View view) {
        LogoGallery.setValue(true);
    }

    public void OnStoreGallery(View view) {
        StoreImageGallery.setValue(true);
    }

    public MutableLiveData<NewStore> getRegisterStore() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            newStore = new NewStore("", "", "", "", "");
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> onGalleryClick() {
        return Gallery;
    }

    public MutableLiveData<Boolean> onLogoGalleryClick() {
        return LogoGallery;
    }

    public MutableLiveData<Boolean> onStoreGalleryClick() {
        return StoreImageGallery;
    }

    public void onClickAddStore(View view) {
        newStore = new NewStore(storefullName.getValue(), location.getValue(), storelicense.getValue(), description.getValue(), storedistance.getValue());
        userMutableLiveData.setValue(newStore);
    }

}
