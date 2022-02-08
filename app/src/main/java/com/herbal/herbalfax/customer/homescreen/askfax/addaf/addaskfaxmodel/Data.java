
package com.herbal.herbalfax.customer.homescreen.askfax.addaf.addaskfaxmodel;


import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("af_question")
    private AfQuestion afQuestion;

    public AfQuestion getAfQuestion() {
        return afQuestion;
    }

    public void setAfQuestion(AfQuestion afQuestion) {
        this.afQuestion = afQuestion;
    }

}
