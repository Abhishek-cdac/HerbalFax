package com.herbal.herbalfax.common_screen.landingpage.events.addevent;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class AddEventViewModel extends ViewModel {
    public AddEventViewModel() {

    }

    public MutableLiveData<AddEvents> userMutableLiveData;
    public MutableLiveData<Boolean> CancelClick = new MutableLiveData<>();

    public MutableLiveData<String> eventName = new MutableLiveData<>();
    public MutableLiveData<String> eventDate = new MutableLiveData<>();
    public MutableLiveData<String> eventTime = new MutableLiveData<>();
    public MutableLiveData<String> eventAddress = new MutableLiveData<>();
    public MutableLiveData<String> eventDesc = new MutableLiveData<>();
    public MutableLiveData<Boolean> addEventClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();


    public MutableLiveData<AddEvents> getAddEvents() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            user = new AddEvents( "", "", "", "", "");
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> onGalleryClick() {

        return Gallery;

    }

    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public MutableLiveData<Boolean> OnAddEventHereClick() {

        return addEventClick;

    }

    public void onAddEventHere(View view) {
        addEventClick.setValue(true);
    }


    AddEvents user;

    public void onClickAddEvent(View view) {
        user = new AddEvents(eventName.getValue(),  eventDate.getValue(), eventTime.getValue(), eventDesc.getValue(),  eventAddress.getValue());
        userMutableLiveData.setValue(user);
    }
  public void onClickCancelEvent(View view) {
      {
          CancelClick.setValue(true);
      }
    }


    public void clearFileds() {
        user = new AddEvents("", "", "", "", "");
        userMutableLiveData.setValue(user);

    }

    public MutableLiveData<Boolean> OnCancelClick() {

        return CancelClick;

    }

}
