package com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("idpost_comments")
    @Expose
    private String idpostComments;
    @SerializedName("PC_Comment")
    @Expose
    private String pCComment;
    @SerializedName("PC_Likes")
    @Expose
    private String pCLikes;
    @SerializedName("PC_CreatedOn")
    @Expose
    private String pCCreatedOn;
    @SerializedName("PC_CreatedAt")
    @Expose
    private String pCCreatedAt;
    @SerializedName("UFullName")
    @Expose
    private String uFullName;
    @SerializedName("UProPic")
    @Expose
    private String uProPic;
    @SerializedName("IsLike")
    @Expose
    private Object isLike;

    public String getIdpostComments() {
        return idpostComments;
    }

    public void setIdpostComments(String idpostComments) {
        this.idpostComments = idpostComments;
    }

    public String getPCComment() {
        return pCComment;
    }

    public void setPCComment(String pCComment) {
        this.pCComment = pCComment;
    }

    public String getPCLikes() {
        return pCLikes;
    }

    public void setPCLikes(String pCLikes) {
        this.pCLikes = pCLikes;
    }

    public String getPCCreatedOn() {
        return pCCreatedOn;
    }

    public void setPCCreatedOn(String pCCreatedOn) {
        this.pCCreatedOn = pCCreatedOn;
    }

    public String getPCCreatedAt() {
        return pCCreatedAt;
    }

    public void setPCCreatedAt(String pCCreatedAt) {
        this.pCCreatedAt = pCCreatedAt;
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

    public Object getIsLike() {
        return isLike;
    }

    public void setIsLike(Object isLike) {
        this.isLike = isLike;
    }

}

