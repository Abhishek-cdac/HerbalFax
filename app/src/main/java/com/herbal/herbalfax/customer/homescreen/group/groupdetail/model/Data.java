
package com.herbal.herbalfax.customer.homescreen.group.groupdetail.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @Expose
    private Group group;
    @SerializedName("group_users")
    private List<GroupUser> groupUsers;
    @SerializedName("group_users_count")
    private Long groupUsersCount;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public Long getGroupUsersCount() {
        return groupUsersCount;
    }

    public void setGroupUsersCount(Long groupUsersCount) {
        this.groupUsersCount = groupUsersCount;
    }

}
