
package com.herbal.herbalfax.vendor.storelist.storelistmodel;

import java.util.List;

import com.google.gson.annotations.Expose;


public class Data {

    @Expose
    private List<Store> stores;

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

}
