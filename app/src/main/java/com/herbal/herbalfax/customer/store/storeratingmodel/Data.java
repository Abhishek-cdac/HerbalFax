
package com.herbal.herbalfax.customer.store.storeratingmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("store_ratings")
    private List<StoreRating> storeRatings;
    @SerializedName("store_ratings_chart")
    private List<StoreRatingsChart> storeRatingsChart;
    @SerializedName("store_ratings_count")
    private Long storeRatingsCount;

    public List<StoreRating> getStoreRatings() {
        return storeRatings;
    }

    public void setStoreRatings(List<StoreRating> storeRatings) {
        this.storeRatings = storeRatings;
    }

    public List<StoreRatingsChart> getStoreRatingsChart() {
        return storeRatingsChart;
    }

    public void setStoreRatingsChart(List<StoreRatingsChart> storeRatingsChart) {
        this.storeRatingsChart = storeRatingsChart;
    }

    public Long getStoreRatingsCount() {
        return storeRatingsCount;
    }

    public void setStoreRatingsCount(Long storeRatingsCount) {
        this.storeRatingsCount = storeRatingsCount;
    }

}
