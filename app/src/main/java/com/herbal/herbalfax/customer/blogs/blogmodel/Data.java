
package com.herbal.herbalfax.customer.blogs.blogmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Data {

    @SerializedName("blog_categories")
    private List<BlogCategory> blogCategories;
    @Expose
    private List<Blog> blogs;

    public List<BlogCategory> getBlogCategories() {
        return blogCategories;
    }

    public void setBlogCategories(List<BlogCategory> blogCategories) {
        this.blogCategories = blogCategories;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

}
