
package com.herbal.herbalfax.customer.homescreen.group.creategroup.model;


import com.google.gson.annotations.SerializedName;


public class GroupPrivacyType {

    @SerializedName("GPT_Title")
    private String gPTTitle;
    @SerializedName("idgroup_privacy_types")
    private String idgroupPrivacyTypes;

    public String getGPTTitle() {
        return gPTTitle;
    }

    public void setGPTTitle(String gPTTitle) {
        this.gPTTitle = gPTTitle;
    }

    public String getIdgroupPrivacyTypes() {
        return idgroupPrivacyTypes;
    }

    public void setIdgroupPrivacyTypes(String idgroupPrivacyTypes) {
        this.idgroupPrivacyTypes = idgroupPrivacyTypes;
    }

}
