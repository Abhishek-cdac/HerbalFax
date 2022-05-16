package com.herbal.herbalfax.customer.homescreen.askfax.sharestory;

public class CreateBlog {
    public CreateBlog(String blogTitle, String blogDesc, String blogURL) {
        this.blogTitle = blogTitle;
        this.blogDesc = blogDesc;
        BlogURL = blogURL;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDesc() {
        return blogDesc;
    }

    public void setBlogDesc(String blogDesc) {
        this.blogDesc = blogDesc;
    }

    public String getBlogURL() {
        return BlogURL;
    }

    public void setBlogURL(String blogURL) {
        BlogURL = blogURL;
    }

    String blogTitle, blogDesc, BlogURL;
}
