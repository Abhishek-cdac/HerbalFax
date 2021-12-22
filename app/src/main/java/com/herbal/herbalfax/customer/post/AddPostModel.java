package com.herbal.herbalfax.customer.post;

public class AddPostModel {
    private final String strDescription;

    public AddPostModel(String Description) {
        strDescription = Description;

    }

    public String getStrDescription() {
        return strDescription;
    }


}
