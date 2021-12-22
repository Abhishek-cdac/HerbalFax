
package com.herbal.herbalfax.customer.homescreen.getusermodel;


import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
