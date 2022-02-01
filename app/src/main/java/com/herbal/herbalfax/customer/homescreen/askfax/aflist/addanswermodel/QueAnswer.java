
package com.herbal.herbalfax.customer.homescreen.askfax.aflist.addanswermodel;


import com.google.gson.annotations.SerializedName;


public class QueAnswer {

    @SerializedName("AFA_Date")
    private String aFADate;
    @SerializedName("AFA_desc")
    private String aFADesc;
    @SerializedName("AFA_IdQuestion")
    private String aFAIdQuestion;
    @SerializedName("AFA_IdUser")
    private String aFAIdUser;
    @SerializedName("AFA_Likes")
    private String aFALikes;
    @SerializedName("AFA_Time")
    private String aFATime;
    @SerializedName("AnswerCreatedTime")
    private String answerCreatedTime;
    @SerializedName("idaf_answers")
    private String idafAnswers;
    @SerializedName("ProfTitle")
    private String profTitle;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

    public String getAFADate() {
        return aFADate;
    }

    public void setAFADate(String aFADate) {
        this.aFADate = aFADate;
    }

    public String getAFADesc() {
        return aFADesc;
    }

    public void setAFADesc(String aFADesc) {
        this.aFADesc = aFADesc;
    }

    public String getAFAIdQuestion() {
        return aFAIdQuestion;
    }

    public void setAFAIdQuestion(String aFAIdQuestion) {
        this.aFAIdQuestion = aFAIdQuestion;
    }

    public String getAFAIdUser() {
        return aFAIdUser;
    }

    public void setAFAIdUser(String aFAIdUser) {
        this.aFAIdUser = aFAIdUser;
    }

    public String getAFALikes() {
        return aFALikes;
    }

    public void setAFALikes(String aFALikes) {
        this.aFALikes = aFALikes;
    }

    public String getAFATime() {
        return aFATime;
    }

    public void setAFATime(String aFATime) {
        this.aFATime = aFATime;
    }

    public String getAnswerCreatedTime() {
        return answerCreatedTime;
    }

    public void setAnswerCreatedTime(String answerCreatedTime) {
        this.answerCreatedTime = answerCreatedTime;
    }

    public String getIdafAnswers() {
        return idafAnswers;
    }

    public void setIdafAnswers(String idafAnswers) {
        this.idafAnswers = idafAnswers;
    }

    public String getProfTitle() {
        return profTitle;
    }

    public void setProfTitle(String profTitle) {
        this.profTitle = profTitle;
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
