
package com.herbal.herbalfax.vendor.storedetail.storedetailmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private Store store;
    @SerializedName("store_business_hrs")
    private List<StoreBusinessHr> storeBusinessHrs;
    @SerializedName("store_photos")
    private List<StorePhoto> storePhotos;
  @SerializedName("store_ratings")
    private List<StoreRatings> storeRatings;

    public List<StoreRatings> getStoreRatings() {
        return storeRatings;
    }

    public void setStoreRatings(List<StoreRatings> storeRatings) {
        this.storeRatings = storeRatings;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<StoreBusinessHr> getStoreBusinessHrs() {
        return storeBusinessHrs;
    }

    public void setStoreBusinessHrs(List<StoreBusinessHr> storeBusinessHrs) {
        this.storeBusinessHrs = storeBusinessHrs;
    }

    public List<StorePhoto> getStorePhotos() {
        return storePhotos;
    }

    public void setStorePhotos(List<StorePhoto> storePhotos) {
        this.storePhotos = storePhotos;
    }

}
