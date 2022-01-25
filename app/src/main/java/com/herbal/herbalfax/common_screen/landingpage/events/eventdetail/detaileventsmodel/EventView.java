
package com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.detaileventsmodel;


import com.google.gson.annotations.SerializedName;


public class EventView {

    @SerializedName("EV_CAt")
    private String eVCAt;
    @SerializedName("EV_COn")
    private String eVCOn;
    @SerializedName("EV_IdEvents")
    private String eVIdEvents;
    @SerializedName("EV_IdUsers")
    private String eVIdUsers;
    @SerializedName("idevent_views")
    private String ideventViews;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

    public String getEVCAt() {
        return eVCAt;
    }

    public void setEVCAt(String eVCAt) {
        this.eVCAt = eVCAt;
    }

    public String getEVCOn() {
        return eVCOn;
    }

    public void setEVCOn(String eVCOn) {
        this.eVCOn = eVCOn;
    }

    public String getEVIdEvents() {
        return eVIdEvents;
    }

    public void setEVIdEvents(String eVIdEvents) {
        this.eVIdEvents = eVIdEvents;
    }

    public String getEVIdUsers() {
        return eVIdUsers;
    }

    public void setEVIdUsers(String eVIdUsers) {
        this.eVIdUsers = eVIdUsers;
    }

    public String getIdeventViews() {
        return ideventViews;
    }

    public void setIdeventViews(String ideventViews) {
        this.ideventViews = ideventViews;
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
