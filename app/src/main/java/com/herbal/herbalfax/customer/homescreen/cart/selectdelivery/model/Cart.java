
package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model;


import com.google.gson.annotations.SerializedName;


public class Cart {

    @SerializedName("Cart_SubTotal")
    private Long cartSubTotal;
    @SerializedName("Cart_Tax")
    private Long cartTax;
    @SerializedName("Cart_Total")
    private Long cartTotal;

    public Long getCartSubTotal() {
        return cartSubTotal;
    }

    public void setCartSubTotal(Long cartSubTotal) {
        this.cartSubTotal = cartSubTotal;
    }

    public Long getCartTax() {
        return cartTax;
    }

    public void setCartTax(Long cartTax) {
        this.cartTax = cartTax;
    }

    public Long getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(Long cartTotal) {
        this.cartTotal = cartTotal;
    }

}
