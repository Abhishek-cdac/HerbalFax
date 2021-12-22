package com.herbal.herbalfax.customer.signup;

public class RegisterUser {

    private String fullName;

    private String city;
    private String zipCode;
    private String address;
    private String phoneNo;
    private String email;
    private String password;


    public RegisterUser(String fullName, String city, String zipCode, String address,  String phoneNo, String email, String password) {
        this.fullName = fullName;
        this.city = city;
        this.zipCode = zipCode;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
