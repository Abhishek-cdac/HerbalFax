package com.herbal.herbalfax.vendor.sellerproduct.productlistmodel;

import com.google.gson.annotations.SerializedName;

public class ProductCategory {

    @SerializedName("idstore_product_categories")
    private String idstoreProductCategories;
    @SerializedName("SPC_Title")
    private String sPCTitle;

    public String getIdstoreProductCategories() {
        return idstoreProductCategories;
    }

    public void setIdstoreProductCategories(String idstoreProductCategories) {
        this.idstoreProductCategories = idstoreProductCategories;
    }

    public String getSPCTitle() {
        return sPCTitle;
    }

    public void setSPCTitle(String sPCTitle) {
        this.sPCTitle = sPCTitle;
    }

}
