package com.herbal.herbalfax.vendor.sellerdeals.adddeal;

import android.view.View;

import androidx.lifecycle.MutableLiveData;


public class AddDealsViewModel {

    NewDeals newDeals;
    public MutableLiveData<NewDeals> userMutableLiveData;

    public MutableLiveData<String> productDealName = new MutableLiveData<>();

    public MutableLiveData<String> productDealQuantityNo = new MutableLiveData<>();
    public MutableLiveData<String> productDealPrice = new MutableLiveData<>();
    public MutableLiveData<String> productDealLocation = new MutableLiveData<>();
    public MutableLiveData<String> productDealDate = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();


    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public MutableLiveData<NewDeals> getRegisterProduct() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            newDeals = new NewDeals("", "", "", "", "");

        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> onGalleryClick() {
        return Gallery;
    }


    public void onClickAddProductDeals(View view) {
        newDeals = new NewDeals(productDealName.getValue(), productDealLocation.getValue(), productDealDate.getValue(), productDealQuantityNo.getValue(), productDealPrice.getValue());
        userMutableLiveData.setValue(newDeals);
    }
}
