package com.herbal.herbalfax.customer.post;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herbal.herbalfax.common_screen.login.LoginUser;

public class AddPostViewModel extends ViewModel {

    public AddPostViewModel() {
    }

    public MutableLiveData<Boolean> AddClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> CancelClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();

    public MutableLiveData<String> description = new MutableLiveData<>();
 // public MutableLiveData<String> question = new MutableLiveData<>();

    private MutableLiveData<AddPostModel> userMutableLiveData;



    public MutableLiveData<Boolean> onGalleryClick() {

        return Gallery;

    }
    public MutableLiveData<AddPostModel> getPost() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }
    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public void onClickAdd(View view) {
      //  AddClick.setValue(true);
        AddPostModel addPostModel = new AddPostModel(description.getValue());
        userMutableLiveData.setValue(addPostModel);
    }

    public void onClickCancel(View view) {
        {
            CancelClick.setValue(true);
        }
  }

    public MutableLiveData<Boolean> OnCancelClick() {

        return CancelClick;

    }


}
