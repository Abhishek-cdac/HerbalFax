
package com.herbal.herbalfax.customer.homescreen.askfax.aflist.addanswermodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("que_answers")
    private List<QueAnswer> queAnswers;

    public List<QueAnswer> getQueAnswers() {
        return queAnswers;
    }

    public void setQueAnswers(List<QueAnswer> queAnswers) {
        this.queAnswers = queAnswers;
    }

}
