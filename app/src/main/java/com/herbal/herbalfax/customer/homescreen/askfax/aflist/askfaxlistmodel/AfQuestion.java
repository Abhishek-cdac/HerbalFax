
package com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel;


import com.google.gson.annotations.SerializedName;


public class AfQuestion {

    public Object getIsLike() {
        return isLike;
    }

    public void setIsLike(Object isLike) {
        this.isLike = isLike;
    }

    public Object getIsFav() {
        return isFav;
    }

    public void setIsFav(Object isFav) {
        this.isFav = isFav;
    }

    @SerializedName("IsFav")
    private Object isFav;
    @SerializedName("IsLike")
    private Object isLike;
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
    @SerializedName("ProfTitle")
    private String profTitle;
    @SerializedName("QuestionCreatedTime")
    private String questionCreatedTime;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

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

    public String getProfTitle() {
        return profTitle;
    }

    public void setProfTitle(String profTitle) {
        this.profTitle = profTitle;
    }

    public String getQuestionCreatedTime() {
        return questionCreatedTime;
    }

    public void setQuestionCreatedTime(String questionCreatedTime) {
        this.questionCreatedTime = questionCreatedTime;
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
