package com.herbal.herbalfax.customer.homescreen.addcard;

public class AddCardUser {
    String name;
    String cardNumber;
    String cardMM;
    String cardYY;
    String cardCVV;

    public AddCardUser(String name, String cardNumber, String cardMM, String cardYY, String cardCVV) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cardMM = cardMM;
        this.cardYY = cardYY;
        this.cardCVV = cardCVV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardMM() {
        return cardMM;
    }

    public void setCardMM(String cardMM) {
        this.cardMM = cardMM;
    }

    public String getCardYY() {
        return cardYY;
    }

    public void setCardYY(String cardYY) {
        this.cardYY = cardYY;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }
}
