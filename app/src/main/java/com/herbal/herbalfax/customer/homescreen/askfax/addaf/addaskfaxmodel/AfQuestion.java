
package com.herbal.herbalfax.customer.homescreen.askfax.addaf.addaskfaxmodel;


import com.google.gson.annotations.SerializedName;


public class AfQuestion {

    @SerializedName("AFQ_Answers")
    private String aFQAnswers;
    @SerializedName("AFQ_CAt")
    private String aFQCAt;
    @SerializedName("AFQ_COn")
    private String aFQCOn;
    @SerializedName("AFQ_Favs")
    private String aFQFavs;
    @SerializedName("AFQ_IdUser")
    private String aFQIdUser;
    @SerializedName("AFQ_Likes")
    private String aFQLikes;
    @SerializedName("AFQ_PrivacyType")
    private String aFQPrivacyType;
    @SerializedName("AFQ_Shares")
    private String aFQShares;
    @SerializedName("AFQ_Title")
    private String aFQTitle;
    @SerializedName("idaf_questions")
    private String idafQuestions;

    public String getAFQAnswers() {
        return aFQAnswers;
    }

    public void setAFQAnswers(String aFQAnswers) {
        this.aFQAnswers = aFQAnswers;
    }

    public String getAFQCAt() {
        return aFQCAt;
    }

    public void setAFQCAt(String aFQCAt) {
        this.aFQCAt = aFQCAt;
    }

    public String getAFQCOn() {
        return aFQCOn;
    }

    public void setAFQCOn(String aFQCOn) {
        this.aFQCOn = aFQCOn;
    }

    public String getAFQFavs() {
        return aFQFavs;
    }

    public void setAFQFavs(String aFQFavs) {
        this.aFQFavs = aFQFavs;
    }

    public String getAFQIdUser() {
        return aFQIdUser;
    }

    public void setAFQIdUser(String aFQIdUser) {
        this.aFQIdUser = aFQIdUser;
    }

    public String getAFQLikes() {
        return aFQLikes;
    }

    public void setAFQLikes(String aFQLikes) {
        this.aFQLikes = aFQLikes;
    }

    public String getAFQPrivacyType() {
        return aFQPrivacyType;
    }

    public void setAFQPrivacyType(String aFQPrivacyType) {
        this.aFQPrivacyType = aFQPrivacyType;
    }

    public String getAFQShares() {
        return aFQShares;
    }

    public void setAFQShares(String aFQShares) {
        this.aFQShares = aFQShares;
    }

    public String getAFQTitle() {
        return aFQTitle;
    }

    public void setAFQTitle(String aFQTitle) {
        this.aFQTitle = aFQTitle;
    }

    public String getIdafQuestions() {
        return idafQuestions;
    }

    public void setIdafQuestions(String idafQuestions) {
        this.idafQuestions = idafQuestions;
    }

}
