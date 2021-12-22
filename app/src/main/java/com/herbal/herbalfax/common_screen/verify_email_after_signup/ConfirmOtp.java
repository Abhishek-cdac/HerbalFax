package com.herbal.herbalfax.common_screen.verify_email_after_signup;

public class ConfirmOtp {

    private final String strOTP;

    public String getStrOTP() {
        return strOTP;
    }

    public ConfirmOtp(String Otp) {
        strOTP = Otp;

    }
}
