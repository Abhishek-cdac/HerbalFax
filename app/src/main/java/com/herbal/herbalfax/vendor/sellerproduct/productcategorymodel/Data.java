
package com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("product_categories")
    private List<ProductCategory> productCategories;

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

}
