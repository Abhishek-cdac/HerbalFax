package com.herbal.herbalfax.vendor.sellerproduct.productlistmodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("product_categories")
    private List<ProductCategory> productCategories;
    @SerializedName("store_product_count")
    private Long storeProductCount;
    @SerializedName("store_products")
    private List<StoreProduct> storeProducts;
    @SerializedName("vendor_stores")
    private List<Object> vendorStores;

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public Long getStoreProductCount() {
        return storeProductCount;
    }

    public void setStoreProductCount(Long storeProductCount) {
        this.storeProductCount = storeProductCount;
    }

    public List<StoreProduct> getStoreProducts() {
        return storeProducts;
    }

    public void setStoreProducts(List<StoreProduct> storeProducts) {
        this.storeProducts = storeProducts;
    }

    public List<Object> getVendorStores() {
        return vendorStores;
    }

    public void setVendorStores(List<Object> vendorStores) {
        this.vendorStores = vendorStores;
    }

}
