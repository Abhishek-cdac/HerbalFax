
package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @Expose
    private Cart cart;
    @SerializedName("cart_count")
    private String cartCount;
    @SerializedName("cart_list")
    private List<CartList> cartList;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

    public List<CartList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartList> cartList) {
        this.cartList = cartList;
    }

}
