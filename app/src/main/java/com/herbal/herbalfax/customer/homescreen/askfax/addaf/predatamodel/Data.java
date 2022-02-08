
package com.herbal.herbalfax.customer.homescreen.askfax.addaf.predatamodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("af_privacytypes")
    private List<AfPrivacytype> afPrivacytypes;

    public List<AfPrivacytype> getAfPrivacytypes() {
        return afPrivacytypes;
    }

    public void setAfPrivacytypes(List<AfPrivacytype> afPrivacytypes) {
        this.afPrivacytypes = afPrivacytypes;
    }

}
