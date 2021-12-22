
package com.herbal.herbalfax.vendor.store.storecategory;


import com.google.gson.annotations.SerializedName;

public class AllStoreCategory {

    @SerializedName("idstore_categories")
    private String idstoreCategories;
    @SerializedName("SCat_Title")
    private String sCatTitle;

    public String getIdstoreCategories() {
        return idstoreCategories;
    }

    public void setIdstoreCategories(String idstoreCategories) {
        this.idstoreCategories = idstoreCategories;
    }

    public String getSCatTitle() {
        return sCatTitle;
    }

    public void setSCatTitle(String sCatTitle) {
        this.sCatTitle = sCatTitle;
    }

}
