
package com.herbal.herbalfax.vendor.storedetail.storedetailmodel;


import com.google.gson.annotations.SerializedName;


public class StorePhoto {

    @SerializedName("idstore_photos")
    private String idstorePhotos;
    @SerializedName("SPhoto_IdStore")
    private String sPhotoIdStore;
    @SerializedName("SPhoto_Path")
    private String sPhotoPath;

    public String getIdstorePhotos() {
        return idstorePhotos;
    }

    public void setIdstorePhotos(String idstorePhotos) {
        this.idstorePhotos = idstorePhotos;
    }

    public String getSPhotoIdStore() {
        return sPhotoIdStore;
    }

    public void setSPhotoIdStore(String sPhotoIdStore) {
        this.sPhotoIdStore = sPhotoIdStore;
    }

    public String getSPhotoPath() {
        return sPhotoPath;
    }

    public void setSPhotoPath(String sPhotoPath) {
        this.sPhotoPath = sPhotoPath;
    }

}
