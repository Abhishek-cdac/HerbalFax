
package com.herbal.herbalfax.customer.homescreen.favourites.favproduct.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("store_product")
    private List<StoreProduct> storeProduct;

    public List<StoreProduct> getStoreProduct() {
        return storeProduct;
    }

    public void setStoreProduct(List<StoreProduct> storeProduct) {
        this.storeProduct = storeProduct;
    }

}
