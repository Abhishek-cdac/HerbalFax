package com.herbal.herbalfax.vendor.storedetail.storedetailmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreRatings {


    @SerializedName("idstore_ratings")
    @Expose
    private String idstoreRatings;
    @SerializedName("SRate_IdStore")
    @Expose
    private String sRateIdStore;

    public String getIdstoreRatings() {
        return idstoreRatings;
    }

    public void setIdstoreRatings(String idstoreRatings) {
        this.idstoreRatings = idstoreRatings;
    }

    public String getsRateIdStore() {
        return sRateIdStore;
    }

    public void setsRateIdStore(String sRateIdStore) {
        this.sRateIdStore = sRateIdStore;
    }

    public String getsRateIdUser() {
        return sRateIdUser;
    }

    public void setsRateIdUser(String sRateIdUser) {
        this.sRateIdUser = sRateIdUser;
    }

    public String getsRateStar() {
        return sRateStar;
    }

    public void setsRateStar(String sRateStar) {
        this.sRateStar = sRateStar;
    }

    public String getsRateReview() {
        return sRateReview;
    }

    public void setsRateReview(String sRateReview) {
        this.sRateReview = sRateReview;
    }

    public String getsRateOn() {
        return sRateOn;
    }

    public void setsRateOn(String sRateOn) {
        this.sRateOn = sRateOn;
    }

    public String getsRateAt() {
        return sRateAt;
    }

    public void setsRateAt(String sRateAt) {
        this.sRateAt = sRateAt;
    }

    public String getStoreRatingTime() {
        return storeRatingTime;
    }

    public void setStoreRatingTime(String storeRatingTime) {
        this.storeRatingTime = storeRatingTime;
    }

    public String getuFullName() {
        return uFullName;
    }

    public void setuFullName(String uFullName) {
        this.uFullName = uFullName;
    }

    public String getuProPic() {
        return uProPic;
    }

    public void setuProPic(String uProPic) {
        this.uProPic = uProPic;
    }

    @SerializedName("SRate_IdUser")
    @Expose
    private String sRateIdUser;
    @SerializedName("SRate_Star")
    @Expose
    private String sRateStar;
    @SerializedName("SRate_Review")
    @Expose
    private String sRateReview;
    @SerializedName("SRate_On")
    @Expose
    private String sRateOn;
    @SerializedName("SRate_At")
    @Expose
    private String sRateAt;
    @SerializedName("StoreRatingTime")
    @Expose
    private String storeRatingTime;
    @SerializedName("UFullName")
    @Expose
    private String uFullName;
    @SerializedName("UProPic")
    @Expose
    private String uProPic;
}
