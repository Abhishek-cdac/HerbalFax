
package com.herbal.herbalfax.common_screen.landingpage.events.eventdetailsmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("events")
    private List<Event> mEvents;

    public List<Event> getEvents() {
        return mEvents;
    }

    public void setEvents(List<Event> events) {
        mEvents = events;
    }

}
