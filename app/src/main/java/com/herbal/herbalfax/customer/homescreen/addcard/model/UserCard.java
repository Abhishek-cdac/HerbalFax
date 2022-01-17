
package com.herbal.herbalfax.customer.homescreen.addcard.model;


import com.google.gson.annotations.SerializedName;


public class UserCard {

    @SerializedName("CT_Title")
    private String cTTitle;
    @SerializedName("iduser_cards")
    private String iduserCards;
    @SerializedName("UCards_CVV")
    private String uCardsCVV;
    @SerializedName("UCards_ExpMonth")
    private String uCardsExpMonth;
    @SerializedName("UCards_ExpYear")
    private String uCardsExpYear;
    @SerializedName("UCards_HolderName")
    private String uCardsHolderName;
    @SerializedName("UCards_IdUser")
    private String uCardsIdUser;
    @SerializedName("UCards_Number")
    private String uCardsNumber;
    @SerializedName("UCards_Type")
    private String uCardsType;

    public String getCTTitle() {
        return cTTitle;
    }

    public void setCTTitle(String cTTitle) {
        this.cTTitle = cTTitle;
    }

    public String getIduserCards() {
        return iduserCards;
    }

    public void setIduserCards(String iduserCards) {
        this.iduserCards = iduserCards;
    }

    public String getUCardsCVV() {
        return uCardsCVV;
    }

    public void setUCardsCVV(String uCardsCVV) {
        this.uCardsCVV = uCardsCVV;
    }

    public String getUCardsExpMonth() {
        return uCardsExpMonth;
    }

    public void setUCardsExpMonth(String uCardsExpMonth) {
        this.uCardsExpMonth = uCardsExpMonth;
    }

    public String getUCardsExpYear() {
        return uCardsExpYear;
    }

    public void setUCardsExpYear(String uCardsExpYear) {
        this.uCardsExpYear = uCardsExpYear;
    }

    public String getUCardsHolderName() {
        return uCardsHolderName;
    }

    public void setUCardsHolderName(String uCardsHolderName) {
        this.uCardsHolderName = uCardsHolderName;
    }

    public String getUCardsIdUser() {
        return uCardsIdUser;
    }

    public void setUCardsIdUser(String uCardsIdUser) {
        this.uCardsIdUser = uCardsIdUser;
    }

    public String getUCardsNumber() {
        return uCardsNumber;
    }

    public void setUCardsNumber(String uCardsNumber) {
        this.uCardsNumber = uCardsNumber;
    }

    public String getUCardsType() {
        return uCardsType;
    }

    public void setUCardsType(String uCardsType) {
        this.uCardsType = uCardsType;
    }

}
