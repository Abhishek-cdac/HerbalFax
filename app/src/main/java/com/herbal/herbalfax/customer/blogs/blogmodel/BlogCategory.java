
package com.herbal.herbalfax.customer.blogs.blogmodel;

import com.google.gson.annotations.SerializedName;


public class BlogCategory {

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
