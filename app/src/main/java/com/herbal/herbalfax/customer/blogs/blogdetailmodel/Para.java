
package com.herbal.herbalfax.customer.blogs.blogdetailmodel;

import com.google.gson.annotations.SerializedName;


public class Para {

    @SerializedName("BPara_CreatedAt")
    private String bParaCreatedAt;
    @SerializedName("BPara_CreatedOn")
    private String bParaCreatedOn;
    @SerializedName("BPara_Desc")
    private String bParaDesc;
    @SerializedName("BPara_IdBlog")
    private String bParaIdBlog;
    @SerializedName("BPara_Image")
    private String bParaImage;
    @SerializedName("BPara_IsMain")
    private String bParaIsMain;
    @SerializedName("idblog_paras")
    private String idblogParas;

    public String getBParaCreatedAt() {
        return bParaCreatedAt;
    }

    public void setBParaCreatedAt(String bParaCreatedAt) {
        this.bParaCreatedAt = bParaCreatedAt;
    }

    public String getBParaCreatedOn() {
        return bParaCreatedOn;
    }

    public void setBParaCreatedOn(String bParaCreatedOn) {
        this.bParaCreatedOn = bParaCreatedOn;
    }

    public String getBParaDesc() {
        return bParaDesc;
    }

    public void setBParaDesc(String bParaDesc) {
        this.bParaDesc = bParaDesc;
    }

    public String getBParaIdBlog() {
        return bParaIdBlog;
    }

    public void setBParaIdBlog(String bParaIdBlog) {
        this.bParaIdBlog = bParaIdBlog;
    }

    public String getBParaImage() {
        return bParaImage;
    }

    public void setBParaImage(String bParaImage) {
        this.bParaImage = bParaImage;
    }

    public String getBParaIsMain() {
        return bParaIsMain;
    }

    public void setBParaIsMain(String bParaIsMain) {
        this.bParaIsMain = bParaIsMain;
    }

    public String getIdblogParas() {
        return idblogParas;
    }

    public void setIdblogParas(String idblogParas) {
        this.idblogParas = idblogParas;
    }

}
