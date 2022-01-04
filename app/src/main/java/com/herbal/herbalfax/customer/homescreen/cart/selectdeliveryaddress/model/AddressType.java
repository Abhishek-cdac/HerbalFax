
package com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.model;


import com.google.gson.annotations.SerializedName;

public class AddressType {

    @SerializedName("AddType_Title")
    private String addTypeTitle;
    @SerializedName("idaddress_types")
    private String idaddressTypes;

    public String getAddTypeTitle() {
        return addTypeTitle;
    }

    public void setAddTypeTitle(String addTypeTitle) {
        this.addTypeTitle = addTypeTitle;
    }

    public String getIdaddressTypes() {
        return idaddressTypes;
    }

    public void setIdaddressTypes(String idaddressTypes) {
        this.idaddressTypes = idaddressTypes;
    }

}
