
package com.herbal.herbalfax.customer.homescreen.addcard.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("user_cards")
    private List<UserCard> userCards;

    public List<UserCard> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<UserCard> userCards) {
        this.userCards = userCards;
    }

}
