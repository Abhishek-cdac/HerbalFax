package com.herbal.herbalfax.vendor.sellerdeals.adddeal;

public class NewDeals {

    public String getProductDealName() {
        return productDealName;
    }

    public void setProductDealName(String productDealName) {
        this.productDealName = productDealName;
    }

    public String getProductDealDate() {
        return productDealDate;
    }

    public void setProductDealDate(String productDealDate) {
        this.productDealDate = productDealDate;
    }

    public String getProductDealPrice() {
        return productDealPrice;
    }

    public void setProductDealPrice(String productDealPrice) {
        this.productDealPrice = productDealPrice;
    }

    public String getProductDealQuantityNo() {
        return productDealQuantityNo;
    }

    public void setProductDealQuantityNo(String productDealQuantityNo) {
        this.productDealQuantityNo = productDealQuantityNo;
    }

    public String getProductDealLocation() {
        return productDealLocation;
    }

    public void setProductDealLocation(String productDealLocation) {
        this.productDealLocation = productDealLocation;
    }

    public NewDeals(String productDealName, String productDealLocation,String productDealDate, String productDealQuantityNo, String productDealPrice) {
        this.productDealName = productDealName;
        this.productDealLocation = productDealLocation;
        this.productDealDate = productDealDate;
        this.productDealQuantityNo = productDealQuantityNo;
        this.productDealPrice = productDealPrice;

    }

    String productDealName, productDealDate, productDealPrice, productDealQuantityNo, productDealLocation;

}
