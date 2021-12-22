
package com.herbal.herbalfax.customer.homescreen.homedashboard.getallcommentmodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("post_comments")
    private List<PostComment> postComments;

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

}
