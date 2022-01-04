package com.herbal.herbalfax.customer.homescreen.cart.addaddress;

public class AddAddressUser {
    private String fullName;
    private String city;
    private String pinCode;
    private String address;
    private String country;

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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AddAddressUser(String fullName, String city, String pinCode, String address, String country) {
        this.fullName = fullName;
        this.city = city;
        this.pinCode = pinCode;
        this.address = address;
        this.country = country;
    }
}
