
package com.herbal.herbalfax.customer.homescreen.group.groupdetail.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GroupUser {

    @SerializedName("GU_At")
    private String gUAt;
    @SerializedName("GU_IdGrp")
    private String gUIdGrp;
    @SerializedName("GU_IdUser")
    private String gUIdUser;
    @SerializedName("GU_IsOwner")
    private String gUIsOwner;
    @SerializedName("GU_On")
    private String gUOn;
    @SerializedName("idgroup_users")
    private String idgroupUsers;
    @Expose
    private String idusers;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

    public String getGUAt() {
        return gUAt;
    }

    public void setGUAt(String gUAt) {
        this.gUAt = gUAt;
    }

    public String getGUIdGrp() {
        return gUIdGrp;
    }

    public void setGUIdGrp(String gUIdGrp) {
        this.gUIdGrp = gUIdGrp;
    }

    public String getGUIdUser() {
        return gUIdUser;
    }

    public void setGUIdUser(String gUIdUser) {
        this.gUIdUser = gUIdUser;
    }

    public String getGUIsOwner() {
        return gUIsOwner;
    }

    public void setGUIsOwner(String gUIsOwner) {
        this.gUIsOwner = gUIsOwner;
    }

    public String getGUOn() {
        return gUOn;
    }

    public void setGUOn(String gUOn) {
        this.gUOn = gUOn;
    }

    public String getIdgroupUsers() {
        return idgroupUsers;
    }

    public void setIdgroupUsers(String idgroupUsers) {
        this.idgroupUsers = idgroupUsers;
    }

    public String getIdusers() {
        return idusers;
    }

    public void setIdusers(String idusers) {
        this.idusers = idusers;
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
