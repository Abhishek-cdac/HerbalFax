package com.herbal.herbalfax.vendor.sellerproduct.addproduct;

public class NewProduct {


    String productName, productDescription;
    String productPrice, productQuantity, productcost, productDistance;

    public NewProduct(String productName, String productPrice, String productQuantity, String productDescription, String productcost, String productDistance) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productDescription = productDescription;
        this.productcost = productcost;
        this.productDistance = productDistance;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductcost() {
        return productcost;
    }

    public void setProductcost(String productcost) {
        this.productcost = productcost;
    }

    public String getProductDistance() {
        return productDistance;
    }

    public void setProductDistance(String productDistance) {
        this.productDistance = productDistance;
    }
}
