package com.herbal.herbalfax.customer.homescreen.askfax;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herbal.herbalfax.customer.homescreen.askfax.sharestory.CreateBlog;


public class AskFaxViewModel extends ViewModel {


    public AskFaxViewModel() {

    }
    CreateBlog user;

    public MutableLiveData<CreateBlog> userMutableLiveData;

    public MutableLiveData<String> BlogTitle = new MutableLiveData<>();
    public MutableLiveData<String> Blogdesc = new MutableLiveData<>();
    public MutableLiveData<String> BlogUrl = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();


    public MutableLiveData<CreateBlog> getSaveBlogs() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            user = new CreateBlog("", "", "");
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> onGalleryClick() {

        return Gallery;

    }

    public void OnGallery(View view) {
        Gallery.setValue(true);
    }



    public void onClickSave(View view) {
        user = new CreateBlog(BlogTitle.getValue(), Blogdesc.getValue(), BlogUrl.getValue());
        userMutableLiveData.setValue(user);
    }


    public void clearFileds() {
        user = new CreateBlog("", "", "");
        userMutableLiveData.setValue(user);

    }
}
