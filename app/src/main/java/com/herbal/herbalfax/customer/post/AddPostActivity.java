package com.herbal.herbalfax.customer.post;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.homedashboard.LandingPageActivity;
import com.herbal.herbalfax.databinding.FragmentAddPostBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    LinearLayout addpostll, addquestionll;
    LinearLayout bottomNavigationView, ll_upload_photo, ll_upload_video;
    private CommonClass clsCommon;
    EditText addQuestionEdt;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 150;
    private FragmentAddPostBinding binding;
    ImageView post_img, back;
    String PostIsMedia;
    private PlayerView pvMain;
    private boolean isVideo = false;
    Bitmap bitmap = null;
    String selectedVideoPath, postGroupId;

    public final static int REQUEST_TAKE_GALLERY_VIDEO = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        AddPostViewModel addPostViewModel = new AddPostViewModel();
        binding = DataBindingUtil.setContentView(AddPostActivity.this, R.layout.fragment_add_post);
        binding.setLifecycleOwner(this);
        binding.setAddPostViewModel(addPostViewModel);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postGroupId = getIntent().getExtras().getString("PostGroupId");
            Log.e("postGroupId", "" + postGroupId);
        }
        back = findViewById(R.id.back);
        addQuestionEdt = findViewById(R.id.addQuestionEdt);
        radioGroup = findViewById(R.id.radioGroup);
        post_img = findViewById(R.id.post_img);
        addpostll = findViewById(R.id.addpostll);
        ll_upload_photo = findViewById(R.id.ll_upload_photo);
        ll_upload_video = findViewById(R.id.ll_upload_video);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        addquestionll = findViewById(R.id.addquestionll);
        pvMain = findViewById(R.id.ep_video_view);
        radioGroup.clearCheck();

        addPostViewModel.onGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AddPostActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            bottomNavigationView.setVisibility(View.VISIBLE);
        });


        ll_upload_photo.setOnClickListener(view -> pickImage());
        ll_upload_video.setOnClickListener(view -> pickVideo());

        back.setOnClickListener(view -> onBackPressed());


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    if (rb.getText().equals("Add Post")) {
                        PostIsMedia = "1";
                        addpostll.setVisibility(View.VISIBLE);
                        addquestionll.setVisibility(View.GONE);
                    } else if (rb.getText().equals("Add Question")) {
                        PostIsMedia = "0";
                        addpostll.setVisibility(View.GONE);
                        addquestionll.setVisibility(View.VISIBLE);
                    }
//                    else {
//                        Toast.makeText(getApplicationContext(), "Please select post type", Toast.LENGTH_SHORT).show();
//
//                    }
                }


            }
        });
        addPostViewModel.OnCancelClick().observe(this, addpost -> {
            Intent mIntent = new Intent(AddPostActivity.this, LandingPageActivity.class);
            startActivity(mIntent);
        });
        addPostViewModel.getPost().observe(this, addpost -> {
            try {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    new CommonClass().showDialogMsg(AddPostActivity.this, "HerbalFax", "select post type", "Ok");

                } else if (PostIsMedia.equals("1")) {
                    if (TextUtils.isEmpty(Objects.requireNonNull(addpost).getStrDescription())) {
                        // binding.loginEmail.setError("Enter an E-Mail Address");
                        new CommonClass().showDialogMsg(AddPostActivity.this, "HerbalFax", "Enter description", "Ok");

                        binding.desc.requestFocus();
                    } else {

                        //  callAddPostAPI(addpost);
                        uploadImage(addpost);
                    }
                } else if (PostIsMedia.equals("0")) {
                    if (TextUtils.isEmpty(Objects.requireNonNull(addpost).getStrDescription())) {
                        // binding.loginEmail.setError("Enter an E-Mail Address");
                        new CommonClass().showDialogMsg(AddPostActivity.this, "HerbalFax", "Enter Question", "Ok");
                        binding.addQuestionEdt.requestFocus();
                    } else {

                        callAddQuestionAPI(addpost);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callAddQuestionAPI(AddPostModel addpost) {

        Log.e("PostIsMedia question ", "" + PostIsMedia);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddPostActivity.this);
        pd.show();
        //create a file to write bitmap data
        File f = new File(getCacheDir(), "profile");
        try {
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = null;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SessionPref pref = SessionPref.getInstance(this);

        RequestBody PostIsMediarb = RequestBody.create(MediaType.parse("text/plain"), PostIsMedia);

        RequestBody PostDesc = RequestBody.create(MediaType.parse("text/plain"), addpost.getStrDescription());
        RequestBody PostMediaType = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody PostGroupId = RequestBody.create(MediaType.parse("text/plain"), postGroupId);

        // RequestBody fbody = RequestBody.create(MediaType.parse("image/*"),f);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("PostMediaLink", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<CommonResponse> call = service.addPost("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), PostIsMediarb, PostDesc, PostMediaType,PostGroupId, filePart);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            Intent intent1 = new Intent(getApplicationContext(), LandingPageActivity.class);
                            startActivity(intent1);
                            finish();
                        } else {
                            clsCommon.showDialogMsg(AddPostActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddPostActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddPostActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddPostActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("IntentReset")
    public void pickImage() {
        @SuppressLint("IntentReset")
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);

//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("*/*");
//        photoPickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
//        startActivityForResult(photoPickerIntent, PICK_PHOTO_FOR_AVATAR);

    }

    public void pickVideo() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode != RESULT_OK) {
                return;
            }
            if (requestCode == PICK_PHOTO_FOR_AVATAR) {

                if (data.getData() == null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != bitmap) {
                    post_img.setImageBitmap(bitmap);
                    binding.camera.setVisibility(View.GONE);
                    binding.addImage.setVisibility(View.GONE);
                    bottomNavigationView.setVisibility(View.GONE);
                    pvMain.setVisibility(View.GONE);


                }


            } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {

                if (data != null) {

                    Uri contentURI = data.getData();
                    selectedVideoPath = getPath(contentURI);
                    Log.e("selectedVideoPath", "" + selectedVideoPath);
                    if (selectedVideoPath != null) {
                        pvMain.setVisibility(View.VISIBLE);
                        post_img.setVisibility(View.GONE);
                        binding.camera.setVisibility(View.GONE);
                        binding.addImage.setVisibility(View.GONE);

                        isVideo = true;

                        TrackSelector trackSelectorDef = new DefaultTrackSelector();
                        SimpleExoPlayer absPlayerInternal = ExoPlayerFactory.newSimpleInstance(this, trackSelectorDef); //creating a player instance

                        String userAgent = Util.getUserAgent(this, this.getString(R.string.app_name));
                        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
                        Uri uriOfContentUrl = Uri.parse(getIntent().getStringExtra("videoPath"));
                        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source
                        absPlayerInternal.prepare(mediaSource);
                        absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline

                        pvMain.setPlayer(absPlayerInternal);
                        pvMain.hideController();
                        pvMain.setControllerAutoShow(false);
                        pvMain.setControllerHideOnTouch(true);
                        pvMain.setUseController(false);

                        bottomNavigationView.setVisibility(View.GONE);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    private void uploadImage(AddPostModel addpost) {
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();

        SessionPref pref = SessionPref.getInstance(this);
        MultipartBody.Part filePart;

        RequestBody PostIsMediarb = RequestBody.create(MediaType.parse("text/plain"), PostIsMedia);

        RequestBody PostDesc = RequestBody.create(MediaType.parse("text/plain"), addpost.getStrDescription());
        RequestBody PostMediaTypeImage = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody PostMediaTypeVideo = RequestBody.create(MediaType.parse("text/plain"), "2");
        RequestBody PostGroupId = RequestBody.create(MediaType.parse("text/plain"), postGroupId);

        GetDataService service;
        Call<CommonResponse> call;
        if (isVideo) {
            File currentFile;
            currentFile = new File(selectedVideoPath);
            filePart = MultipartBody.Part.createFormData("PostMediaLink", currentFile.getName(), RequestBody.create(MediaType.parse("video/*"), currentFile));
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            call = service.addPost("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), PostIsMediarb, PostDesc, PostMediaTypeVideo, PostGroupId, filePart);
        } else {
            //create a file to write bitmap data
            File f = new File(getCacheDir(), "profile");
            try {
                f.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
                byte[] bitmapdata = bos.toByteArray();
                //write the bytes in file
                FileOutputStream fos = null;
                fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            filePart = MultipartBody.Part.createFormData("PostMediaLink", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            call = service.addPost("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), PostIsMediarb, PostDesc, PostMediaTypeImage,PostGroupId,  filePart);
        }


        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.dismiss();
                if (response.code() == 200) {

                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        Toast.makeText(getApplicationContext(), "feed post successfully", Toast.LENGTH_SHORT).show();
                        //  LoginUserDetails user = response.body().getUserData();
                        // callAPIFeedPost(user.getMediaId());
                        Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                        startActivity(intent);
                    } else {
                        new CommonClass().showDialogMsg(AddPostActivity.this, "PlayDate", "An error occurred!", "Ok");
                    }


                } else {
                    new CommonClass().showDialogMsg(AddPostActivity.this, "PlayDate", "An error occurred!", "Ok");

                }


            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.dismiss();
            }
        });
    }


    public void onClear(View v) {
        radioGroup.clearCheck();
    }

    public void onSubmit(View v) {
        RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(AddPostActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
    }

}