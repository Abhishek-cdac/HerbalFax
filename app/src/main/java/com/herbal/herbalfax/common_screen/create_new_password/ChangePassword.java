package com.herbal.herbalfax.common_screen.create_new_password;

public class ChangePassword {
    private final String strNewPassword;

    public String getStrNewPassword() {
        return strNewPassword;
    }

    public String getStrPassword() {
        return strPassword;
    }

    private final String strPassword;

    public ChangePassword(String password, String newPassword) {

        strPassword = password;
        strNewPassword = newPassword;
    }
}
