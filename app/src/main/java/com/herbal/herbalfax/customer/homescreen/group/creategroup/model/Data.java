
package com.herbal.herbalfax.customer.homescreen.group.creategroup.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("group_privacy_types")
    private List<GroupPrivacyType> groupPrivacyTypes;
    @SerializedName("group_types")
    private List<GroupType> groupTypes;

    public List<GroupPrivacyType> getGroupPrivacyTypes() {
        return groupPrivacyTypes;
    }

    public void setGroupPrivacyTypes(List<GroupPrivacyType> groupPrivacyTypes) {
        this.groupPrivacyTypes = groupPrivacyTypes;
    }

    public List<GroupType> getGroupTypes() {
        return groupTypes;
    }

    public void setGroupTypes(List<GroupType> groupTypes) {
        this.groupTypes = groupTypes;
    }

}
