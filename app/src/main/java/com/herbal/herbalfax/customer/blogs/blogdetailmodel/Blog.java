
package com.herbal.herbalfax.customer.blogs.blogdetailmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Blog {

    @SerializedName("BlogCategory")
    private String blogCategory;
    @SerializedName("BlogCountNo")
    private String blogCountNo;
    @SerializedName("BlogCountYes")
    private String blogCountYes;
    @SerializedName("BlogCreatedAt")
    private String blogCreatedAt;
    @SerializedName("BlogCreatedOn")
    private String blogCreatedOn;
    @SerializedName("BlogCreatedTime")
    private String blogCreatedTime;
    @SerializedName("BlogCustAffLinks")
    private String blogCustAffLinks;
    @SerializedName("BlogDesc")
    private String blogDesc;
    @SerializedName("BlogGroup")
    private String blogGroup;
    @SerializedName("BlogImage")
    private String blogImage;
    @SerializedName("BlogLikes")
    private String blogLikes;
    @SerializedName("BlogTitle")
    private String blogTitle;

    public String getuFullName() {
        return uFullName;
    }

    public void setuFullName(String uFullName) {
        this.uFullName = uFullName;
    }

    public String getuProPic() {
        return uProPic;
    }

    public void setuProPic(String uProPic) {
        this.uProPic = uProPic;
    }

    public String getProfTitle() {
        return profTitle;
    }

    public void setProfTitle(String profTitle) {
        this.profTitle = profTitle;
    }

    @SerializedName("BlogTotAffLinks")
    private String blogTotAffLinks;
    @SerializedName("BlogURL")
    private String blogURL;

    public String getIsReport() {
        return isReport;
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport;
    }

    @SerializedName("IsReport")
    private String isReport;
    @SerializedName("BlogUser")
    private String blogUser;

    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;
    @SerializedName("ProfTitle")
    private String profTitle;
    @Expose
    private String idblogs;
    @Expose
    private List<Para> paras;

    public String getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(String blogCategory) {
        this.blogCategory = blogCategory;
    }

    public String getBlogCountNo() {
        return blogCountNo;
    }

    public void setBlogCountNo(String blogCountNo) {
        this.blogCountNo = blogCountNo;
    }

    public String getBlogCountYes() {
        return blogCountYes;
    }

    public void setBlogCountYes(String blogCountYes) {
        this.blogCountYes = blogCountYes;
    }

    public String getBlogCreatedAt() {
        return blogCreatedAt;
    }

    public void setBlogCreatedAt(String blogCreatedAt) {
        this.blogCreatedAt = blogCreatedAt;
    }

    public String getBlogCreatedOn() {
        return blogCreatedOn;
    }

    public void setBlogCreatedOn(String blogCreatedOn) {
        this.blogCreatedOn = blogCreatedOn;
    }

    public String getBlogCreatedTime() {
        return blogCreatedTime;
    }

    public void setBlogCreatedTime(String blogCreatedTime) {
        this.blogCreatedTime = blogCreatedTime;
    }

    public String getBlogCustAffLinks() {
        return blogCustAffLinks;
    }

    public void setBlogCustAffLinks(String blogCustAffLinks) {
        this.blogCustAffLinks = blogCustAffLinks;
    }

    public String getBlogDesc() {
        return blogDesc;
    }

    public void setBlogDesc(String blogDesc) {
        this.blogDesc = blogDesc;
    }

    public String getBlogGroup() {
        return blogGroup;
    }

    public void setBlogGroup(String blogGroup) {
        this.blogGroup = blogGroup;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public String getBlogLikes() {
        return blogLikes;
    }

    public void setBlogLikes(String blogLikes) {
        this.blogLikes = blogLikes;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogTotAffLinks() {
        return blogTotAffLinks;
    }

    public void setBlogTotAffLinks(String blogTotAffLinks) {
        this.blogTotAffLinks = blogTotAffLinks;
    }

    public String getBlogURL() {
        return blogURL;
    }

    public void setBlogURL(String blogURL) {
        this.blogURL = blogURL;
    }

    public String getBlogUser() {
        return blogUser;
    }

    public void setBlogUser(String blogUser) {
        this.blogUser = blogUser;
    }

    public String getIdblogs() {
        return idblogs;
    }

    public void setIdblogs(String idblogs) {
        this.idblogs = idblogs;
    }

    public List<Para> getParas() {
        return paras;
    }

    public void setParas(List<Para> paras) {
        this.paras = paras;
    }

}
