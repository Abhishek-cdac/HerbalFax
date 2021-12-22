
package com.herbal.herbalfax.common_screen.verify_email_after_signup.otpverified;

import java.util.List;
import com.google.gson.annotations.Expose;

public class SignUpOtpVerifyResponse {

    @Expose
    private Data data;
    @Expose
    private List<Object> errors;
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

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
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
