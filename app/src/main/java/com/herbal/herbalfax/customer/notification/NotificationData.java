package com.herbal.herbalfax.customer.notification;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
    public String getmPatternID() {
        return mPatternID;
    }

    public void setmPatternID(String mPatternID) {
        this.mPatternID = mPatternID;
    }

    @SerializedName("patternID")
    private String mPatternID;

    public NotificationData(String mPatternID) {
        this.mPatternID = mPatternID;
    }
}
