package com.herbal.herbalfax.customer.homescreen.deals;

public class NewDealsCategorys {
    private String categories;



    public NewDealsCategorys(String categories) {
        this.categories = categories;
    }

    public String getCategory() {
        return categories;
    }

    public void setCategory(String name)
    {
        this.categories = name;
    }
}
