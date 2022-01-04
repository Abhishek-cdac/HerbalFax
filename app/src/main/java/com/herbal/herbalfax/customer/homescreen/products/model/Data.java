
package com.herbal.herbalfax.customer.homescreen.products.model;


import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("cart_count")
    private String cartCount;

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

}
