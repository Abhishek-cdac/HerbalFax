package com.herbal.herbalfax.customer.homescreen.getusermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User {

    @Expose
    private String idusers;
    @SerializedName("UActive")
    private String uActive;
    @SerializedName("UCity")
    private String uCity;
    @SerializedName("UDOB")
    private String uDOB;
    @SerializedName("UDevToken")
    private String uDevToken;
    @SerializedName("UEmailAddress")
    private String uEmailAddress;
    @SerializedName("UEmailVerified")
    private String uEmailVerified;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UGender")
    private String uGender;
    @SerializedName("UMobNo")
    private String uMobNo;
    @SerializedName("UMobNoCode")
    private String uMobNoCode;
    @SerializedName("UProPic")
    private String uProPic;
    @SerializedName("UserType")
    private String userType;
    @SerializedName("ProfTitle")
    private String ProfTitle;
    @SerializedName("UProfessionOther")
    private String UProfessionOther;

    public String getProfTitle() {
        return ProfTitle;
    }

    public void setProfTitle(String profTitle) {
        ProfTitle = profTitle;
    }

    public String getUProfessionOther() {
        return UProfessionOther;
    }

    public void setUProfessionOther(String UProfessionOther) {
        this.UProfessionOther = UProfessionOther;
    }

    public String getIdusers() {
        return idusers;
    }

    public void setIdusers(String idusers) {
        this.idusers = idusers;
    }

    public String getUActive() {
        return uActive;
    }

    public void setUActive(String uActive) {
        this.uActive = uActive;
    }

    public String getUCity() {
        return uCity;
    }

    public void setUCity(String uCity) {
        this.uCity = uCity;
    }

    public String getUDOB() {
        return uDOB;
    }

    public void setUDOB(String uDOB) {
        this.uDOB = uDOB;
    }

    public String getUDevToken() {
        return uDevToken;
    }

    public void setUDevToken(String uDevToken) {
        this.uDevToken = uDevToken;
    }

    public String getUEmailAddress() {
        return uEmailAddress;
    }

    public void setUEmailAddress(String uEmailAddress) {
        this.uEmailAddress = uEmailAddress;
    }

    public String getUEmailVerified() {
        return uEmailVerified;
    }

    public void setUEmailVerified(String uEmailVerified) {
        this.uEmailVerified = uEmailVerified;
    }

    public String getUFullName() {
        return uFullName;
    }

    public void setUFullName(String uFullName) {
        this.uFullName = uFullName;
    }

    public String getUGender() {
        return uGender;
    }

    public void setUGender(String uGender) {
        this.uGender = uGender;
    }

    public String getUMobNo() {
        return uMobNo;
    }

    public void setUMobNo(String uMobNo) {
        this.uMobNo = uMobNo;
    }

    public String getUMobNoCode() {
        return uMobNoCode;
    }

    public void setUMobNoCode(String uMobNoCode) {
        this.uMobNoCode = uMobNoCode;
    }

    public String getUProPic() {
        return uProPic;
    }

    public void setUProPic(String uProPic) {
        this.uProPic = uProPic;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
