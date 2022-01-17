
package com.herbal.herbalfax.customer.homescreen.addcard.ordersubmitmodel;


import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
