
package com.herbal.herbalfax.customer.signup.presignupmodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("all_countries")
    private List<AllCountry> mAllCountries;
    @SerializedName("all_genders")
    private List<AllGender> mAllGenders;
    @SerializedName("all_professions")
    private List<AllProfession> mAllProfessions;

    public List<AllCountry> getAllCountries() {
        return mAllCountries;
    }

    public void setAllCountries(List<AllCountry> allCountries) {
        mAllCountries = allCountries;
    }

    public List<AllGender> getAllGenders() {
        return mAllGenders;
    }

    public void setAllGenders(List<AllGender> allGenders) {
        mAllGenders = allGenders;
    }

    public List<AllProfession> getAllProfessions() {
        return mAllProfessions;
    }

    public void setAllProfessions(List<AllProfession> allProfessions) {
        mAllProfessions = allProfessions;
    }

}
