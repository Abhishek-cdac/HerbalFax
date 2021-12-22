
package com.herbal.herbalfax.vendor.storedetail.storedetailmodel;


import com.google.gson.annotations.SerializedName;


public class StoreBusinessHr {

    @SerializedName("idstore_business_hrs")
    private String idstoreBusinessHrs;
    @SerializedName("SBH_DOW")
    private String sBHDOW;
    @SerializedName("SBH_EndTime")
    private String sBHEndTime;
    @SerializedName("SBH_IdStore")
    private String sBHIdStore;
    @SerializedName("SBH_StartTime")
    private String sBHStartTime;
    @SerializedName("SBH_Status")
    private String sBHStatus;

    public String getIdstoreBusinessHrs() {
        return idstoreBusinessHrs;
    }

    public void setIdstoreBusinessHrs(String idstoreBusinessHrs) {
        this.idstoreBusinessHrs = idstoreBusinessHrs;
    }

    public String getSBHDOW() {
        return sBHDOW;
    }

    public void setSBHDOW(String sBHDOW) {
        this.sBHDOW = sBHDOW;
    }

    public String getSBHEndTime() {
        return sBHEndTime;
    }

    public void setSBHEndTime(String sBHEndTime) {
        this.sBHEndTime = sBHEndTime;
    }

    public String getSBHIdStore() {
        return sBHIdStore;
    }

    public void setSBHIdStore(String sBHIdStore) {
        this.sBHIdStore = sBHIdStore;
    }

    public String getSBHStartTime() {
        return sBHStartTime;
    }

    public void setSBHStartTime(String sBHStartTime) {
        this.sBHStartTime = sBHStartTime;
    }

    public String getSBHStatus() {
        return sBHStatus;
    }

    public void setSBHStatus(String sBHStatus) {
        this.sBHStatus = sBHStatus;
    }

}
