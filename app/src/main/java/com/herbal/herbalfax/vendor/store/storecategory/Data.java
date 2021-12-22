
package com.herbal.herbalfax.vendor.store.storecategory;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("all_store_categories")
    private List<AllStoreCategory> allStoreCategories;

    public List<AllStoreCategory> getAllStoreCategories() {
        return allStoreCategories;
    }

    public void setAllStoreCategories(List<AllStoreCategory> allStoreCategories) {
        this.allStoreCategories = allStoreCategories;
    }

}
