package com.herbal.herbalfax.customer.homescreen.products.toprated.beancmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopVendorListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Vendor {

        @SerializedName("idusers")
        @Expose
        private String idusers;
        @SerializedName("UserType")
        @Expose
        private String userType;
        @SerializedName("UFullName")
        @Expose
        private String uFullName;
        @SerializedName("UDOB")
        @Expose
        private String udob;
        @SerializedName("UGender")
        @Expose
        private String uGender;
        @SerializedName("UProfession")
        @Expose
        private String uProfession;
        @SerializedName("UProfessionOther")
        @Expose
        private String uProfessionOther;
        @SerializedName("UCity")
        @Expose
        private String uCity;
        @SerializedName("UZipCode")
        @Expose
        private String uZipCode;
        @SerializedName("UEmailAddress")
        @Expose
        private String uEmailAddress;
        @SerializedName("UEmailVerified")
        @Expose
        private String uEmailVerified;
        @SerializedName("UMobNoCode")
        @Expose
        private String uMobNoCode;
        @SerializedName("UMobNo")
        @Expose
        private String uMobNo;
        @SerializedName("UPassword")
        @Expose
        private String uPassword;
        @SerializedName("UProPic")
        @Expose
        private String uProPic;
        @SerializedName("UIAgree")
        @Expose
        private String uIAgree;
        @SerializedName("UVendorStatus")
        @Expose
        private String uVendorStatus;
        @SerializedName("UCurOTP")
        @Expose
        private String uCurOTP;
        @SerializedName("UActive")
        @Expose
        private String uActive;
        @SerializedName("UDevType")
        @Expose
        private String uDevType;
        @SerializedName("UDevPlatform")
        @Expose
        private String uDevPlatform;
        @SerializedName("UDevUID")
        @Expose
        private String uDevUID;
        @SerializedName("UDevToken")
        @Expose
        private String uDevToken;
        @SerializedName("URatings")
        @Expose
        private String uRatings;
        @SerializedName("UFacebookLink")
        @Expose
        private Object uFacebookLink;
        @SerializedName("UGoogleLink")
        @Expose
        private Object uGoogleLink;
        @SerializedName("UTwitterLink")
        @Expose
        private Object uTwitterLink;
        @SerializedName("UInstagramLink")
        @Expose
        private Object uInstagramLink;
        @SerializedName("UCreatedOn")
        @Expose
        private String uCreatedOn;
        @SerializedName("UCreatedAt")
        @Expose
        private String uCreatedAt;
        @SerializedName("UUpdatedOn")
        @Expose
        private Object uUpdatedOn;
        @SerializedName("UUpdatedAt")
        @Expose
        private Object uUpdatedAt;
        @SerializedName("stores")
        @Expose
        private List<Store> stores = null;

        public String getIdusers() {
            return idusers;
        }

        public void setIdusers(String idusers) {
            this.idusers = idusers;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUFullName() {
            return uFullName;
        }

        public void setUFullName(String uFullName) {
            this.uFullName = uFullName;
        }

        public String getUdob() {
            return udob;
        }

        public void setUdob(String udob) {
            this.udob = udob;
        }

        public String getUGender() {
            return uGender;
        }

        public void setUGender(String uGender) {
            this.uGender = uGender;
        }

        public String getUProfession() {
            return uProfession;
        }

        public void setUProfession(String uProfession) {
            this.uProfession = uProfession;
        }

        public String getUProfessionOther() {
            return uProfessionOther;
        }

        public void setUProfessionOther(String uProfessionOther) {
            this.uProfessionOther = uProfessionOther;
        }

        public String getUCity() {
            return uCity;
        }

        public void setUCity(String uCity) {
            this.uCity = uCity;
        }

        public String getUZipCode() {
            return uZipCode;
        }

        public void setUZipCode(String uZipCode) {
            this.uZipCode = uZipCode;
        }

        public String getUEmailAddress() {
            return uEmailAddress;
        }

        public void setUEmailAddress(String uEmailAddress) {
            this.uEmailAddress = uEmailAddress;
        }

        public String getUEmailVerified() {
            return uEmailVerified;
        }

        public void setUEmailVerified(String uEmailVerified) {
            this.uEmailVerified = uEmailVerified;
        }

        public String getUMobNoCode() {
            return uMobNoCode;
        }

        public void setUMobNoCode(String uMobNoCode) {
            this.uMobNoCode = uMobNoCode;
        }

        public String getUMobNo() {
            return uMobNo;
        }

        public void setUMobNo(String uMobNo) {
            this.uMobNo = uMobNo;
        }

        public String getUPassword() {
            return uPassword;
        }

        public void setUPassword(String uPassword) {
            this.uPassword = uPassword;
        }

        public String getUProPic() {
            return uProPic;
        }

        public void setUProPic(String uProPic) {
            this.uProPic = uProPic;
        }

        public String getUIAgree() {
            return uIAgree;
        }

        public void setUIAgree(String uIAgree) {
            this.uIAgree = uIAgree;
        }

        public String getUVendorStatus() {
            return uVendorStatus;
        }

        public void setUVendorStatus(String uVendorStatus) {
            this.uVendorStatus = uVendorStatus;
        }

        public String getUCurOTP() {
            return uCurOTP;
        }

        public void setUCurOTP(String uCurOTP) {
            this.uCurOTP = uCurOTP;
        }

        public String getUActive() {
            return uActive;
        }

        public void setUActive(String uActive) {
            this.uActive = uActive;
        }

        public String getUDevType() {
            return uDevType;
        }

        public void setUDevType(String uDevType) {
            this.uDevType = uDevType;
        }

        public String getUDevPlatform() {
            return uDevPlatform;
        }

        public void setUDevPlatform(String uDevPlatform) {
            this.uDevPlatform = uDevPlatform;
        }

        public String getUDevUID() {
            return uDevUID;
        }

        public void setUDevUID(String uDevUID) {
            this.uDevUID = uDevUID;
        }

        public String getUDevToken() {
            return uDevToken;
        }

        public void setUDevToken(String uDevToken) {
            this.uDevToken = uDevToken;
        }

        public String getURatings() {
            return uRatings;
        }

        public void setURatings(String uRatings) {
            this.uRatings = uRatings;
        }

        public Object getUFacebookLink() {
            return uFacebookLink;
        }

        public void setUFacebookLink(Object uFacebookLink) {
            this.uFacebookLink = uFacebookLink;
        }

        public Object getUGoogleLink() {
            return uGoogleLink;
        }

        public void setUGoogleLink(Object uGoogleLink) {
            this.uGoogleLink = uGoogleLink;
        }

        public Object getUTwitterLink() {
            return uTwitterLink;
        }

        public void setUTwitterLink(Object uTwitterLink) {
            this.uTwitterLink = uTwitterLink;
        }

        public Object getUInstagramLink() {
            return uInstagramLink;
        }

        public void setUInstagramLink(Object uInstagramLink) {
            this.uInstagramLink = uInstagramLink;
        }

        public String getUCreatedOn() {
            return uCreatedOn;
        }

        public void setUCreatedOn(String uCreatedOn) {
            this.uCreatedOn = uCreatedOn;
        }

        public String getUCreatedAt() {
            return uCreatedAt;
        }

        public void setUCreatedAt(String uCreatedAt) {
            this.uCreatedAt = uCreatedAt;
        }

        public Object getUUpdatedOn() {
            return uUpdatedOn;
        }

        public void setUUpdatedOn(Object uUpdatedOn) {
            this.uUpdatedOn = uUpdatedOn;
        }

        public Object getUUpdatedAt() {
            return uUpdatedAt;
        }

        public void setUUpdatedAt(Object uUpdatedAt) {
            this.uUpdatedAt = uUpdatedAt;
        }

        public List<Store> getStores() {
            return stores;
        }

        public void setStores(List<Store> stores) {
            this.stores = stores;
        }

    }


    public class Data {

        @SerializedName("vendors_count")
        @Expose
        private Integer vendorsCount;
        @SerializedName("vendors")
        @Expose
        private List<Vendor> vendors = null;

        public Integer getVendorsCount() {
            return vendorsCount;
        }

        public void setVendorsCount(Integer vendorsCount) {
            this.vendorsCount = vendorsCount;
        }

        public List<Vendor> getVendors() {
            return vendors;
        }

        public void setVendors(List<Vendor> vendors) {
            this.vendors = vendors;
        }

    }


    public class Store {

        @SerializedName("idstores")
        @Expose
        private String idstores;
        @SerializedName("StoreIdVendor")
        @Expose
        private String storeIdVendor;
        @SerializedName("StoreName")
        @Expose
        private String storeName;
        @SerializedName("StoreCategory")
        @Expose
        private String storeCategory;
        @SerializedName("StoreLocation")
        @Expose
        private String storeLocation;
        @SerializedName("StoreLat")
        @Expose
        private String storeLat;
        @SerializedName("StoreLong")
        @Expose
        private String storeLong;
        @SerializedName("StoreLicNo")
        @Expose
        private String storeLicNo;
        @SerializedName("StoreDesc")
        @Expose
        private String storeDesc;
        @SerializedName("StoreContactName")
        @Expose
        private String storeContactName;
        @SerializedName("StoreContactDesignation")
        @Expose
        private String storeContactDesignation;
        @SerializedName("StorePhone")
        @Expose
        private String storePhone;
        @SerializedName("StoreEmail")
        @Expose
        private String storeEmail;
        @SerializedName("StoreDeliveryRange")
        @Expose
        private String storeDeliveryRange;
        @SerializedName("StoreLogo")
        @Expose
        private String storeLogo;
        @SerializedName("StoreLicCertificate")
        @Expose
        private String storeLicCertificate;
        @SerializedName("StoreRating")
        @Expose
        private String storeRating;
        @SerializedName("StoreCheckIns")
        @Expose
        private String storeCheckIns;
        @SerializedName("StoreFavs")
        @Expose
        private String storeFavs;
        @SerializedName("StoreViews")
        @Expose
        private String storeViews;
        @SerializedName("StoreCreatedOn")
        @Expose
        private String storeCreatedOn;
        @SerializedName("StoreCreatedAt")
        @Expose
        private String storeCreatedAt;
        @SerializedName("StoreActive")
        @Expose
        private String storeActive;
        @SerializedName("StoreShippingCost")
        @Expose
        private Object storeShippingCost;
        @SerializedName("StorePerKM")
        @Expose
        private Object storePerKM;
        @SerializedName("SCat_Title")
        @Expose
        private String sCatTitle;

        public String getIdstores() {
            return idstores;
        }

        public void setIdstores(String idstores) {
            this.idstores = idstores;
        }

        public String getStoreIdVendor() {
            return storeIdVendor;
        }

        public void setStoreIdVendor(String storeIdVendor) {
            this.storeIdVendor = storeIdVendor;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreCategory() {
            return storeCategory;
        }

        public void setStoreCategory(String storeCategory) {
            this.storeCategory = storeCategory;
        }

        public String getStoreLocation() {
            return storeLocation;
        }

        public void setStoreLocation(String storeLocation) {
            this.storeLocation = storeLocation;
        }

        public String getStoreLat() {
            return storeLat;
        }

        public void setStoreLat(String storeLat) {
            this.storeLat = storeLat;
        }

        public String getStoreLong() {
            return storeLong;
        }

        public void setStoreLong(String storeLong) {
            this.storeLong = storeLong;
        }

        public String getStoreLicNo() {
            return storeLicNo;
        }

        public void setStoreLicNo(String storeLicNo) {
            this.storeLicNo = storeLicNo;
        }

        public String getStoreDesc() {
            return storeDesc;
        }

        public void setStoreDesc(String storeDesc) {
            this.storeDesc = storeDesc;
        }

        public String getStoreContactName() {
            return storeContactName;
        }

        public void setStoreContactName(String storeContactName) {
            this.storeContactName = storeContactName;
        }

        public String getStoreContactDesignation() {
            return storeContactDesignation;
        }

        public void setStoreContactDesignation(String storeContactDesignation) {
            this.storeContactDesignation = storeContactDesignation;
        }

        public String getStorePhone() {
            return storePhone;
        }

        public void setStorePhone(String storePhone) {
            this.storePhone = storePhone;
        }

        public String getStoreEmail() {
            return storeEmail;
        }

        public void setStoreEmail(String storeEmail) {
            this.storeEmail = storeEmail;
        }

        public String getStoreDeliveryRange() {
            return storeDeliveryRange;
        }

        public void setStoreDeliveryRange(String storeDeliveryRange) {
            this.storeDeliveryRange = storeDeliveryRange;
        }

        public String getStoreLogo() {
            return storeLogo;
        }

        public void setStoreLogo(String storeLogo) {
            this.storeLogo = storeLogo;
        }

        public String getStoreLicCertificate() {
            return storeLicCertificate;
        }

        public void setStoreLicCertificate(String storeLicCertificate) {
            this.storeLicCertificate = storeLicCertificate;
        }

        public String getStoreRating() {
            return storeRating;
        }

        public void setStoreRating(String storeRating) {
            this.storeRating = storeRating;
        }

        public String getStoreCheckIns() {
            return storeCheckIns;
        }

        public void setStoreCheckIns(String storeCheckIns) {
            this.storeCheckIns = storeCheckIns;
        }

        public String getStoreFavs() {
            return storeFavs;
        }

        public void setStoreFavs(String storeFavs) {
            this.storeFavs = storeFavs;
        }

        public String getStoreViews() {
            return storeViews;
        }

        public void setStoreViews(String storeViews) {
            this.storeViews = storeViews;
        }

        public String getStoreCreatedOn() {
            return storeCreatedOn;
        }

        public void setStoreCreatedOn(String storeCreatedOn) {
            this.storeCreatedOn = storeCreatedOn;
        }

        public String getStoreCreatedAt() {
            return storeCreatedAt;
        }

        public void setStoreCreatedAt(String storeCreatedAt) {
            this.storeCreatedAt = storeCreatedAt;
        }

        public String getStoreActive() {
            return storeActive;
        }

        public void setStoreActive(String storeActive) {
            this.storeActive = storeActive;
        }

        public Object getStoreShippingCost() {
            return storeShippingCost;
        }

        public void setStoreShippingCost(Object storeShippingCost) {
            this.storeShippingCost = storeShippingCost;
        }

        public Object getStorePerKM() {
            return storePerKM;
        }

        public void setStorePerKM(Object storePerKM) {
            this.storePerKM = storePerKM;
        }

        public String getSCatTitle() {
            return sCatTitle;
        }

        public void setSCatTitle(String sCatTitle) {
            this.sCatTitle = sCatTitle;
        }

    }

}
