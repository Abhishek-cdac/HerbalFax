package com.herbal.herbalfax.vendor.store;

public class NewStore {

    private String storeFullName;
    private String storeLocation;
    private String storeLicenseNo;
    private String storeDesc;
    private String storeDistanceRange;

    public NewStore(String storeFullName, String storeLocation, String storeLicenseNo, String storeDesc, String storeDistanceRange) {
        this.storeFullName = storeFullName;

        this.storeLocation = storeLocation;
        this.storeLicenseNo = storeLicenseNo;
        this.storeDesc = storeDesc;
        this.storeDistanceRange = storeDistanceRange;
    }

    public String getStoreFullName() {
        return storeFullName;
    }

    public void setStoreFullName(String storeFullName) {
        this.storeFullName = storeFullName;
    }



    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStoreLicenseNo() {
        return storeLicenseNo;
    }

    public void setStoreLicenseNo(String storeLicenseNo) {
        this.storeLicenseNo = storeLicenseNo;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getStoreDistanceRange() {
        return storeDistanceRange;
    }

    public void setStoreDistanceRange(String storeDistanceRange) {
        this.storeDistanceRange = storeDistanceRange;
    }


}
