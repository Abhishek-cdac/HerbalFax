
package com.herbal.herbalfax.common_screen.landingpage.events.eventdetailsmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class EventListResponse {

    @SerializedName("data")
    private Data mData;
    @SerializedName("errors")
    private List<Object> mErrors;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public List<Object> getErrors() {
        return mErrors;
    }

    public void setErrors(List<Object> errors) {
        mErrors = errors;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
