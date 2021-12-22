
package com.herbal.herbalfax.customer.selectInterest.getintrestmodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AllInterest {

    @Expose
    private String idinterests;
    @SerializedName("Int_Img")
    private String intImg;
    @SerializedName("Int_Title")
    private String intTitle;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;



    public String getIdinterests() {
        return idinterests;
    }

    public void setIdinterests(String idinterests) {
        this.idinterests = idinterests;
    }

    public String getIntImg() {
        return intImg;
    }

    public void setIntImg(String intImg) {
        this.intImg = intImg;
    }

    public String getIntTitle() {
        return intTitle;
    }

    public void setIntTitle(String intTitle) {
        this.intTitle = intTitle;
    }

}
