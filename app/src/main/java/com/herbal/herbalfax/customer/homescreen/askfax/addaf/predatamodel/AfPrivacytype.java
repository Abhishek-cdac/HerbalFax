
package com.herbal.herbalfax.customer.homescreen.askfax.addaf.predatamodel;


import com.google.gson.annotations.SerializedName;


public class AfPrivacytype {

    @SerializedName("AFPT_Title")
    private String aFPTTitle;
    @SerializedName("idaf_privacytypes")
    private String idafPrivacytypes;

    public String getAFPTTitle() {
        return aFPTTitle;
    }

    public void setAFPTTitle(String aFPTTitle) {
        this.aFPTTitle = aFPTTitle;
    }

    public String getIdafPrivacytypes() {
        return idafPrivacytypes;
    }

    public void setIdafPrivacytypes(String idafPrivacytypes) {
        this.idafPrivacytypes = idafPrivacytypes;
    }

}
