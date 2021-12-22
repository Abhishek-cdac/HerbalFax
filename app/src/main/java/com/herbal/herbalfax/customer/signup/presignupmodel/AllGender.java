
package com.herbal.herbalfax.customer.signup.presignupmodel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AllGender {

    @SerializedName("GenTitle")
    private String mGenTitle;
    @SerializedName("idgenders")
    private String mIdgenders;

    public String getGenTitle() {
        return mGenTitle;
    }

    public void setGenTitle(String genTitle) {
        mGenTitle = genTitle;
    }

    public String getIdgenders() {
        return mIdgenders;
    }

    public void setIdgenders(String idgenders) {
        mIdgenders = idgenders;
    }

}
