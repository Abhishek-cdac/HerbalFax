
package com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartList {

    @SerializedName("Cart_At")
    private String cartAt;
    @SerializedName("Cart_IdProduct")
    private String cartIdProduct;
    @SerializedName("Cart_IdStore")
    private String cartIdStore;
    @SerializedName("Cart_IdUser")
    private String cartIdUser;
    @SerializedName("Cart_On")
    private String cartOn;
    @SerializedName("Cart_Qty")
    private String cartQty;
    @SerializedName("Cart_Rate")
    private String cartRate;
    @SerializedName("Cart_SubTotal")
    private String cartSubTotal;
    @SerializedName("Cart_Tax")
    private String cartTax;
    @SerializedName("Cart_TaxPercent")
    private String cartTaxPercent;
    @SerializedName("Cart_Total")
    private String cartTotal;
    @Expose
    private String idcart;
    @SerializedName("SPC_Title")
    private String sPCTitle;
    @SerializedName("SP_Name")
    private String sPName;
    @SerializedName("SPP_Path")
    private String sPPPath;

    public String getCartAt() {
        return cartAt;
    }

    public void setCartAt(String cartAt) {
        this.cartAt = cartAt;
    }

    public String getCartIdProduct() {
        return cartIdProduct;
    }

    public void setCartIdProduct(String cartIdProduct) {
        this.cartIdProduct = cartIdProduct;
    }

    public String getCartIdStore() {
        return cartIdStore;
    }

    public void setCartIdStore(String cartIdStore) {
        this.cartIdStore = cartIdStore;
    }

    public String getCartIdUser() {
        return cartIdUser;
    }

    public void setCartIdUser(String cartIdUser) {
        this.cartIdUser = cartIdUser;
    }

    public String getCartOn() {
        return cartOn;
    }

    public void setCartOn(String cartOn) {
        this.cartOn = cartOn;
    }

    public String getCartQty() {
        return cartQty;
    }

    public void setCartQty(String cartQty) {
        this.cartQty = cartQty;
    }

    public String getCartRate() {
        return cartRate;
    }

    public void setCartRate(String cartRate) {
        this.cartRate = cartRate;
    }

    public String getCartSubTotal() {
        return cartSubTotal;
    }

    public void setCartSubTotal(String cartSubTotal) {
        this.cartSubTotal = cartSubTotal;
    }

    public String getCartTax() {
        return cartTax;
    }

    public void setCartTax(String cartTax) {
        this.cartTax = cartTax;
    }

    public String getCartTaxPercent() {
        return cartTaxPercent;
    }

    public void setCartTaxPercent(String cartTaxPercent) {
        this.cartTaxPercent = cartTaxPercent;
    }

    public String getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(String cartTotal) {
        this.cartTotal = cartTotal;
    }

    public String getIdcart() {
        return idcart;
    }

    public void setIdcart(String idcart) {
        this.idcart = idcart;
    }

    public String getSPCTitle() {
        return sPCTitle;
    }

    public void setSPCTitle(String sPCTitle) {
        this.sPCTitle = sPCTitle;
    }

    public String getSPName() {
        return sPName;
    }

    public void setSPName(String sPName) {
        this.sPName = sPName;
    }

    public String getSPPPath() {
        return sPPPath;
    }

    public void setSPPPath(String sPPPath) {
        this.sPPPath = sPPPath;
    }

}
