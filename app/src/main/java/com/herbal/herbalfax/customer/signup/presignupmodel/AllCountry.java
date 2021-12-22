
package com.herbal.herbalfax.customer.signup.presignupmodel;

import com.google.gson.annotations.SerializedName;

public class AllCountry {

    @SerializedName("CounCode")
    private String mCounCode;
    @SerializedName("CounName")
    private String mCounName;
    @SerializedName("idcountries")
    private String mIdcountries;

    public String getCounCode() {
        return mCounCode;
    }

    public void setCounCode(String counCode) {
        mCounCode = counCode;
    }

    public String getCounName() {
        return mCounName;
    }

    public void setCounName(String counName) {
        mCounName = counName;
    }

    public String getIdcountries() {
        return mIdcountries;
    }

    public void setIdcountries(String idcountries) {
        mIdcountries = idcountries;
    }

}
