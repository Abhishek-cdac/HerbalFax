
package com.herbal.herbalfax.common_screen.landingpage.events.addevent.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Event {

    @SerializedName("EventAddress")
    private String eventAddress;
    @SerializedName("EventCAt")
    private String eventCAt;
    @SerializedName("EventCOn")
    private String eventCOn;
    @SerializedName("EventComments")
    private String eventComments;
    @SerializedName("EventDate")
    private String eventDate;
    @SerializedName("EventDay")
    private String eventDay;
    @SerializedName("EventDesc")
    private String eventDesc;
    @SerializedName("EventImage")
    private String eventImage;
    @SerializedName("EventLat")
    private String eventLat;
    @SerializedName("EventLong")
    private String eventLong;
    @SerializedName("EventName")
    private String eventName;
    @SerializedName("EventTime")
    private String eventTime;
    @SerializedName("EventUser")
    private String eventUser;
    @SerializedName("EventViews")
    private String eventViews;
    @Expose
    private String idevents;

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventCAt() {
        return eventCAt;
    }

    public void setEventCAt(String eventCAt) {
        this.eventCAt = eventCAt;
    }

    public String getEventCOn() {
        return eventCOn;
    }

    public void setEventCOn(String eventCOn) {
        this.eventCOn = eventCOn;
    }

    public String getEventComments() {
        return eventComments;
    }

    public void setEventComments(String eventComments) {
        this.eventComments = eventComments;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventLat() {
        return eventLat;
    }

    public void setEventLat(String eventLat) {
        this.eventLat = eventLat;
    }

    public String getEventLong() {
        return eventLong;
    }

    public void setEventLong(String eventLong) {
        this.eventLong = eventLong;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventUser() {
        return eventUser;
    }

    public void setEventUser(String eventUser) {
        this.eventUser = eventUser;
    }

    public String getEventViews() {
        return eventViews;
    }

    public void setEventViews(String eventViews) {
        this.eventViews = eventViews;
    }

    public String getIdevents() {
        return idevents;
    }

    public void setIdevents(String idevents) {
        this.idevents = idevents;
    }

}
