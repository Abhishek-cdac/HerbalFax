
package com.herbal.herbalfax.customer.signup.presignupmodel;

import com.google.gson.annotations.SerializedName;

public class AllProfession {

    @SerializedName("idprofessions")
    private String mIdprofessions;
    @SerializedName("ProfActive")
    private String mProfActive;
    @SerializedName("ProfTitle")
    private String mProfTitle;

    public String getIdprofessions() {
        return mIdprofessions;
    }

    public void setIdprofessions(String idprofessions) {
        mIdprofessions = idprofessions;
    }

    public String getProfActive() {
        return mProfActive;
    }

    public void setProfActive(String profActive) {
        mProfActive = profActive;
    }

    public String getProfTitle() {
        return mProfTitle;
    }

    public void setProfTitle(String profTitle) {
        mProfTitle = profTitle;
    }

}
