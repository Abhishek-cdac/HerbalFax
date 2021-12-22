
package com.herbal.herbalfax.customer.homescreen.homedashboard.getallcommentmodel;


import com.google.gson.annotations.SerializedName;


public class PostComment {

    @SerializedName("idpost_comments")
    private String idpostComments;
    @SerializedName("IsLike")
    private Object isLike;
    @SerializedName("PC_Comment")
    private String pCComment;
    @SerializedName("PC_CreatedOn")
    private String pCCreatedOn;
    @SerializedName("PC_Likes")
    private String pCLikes;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

    public String getIdpostComments() {
        return idpostComments;
    }

    public void setIdpostComments(String idpostComments) {
        this.idpostComments = idpostComments;
    }

    public Object getIsLike() {
        return isLike;
    }

    public void setIsLike(Object isLike) {
        this.isLike = isLike;
    }

    public String getPCComment() {
        return pCComment;
    }

    public void setPCComment(String pCComment) {
        this.pCComment = pCComment;
    }

    public String getPCCreatedOn() {
        return pCCreatedOn;
    }

    public void setPCCreatedOn(String pCCreatedOn) {
        this.pCCreatedOn = pCCreatedOn;
    }

    public String getPCLikes() {
        return pCLikes;
    }

    public void setPCLikes(String pCLikes) {
        this.pCLikes = pCLikes;
    }

    public String getUFullName() {
        return uFullName;
    }

    public void setUFullName(String uFullName) {
        this.uFullName = uFullName;
    }

    public String getUProPic() {
        return uProPic;
    }

    public void setUProPic(String uProPic) {
        this.uProPic = uProPic;
    }

}
