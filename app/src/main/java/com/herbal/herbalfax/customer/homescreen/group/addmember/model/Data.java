
package com.herbal.herbalfax.customer.homescreen.group.addmember.model;

import java.util.List;
import com.google.gson.annotations.Expose;


public class Data {

    @Expose
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
