
package com.herbal.herbalfax.common_screen.landingpage.events.addevent.model;


import com.google.gson.annotations.Expose;


public class Data {

    @Expose
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
