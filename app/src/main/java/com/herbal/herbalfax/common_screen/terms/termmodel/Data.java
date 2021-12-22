
package com.herbal.herbalfax.common_screen.terms.termmodel;


import com.google.gson.annotations.Expose;


public class Data {

    @Expose
    private Tnc tnc;

    public Tnc getTnc() {
        return tnc;
    }

    public void setTnc(Tnc tnc) {
        this.tnc = tnc;
    }

}
