
package com.herbal.herbalfax.customer.homescreen.askfax.sharestorypredatamodel;


import com.google.gson.annotations.SerializedName;


public class AllBlogCategory {

    @SerializedName("BCat_Image")
    private String bCatImage;
    @SerializedName("BCat_Title")
    private String bCatTitle;
    @SerializedName("idblog_categories")
    private String idblogCategories;

    public String getBCatImage() {
        return bCatImage;
    }

    public void setBCatImage(String bCatImage) {
        this.bCatImage = bCatImage;
    }

    public String getBCatTitle() {
        return bCatTitle;
    }

    public void setBCatTitle(String bCatTitle) {
        this.bCatTitle = bCatTitle;
    }

    public String getIdblogCategories() {
        return idblogCategories;
    }

    public void setIdblogCategories(String idblogCategories) {
        this.idblogCategories = idblogCategories;
    }

}
