
package com.herbal.herbalfax.customer.homescreen.group.addmember.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected = false;

    @Expose
    private String idusers;
    @SerializedName("ProfTitle")
    private String profTitle;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

    public String getIdusers() {
        return idusers;
    }

    public void setIdusers(String idusers) {
        this.idusers = idusers;
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
