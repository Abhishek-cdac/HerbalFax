
package com.herbal.herbalfax.customer.blogs.blogdetailmodel;


import com.google.gson.annotations.Expose;


public class Data {

    @Expose
    private Blog blog;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

}
