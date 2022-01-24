
package com.herbal.herbalfax.common_screen.landingpage.events.eventdetail.commentlstmodel;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("event_comments")
    private List<EventComment> eventComments;

    public List<EventComment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(List<EventComment> eventComments) {
        this.eventComments = eventComments;
    }

}
