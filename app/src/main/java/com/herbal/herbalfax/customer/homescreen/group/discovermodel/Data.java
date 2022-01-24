
package com.herbal.herbalfax.customer.homescreen.group.discovermodel;

import java.util.List;

import com.google.gson.annotations.Expose;


public class Data {

    @Expose
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
