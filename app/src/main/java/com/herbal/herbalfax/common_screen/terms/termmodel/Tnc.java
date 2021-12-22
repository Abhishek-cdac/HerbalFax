
package com.herbal.herbalfax.common_screen.terms.termmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Tnc {

    @Expose
    private String idtncs;
    @SerializedName("Tnc_Data")
    private String tncData;
    @SerializedName("Tnc_Title")
    private String tncTitle;

    public String getIdtncs() {
        return idtncs;
    }

    public void setIdtncs(String idtncs) {
        this.idtncs = idtncs;
    }

    public String getTncData() {
        return tncData;
    }

    public void setTncData(String tncData) {
        this.tncData = tncData;
    }

    public String getTncTitle() {
        return tncTitle;
    }

    public void setTncTitle(String tncTitle) {
        this.tncTitle = tncTitle;
    }

}
