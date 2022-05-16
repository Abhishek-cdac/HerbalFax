
package com.herbal.herbalfax.customer.homescreen.group.creategroup.creategrpmodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("GPT_Title")
    private String gPTTitle;
    @SerializedName("GrpAt")
    private String grpAt;
    @SerializedName("GrpDesc")
    private String grpDesc;
    @SerializedName("GrpImage")
    private String grpImage;
    @SerializedName("GrpName")
    private String grpName;
    @SerializedName("GrpOn")
    private String grpOn;
    @SerializedName("GrpOwner")
    private String grpOwner;
    @SerializedName("GrpPrivacy")
    private String grpPrivacy;
    @SerializedName("GrpType")
    private String grpType;
    @SerializedName("GrpType_Title")
    private String grpTypeTitle;
    @Expose
    private String idgroups;
    @SerializedName("user_count")
    private String userCount;

    public String getGPTTitle() {
        return gPTTitle;
    }

    public void setGPTTitle(String gPTTitle) {
        this.gPTTitle = gPTTitle;
    }

    public String getGrpAt() {
        return grpAt;
    }

    public void setGrpAt(String grpAt) {
        this.grpAt = grpAt;
    }

    public String getGrpDesc() {
        return grpDesc;
    }

    public void setGrpDesc(String grpDesc) {
        this.grpDesc = grpDesc;
    }

    public String getGrpImage() {
        return grpImage;
    }

    public void setGrpImage(String grpImage) {
        this.grpImage = grpImage;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getGrpOn() {
        return grpOn;
    }

    public void setGrpOn(String grpOn) {
        this.grpOn = grpOn;
    }

    public String getGrpOwner() {
        return grpOwner;
    }

    public void setGrpOwner(String grpOwner) {
        this.grpOwner = grpOwner;
    }

    public String getGrpPrivacy() {
        return grpPrivacy;
    }

    public void setGrpPrivacy(String grpPrivacy) {
        this.grpPrivacy = grpPrivacy;
    }

    public String getGrpType() {
        return grpType;
    }

    public void setGrpType(String grpType) {
        this.grpType = grpType;
    }

    public String getGrpTypeTitle() {
        return grpTypeTitle;
    }

    public void setGrpTypeTitle(String grpTypeTitle) {
        this.grpTypeTitle = grpTypeTitle;
    }

    public String getIdgroups() {
        return idgroups;
    }

    public void setIdgroups(String idgroups) {
        this.idgroups = idgroups;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

}
