
package com.herbal.herbalfax.customer.selectInterest.getintrestmodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("all_interests")
    private List<AllInterest> allInterests;

    public List<AllInterest> getAllInterests() {
        return allInterests;
    }

    public void setAllInterests(List<AllInterest> allInterests) {
        this.allInterests = allInterests;
    }

}
