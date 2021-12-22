
package com.herbal.herbalfax.vendor.sellerproduct.productdetail.productdetailsmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StorePhoto;


public class Data {

    @SerializedName("store_product")
    private StoreProduct storeProduct;
    @SerializedName("store_product_photos")
    private List<StorePhoto> storeProductPhotos;
    @SerializedName("store_product_ratings")
    private List<Object> storeProductRatings;

    public StoreProduct getStoreProduct() {
        return storeProduct;
    }

    public void setStoreProduct(StoreProduct storeProduct) {
        this.storeProduct = storeProduct;
    }

    public List<StorePhoto> getStoreProductPhotos() {
        return storeProductPhotos;
    }

    public void setStoreProductPhotos(List<StorePhoto> storeProductPhotos) {
        this.storeProductPhotos = storeProductPhotos;
    }

    public List<Object> getStoreProductRatings() {
        return storeProductRatings;
    }

    public void setStoreProductRatings(List<Object> storeProductRatings) {
        this.storeProductRatings = storeProductRatings;
    }

}
