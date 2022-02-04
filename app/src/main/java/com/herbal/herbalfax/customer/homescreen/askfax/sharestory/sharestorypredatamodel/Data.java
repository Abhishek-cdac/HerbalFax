
package com.herbal.herbalfax.customer.homescreen.askfax.sharestory.sharestorypredatamodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("all_blog_categories")
    private List<AllBlogCategory> allBlogCategories;

    public List<AllBlogCategory> getAllBlogCategories() {
        return allBlogCategories;
    }

    public void setAllBlogCategories(List<AllBlogCategory> allBlogCategories) {
        this.allBlogCategories = allBlogCategories;
    }

}
