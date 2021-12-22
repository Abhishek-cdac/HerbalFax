
package com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("posts")
    private List<Post> mPosts;

    public List<Post> getPosts() {
        return mPosts;
    }

    public void setPosts(List<Post> posts) {
        mPosts = posts;
    }

}
