
package com.herbal.herbalfax.customer.homescreen.group.creategroup.model;


import com.google.gson.annotations.SerializedName;


public class GroupType {

    @SerializedName("GrpType_Title")
    private String grpTypeTitle;
    @SerializedName("idgroup_types")
    private String idgroupTypes;

    public String getGrpTypeTitle() {
        return grpTypeTitle;
    }

    public void setGrpTypeTitle(String grpTypeTitle) {
        this.grpTypeTitle = grpTypeTitle;
    }

    public String getIdgroupTypes() {
        return idgroupTypes;
    }

    public void setIdgroupTypes(String idgroupTypes) {
        this.idgroupTypes = idgroupTypes;
    }

}
