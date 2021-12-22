
package com.herbal.herbalfax.common_screen.login;

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
