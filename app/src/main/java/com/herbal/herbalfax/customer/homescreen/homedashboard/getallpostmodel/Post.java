
package com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("idposts")
    private String mIdposts;
    @SerializedName("IsFav")
    private String mIsFav;
    @SerializedName("IsLike")
    private String mIsLike;

    public String getmIsLike() {
        return mIsLike;
    }

    public void setmIsLike(String mIsLike) {
        this.mIsLike = mIsLike;
    }

    @SerializedName("PostComments")
    private String mPostComments;
    @SerializedName("PostCreatedAt")
    private String mPostCreatedAt;
    @SerializedName("PostCreatedOn")
    private String mPostCreatedOn;
    @SerializedName("PostDesc")
    private String mPostDesc;
    @SerializedName("PostDislikes")
    private String mPostDislikes;
    @SerializedName("PostIsMedia")
    private String mPostIsMedia;
    @SerializedName("PostLikes")
    private String mPostLikes;
    @SerializedName("PostMediaLink")
    private String mPostMediaLink;
    @SerializedName("PostMediaType")
    private String mPostMediaType;
    @SerializedName("PostThumbnail")
    private String mPostThumbnail;
    @SerializedName("PostUser")
    private String mPostUser;
    @SerializedName("UFullName")
    private String mUFullName;
    @SerializedName("UProPic")
    private String mUProPic;

    @SerializedName("PostViews")
    private String postViews;

    public String getPostViews() {
        return postViews;
    }

    public void setPostViews(String postViews) {
        this.postViews = postViews;
    }

    public String getPostGroupId() {
        return postGroupId;
    }

    public void setPostGroupId(String postGroupId) {
        this.postGroupId = postGroupId;
    }

    @SerializedName("PostGroupId")
    private String postGroupId;
    @SerializedName("view_user")
    private List<ViewUser> viewUser = null;

    public List<ViewUser> getViewUser() {
        return viewUser;
    }

    public void setViewUser(List<ViewUser> viewUser) {
        this.viewUser = viewUser;
    }

    public String getmProfTitle() {
        return mProfTitle;
    }

    public void setmProfTitle(String mProfTitle) {
        this.mProfTitle = mProfTitle;
    }

    @SerializedName("ProfTitle")
    private String mProfTitle;
    @SerializedName("comments")
    private List<Comment> comments = null;

    public String getIdposts() {
        return mIdposts;
    }

    public void setIdposts(String idposts) {
        mIdposts = idposts;
    }

    public String getIsFav() {
        return mIsFav;
    }

    public void setIsFav(String isFav) {
        mIsFav = isFav;
    }

    public String getPostComments() {
        return mPostComments;
    }

    public void setPostComments(String postComments) {
        mPostComments = postComments;
    }

    public String getPostCreatedAt() {
        return mPostCreatedAt;
    }

    public void setPostCreatedAt(String postCreatedAt) {
        mPostCreatedAt = postCreatedAt;
    }

    public String getPostCreatedOn() {
        return mPostCreatedOn;
    }

    public void setPostCreatedOn(String postCreatedOn) {
        mPostCreatedOn = postCreatedOn;
    }

    public String getPostDesc() {
        return mPostDesc;
    }

    public void setPostDesc(String postDesc) {
        mPostDesc = postDesc;
    }

    public String getPostDislikes() {
        return mPostDislikes;
    }

    public void setPostDislikes(String postDislikes) {
        mPostDislikes = postDislikes;
    }

    public String getPostIsMedia() {
        return mPostIsMedia;
    }

    public void setPostIsMedia(String postIsMedia) {
        mPostIsMedia = postIsMedia;
    }

    public String getPostLikes() {
        return mPostLikes;
    }

    public void setPostLikes(String postLikes) {
        mPostLikes = postLikes;
    }

    public String getPostMediaLink() {
        return mPostMediaLink;
    }

    public void setPostMediaLink(String postMediaLink) {
        mPostMediaLink = postMediaLink;
    }

    public String getPostMediaType() {
        return mPostMediaType;
    }

    public void setPostMediaType(String postMediaType) {
        mPostMediaType = postMediaType;
    }

    public String getPostThumbnail() {
        return mPostThumbnail;
    }

    public void setPostThumbnail(String postThumbnail) {
        mPostThumbnail = postThumbnail;
    }

    public String getPostUser() {
        return mPostUser;
    }

    public void setPostUser(String postUser) {
        mPostUser = postUser;
    }

    public String getUFullName() {
        return mUFullName;
    }

    public void setUFullName(String uFullName) {
        mUFullName = uFullName;
    }

    public String getUProPic() {
        return mUProPic;
    }

    public void setUProPic(String uProPic) {
        mUProPic = uProPic;
    }

}
