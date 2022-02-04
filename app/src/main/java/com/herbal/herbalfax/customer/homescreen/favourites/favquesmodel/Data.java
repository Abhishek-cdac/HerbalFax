
package com.herbal.herbalfax.customer.homescreen.favourites.favquesmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel.AfQuestion;


public class Data {
/*AfQuestionFav*/
    @SerializedName("af_question_favs")
    private List<AfQuestion> afQuestionFavs;

    public List<AfQuestion> getAfQuestionFavs() {
        return afQuestionFavs;
    }

    public void setAfQuestionFavs(List<AfQuestion> afQuestionFavs) {
        this.afQuestionFavs = afQuestionFavs;
    }

}
