
package com.herbal.herbalfax.customer.homescreen.group.creategroup.creategrpmodel;


import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
