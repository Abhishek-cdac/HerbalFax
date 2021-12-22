package com.herbal.herbalfax.vendor.sellerproduct.addproduct;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.herbal.herbalfax.vendor.store.NewStore;

public class AddProductViewModel {

    NewProduct newProduct;
    public MutableLiveData<NewProduct> userMutableLiveData;

    public MutableLiveData<String> productName = new MutableLiveData<>();

    public MutableLiveData<String> productQuantity = new MutableLiveData<>();
    public MutableLiveData<String> productPrice = new MutableLiveData<>();
    public MutableLiveData<String> productcost = new MutableLiveData<>();
    public MutableLiveData<String> productDescription = new MutableLiveData<>();
    public MutableLiveData<String> productDistance = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();


    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public MutableLiveData<NewProduct> getRegisterProduct() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            newProduct = new NewProduct("", "", "", "", "","");

        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> onGalleryClick() {
        return Gallery;
    }


    public void onClickAddProduct(View view) {
        newProduct = new NewProduct(productName.getValue(), productPrice.getValue(), productQuantity.getValue(), productDescription.getValue(), productcost.getValue(), productDistance.getValue());
        userMutableLiveData.setValue(newProduct);
    }
}
