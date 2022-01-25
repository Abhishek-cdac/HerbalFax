
package com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.detaileventsmodel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @Expose
    private Event event;
    @SerializedName("event_comments")
    private List<EventComment> eventComments;
    @SerializedName("event_views")
    private List<EventView> eventViews;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<EventComment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(List<EventComment> eventComments) {
        this.eventComments = eventComments;
    }

    public List<EventView> getEventViews() {
        return eventViews;
    }

    public void setEventViews(List<EventView> eventViews) {
        this.eventViews = eventViews;
    }

}
