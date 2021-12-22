
package com.herbal.herbalfax.commonmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CommonResponse {

    @Expose
    private Data data;

//    public Errors getErrors() {
//        return errors;
//    }
//
//    public void setErrors(Errors errors) {
//        this.errors = errors;
//    }

//    @Expose
//    private Errors  errors;


    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @Expose
    private String message;
    @Expose
    private Long status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
