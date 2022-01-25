
package com.herbal.herbalfax.vendor.sellerproduct.productdetail.productdetailsmodel;

import com.google.gson.annotations.SerializedName;


public class StoreProduct {

    @SerializedName("idstore_products")
    private String idstoreProducts;
    @SerializedName("IsFav")
    private String isFav;
    @SerializedName("SP_Active")
    private String sPActive;
    @SerializedName("SPC_Title")
    private String sPCTitle;
    @SerializedName("SP_Category")
    private String sPCategory;
    @SerializedName("SP_CreatedAt")
    private String sPCreatedAt;
    @SerializedName("SP_CreatedOn")
    private String sPCreatedOn;
    @SerializedName("SP_Desc")
    private String sPDesc;
    @SerializedName("SP_IdStore")
    private String sPIdStore;
    @SerializedName("SP_Name")
    private String sPName;
    @SerializedName("SPP_Path")
    private Object sPPPath;
    @SerializedName("SP_PerKM")
    private String sPPerKM;
    @SerializedName("SP_Qty")
    private String sPQty;
    @SerializedName("SP_Rate")
    private String sPRate;
    @SerializedName("SP_Rating")
    private String sPRating;
    @SerializedName("SP_Reviews")
    private String sPReviews;
    @SerializedName("SP_ShippingCost")
    private String sPShippingCost;
    @SerializedName("SP_UpdatedAt")
    private String sPUpdatedAt;
    @SerializedName("SP_UpdatedOn")
    private String sPUpdatedOn;
    @SerializedName("StoreName")
    private String storeName;

    @SerializedName("SP_Expiry")
    private String sP_Expiry;
    @SerializedName("SP_Location")
    private String sP_Location;

    public String getsP_Expiry() {
        return sP_Expiry;
    }

    public void setsP_Expiry(String sP_Expiry) {
        this.sP_Expiry = sP_Expiry;
    }

    public String getsP_Location() {
        return sP_Location;
    }

    public void setsP_Location(String sP_Location) {
        this.sP_Location = sP_Location;
    }

    public String getsP_Long() {
        return sP_Long;
    }

    public void setsP_Long(String sP_Long) {
        this.sP_Long = sP_Long;
    }

    public String getsP_Lat() {
        return sP_Lat;
    }

    public void setsP_Lat(String sP_Lat) {
        this.sP_Lat = sP_Lat;
    }

    @SerializedName("SP_Long")
    private String sP_Long;

    @SerializedName("SP_Lat")
    private String sP_Lat;
    public String getIdstoreProducts() {
        return idstoreProducts;
    }

    public void setIdstoreProducts(String idstoreProducts) {
        this.idstoreProducts = idstoreProducts;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public String getSPActive() {
        return sPActive;
    }

    public void setSPActive(String sPActive) {
        this.sPActive = sPActive;
    }

    public String getSPCTitle() {
        return sPCTitle;
    }

    public void setSPCTitle(String sPCTitle) {
        this.sPCTitle = sPCTitle;
    }

    public String getSPCategory() {
        return sPCategory;
    }

    public void setSPCategory(String sPCategory) {
        this.sPCategory = sPCategory;
    }

    public String getSPCreatedAt() {
        return sPCreatedAt;
    }

    public void setSPCreatedAt(String sPCreatedAt) {
        this.sPCreatedAt = sPCreatedAt;
    }

    public String getSPCreatedOn() {
        return sPCreatedOn;
    }

    public void setSPCreatedOn(String sPCreatedOn) {
        this.sPCreatedOn = sPCreatedOn;
    }

    public String getSPDesc() {
        return sPDesc;
    }

    public void setSPDesc(String sPDesc) {
        this.sPDesc = sPDesc;
    }

    public String getSPIdStore() {
        return sPIdStore;
    }

    public void setSPIdStore(String sPIdStore) {
        this.sPIdStore = sPIdStore;
    }

    public String getSPName() {
        return sPName;
    }

    public void setSPName(String sPName) {
        this.sPName = sPName;
    }

    public Object getSPPPath() {
        return sPPPath;
    }

    public void setSPPPath(Object sPPPath) {
        this.sPPPath = sPPPath;
    }

    public String getSPPerKM() {
        return sPPerKM;
    }

    public void setSPPerKM(String sPPerKM) {
        this.sPPerKM = sPPerKM;
    }

    public String getSPQty() {
        return sPQty;
    }

    public void setSPQty(String sPQty) {
        this.sPQty = sPQty;
    }

    public String getSPRate() {
        return sPRate;
    }

    public void setSPRate(String sPRate) {
        this.sPRate = sPRate;
    }

    public String getSPRating() {
        return sPRating;
    }

    public void setSPRating(String sPRating) {
        this.sPRating = sPRating;
    }

    public String getSPReviews() {
        return sPReviews;
    }

    public void setSPReviews(String sPReviews) {
        this.sPReviews = sPReviews;
    }

    public String getSPShippingCost() {
        return sPShippingCost;
    }

    public void setSPShippingCost(String sPShippingCost) {
        this.sPShippingCost = sPShippingCost;
    }

    public String getSPUpdatedAt() {
        return sPUpdatedAt;
    }

    public void setSPUpdatedAt(String sPUpdatedAt) {
        this.sPUpdatedAt = sPUpdatedAt;
    }

    public String getSPUpdatedOn() {
        return sPUpdatedOn;
    }

    public void setSPUpdatedOn(String sPUpdatedOn) {
        this.sPUpdatedOn = sPUpdatedOn;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}
