package com.herbal.herbalfax.customer.homescreen.homedashboard.getallpostmodel;

import com.google.gson.annotations.SerializedName;

public class ViewUser {

    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

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
