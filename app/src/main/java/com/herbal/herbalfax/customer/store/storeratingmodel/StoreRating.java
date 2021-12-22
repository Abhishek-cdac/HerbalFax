
package com.herbal.herbalfax.customer.store.storeratingmodel;


import com.google.gson.annotations.SerializedName;

public class StoreRating {

    @SerializedName("idstore_ratings")
    private String idstoreRatings;
    @SerializedName("SRate_At")
    private String sRateAt;
    @SerializedName("SRate_IdStore")
    private String sRateIdStore;
    @SerializedName("SRate_IdUser")
    private String sRateIdUser;
    @SerializedName("SRate_On")
    private String sRateOn;
    @SerializedName("SRate_Review")
    private String sRateReview;
    @SerializedName("SRate_Star")
    private String sRateStar;
    @SerializedName("StoreRatingTime")
    private String storeRatingTime;
    @SerializedName("UFullName")
    private String uFullName;
    @SerializedName("UProPic")
    private String uProPic;

    public String getIdstoreRatings() {
        return idstoreRatings;
    }

    public void setIdstoreRatings(String idstoreRatings) {
        this.idstoreRatings = idstoreRatings;
    }

    public String getSRateAt() {
        return sRateAt;
    }

    public void setSRateAt(String sRateAt) {
        this.sRateAt = sRateAt;
    }

    public String getSRateIdStore() {
        return sRateIdStore;
    }

    public void setSRateIdStore(String sRateIdStore) {
        this.sRateIdStore = sRateIdStore;
    }

    public String getSRateIdUser() {
        return sRateIdUser;
    }

    public void setSRateIdUser(String sRateIdUser) {
        this.sRateIdUser = sRateIdUser;
    }

    public String getSRateOn() {
        return sRateOn;
    }

    public void setSRateOn(String sRateOn) {
        this.sRateOn = sRateOn;
    }

    public String getSRateReview() {
        return sRateReview;
    }

    public void setSRateReview(String sRateReview) {
        this.sRateReview = sRateReview;
    }

    public String getSRateStar() {
        return sRateStar;
    }

    public void setSRateStar(String sRateStar) {
        this.sRateStar = sRateStar;
    }

    public String getStoreRatingTime() {
        return storeRatingTime;
    }

    public void setStoreRatingTime(String storeRatingTime) {
        this.storeRatingTime = storeRatingTime;
    }

    public String getUFullName() {
        return uFullName;
    }

    public void setUFullName(String uFullName) {
        this.uFullName = uFullName;
    }

    public String getUProPic() {
        return uProPic;
    }

    public void setUProPic(String uProPic) {
        this.uProPic = uProPic;
    }

}
