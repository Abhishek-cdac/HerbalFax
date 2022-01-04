
package com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("address_types")
    private List<AddressType> addressTypes;
    @SerializedName("user_addresses")
    private List<UserAddress> userAddresses;

    public List<AddressType> getAddressTypes() {
        return addressTypes;
    }

    public void setAddressTypes(List<AddressType> addressTypes) {
        this.addressTypes = addressTypes;
    }

    public List<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<UserAddress> userAddresses) {
        this.userAddresses = userAddresses;
    }

}
