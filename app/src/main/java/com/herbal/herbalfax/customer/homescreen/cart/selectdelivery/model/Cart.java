
package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model;


import com.google.gson.annotations.SerializedName;


public class Cart {

    @SerializedName("Cart_SubTotal")
    private Double cartSubTotal;
    @SerializedName("Cart_Tax")
    private Double cartTax;
    @SerializedName("Cart_Total")
    private Double cartTotal;

    public Double getCartSubTotal() {
        return cartSubTotal;
    }

    public void setCartSubTotal(Double cartSubTotal) {
        this.cartSubTotal = cartSubTotal;
    }

    public Double getCartTax() {
        return cartTax;
    }

    public void setCartTax(Double cartTax) {
        this.cartTax = cartTax;
    }

    public Double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(Double cartTotal) {
        this.cartTotal = cartTotal;
    }
}
