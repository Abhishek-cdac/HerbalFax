
package com.herbal.herbalfax.common_screen.verify_email_after_signup.resendmodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @Expose
    private Long otp;
    @SerializedName("user_email")
    private String userEmail;

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
