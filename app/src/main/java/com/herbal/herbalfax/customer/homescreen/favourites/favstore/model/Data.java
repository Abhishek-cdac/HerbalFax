
package com.herbal.herbalfax.customer.homescreen.favourites.favstore.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("store_add_to_fav")
    private List<StoreAddToFav> storeAddToFav;

    public List<StoreAddToFav> getStoreAddToFav() {
        return storeAddToFav;
    }

    public void setStoreAddToFav(List<StoreAddToFav> storeAddToFav) {
        this.storeAddToFav = storeAddToFav;
    }

}
