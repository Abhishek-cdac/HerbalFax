
package com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("af_questions")
    private List<AfQuestion> afQuestions;

    public List<AfQuestion> getAfQuestions() {
        return afQuestions;
    }

    public void setAfQuestions(List<AfQuestion> afQuestions) {
        this.afQuestions = afQuestions;
    }

}
