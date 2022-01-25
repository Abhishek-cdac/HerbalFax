
package com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.detaileventsmodel;


import com.google.gson.annotations.SerializedName;


public class EventComment {

    @SerializedName("EC_CAt")
    private String eCCAt;
    @SerializedName("EC_COn")
    private String eCCOn;
    @SerializedName("EC_Comments")
    private String eCComments;
    @SerializedName("EC_IdEvents")
    private String eCIdEvents;
    @SerializedName("EC_IdUsers")
    private String eCIdUsers;
    @SerializedName("idevent_comments")
    private String ideventComments;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

    public String getECCAt() {
        return eCCAt;
    }

    public void setECCAt(String eCCAt) {
        this.eCCAt = eCCAt;
    }

    public String getECCOn() {
        return eCCOn;
    }

    public void setECCOn(String eCCOn) {
        this.eCCOn = eCCOn;
    }

    public String getECComments() {
        return eCComments;
    }

    public void setECComments(String eCComments) {
        this.eCComments = eCComments;
    }

    public String getECIdEvents() {
        return eCIdEvents;
    }

    public void setECIdEvents(String eCIdEvents) {
        this.eCIdEvents = eCIdEvents;
    }

    public String getECIdUsers() {
        return eCIdUsers;
    }

    public void setECIdUsers(String eCIdUsers) {
        this.eCIdUsers = eCIdUsers;
    }

    public String getIdeventComments() {
        return ideventComments;
    }

    public void setIdeventComments(String ideventComments) {
        this.ideventComments = ideventComments;
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
