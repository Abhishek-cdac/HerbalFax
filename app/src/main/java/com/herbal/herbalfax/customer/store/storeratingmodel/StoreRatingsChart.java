
package com.herbal.herbalfax.customer.store.storeratingmodel;


import com.google.gson.annotations.SerializedName;

public class StoreRatingsChart {

    @SerializedName("rate_count")
    private int rateCount;
    @SerializedName("SRate_Star")
    private String sRateStar;

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public String getSRateStar() {
        return sRateStar;
    }

    public void setSRateStar(String sRateStar) {
        this.sRateStar = sRateStar;
    }

}
