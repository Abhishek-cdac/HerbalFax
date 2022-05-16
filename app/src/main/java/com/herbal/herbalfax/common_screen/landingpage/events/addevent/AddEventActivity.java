package com.herbal.herbalfax.common_screen.landingpage.events.addevent;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.landingpage.events.addevent.model.AddEventResponse;
import com.herbal.herbalfax.common_screen.landingpage.events.eventlist.EventsDetailsActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.placepicker.GooglePlacePickerActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity {
    CommonClass clsCommon;

    final Calendar myCalendar = Calendar.getInstance();
    Bitmap bitmap = null;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 150;
    EditText eventDate;
    ImageView back, eventImage;
    LinearLayout ll_pic;
    EditText eventTimeEdt, eventAddress;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        AddEventViewModel addEventViewModel = new AddEventViewModel();
        com.herbal.herbalfax.databinding.ActivityAddEventBinding binding = DataBindingUtil.setContentView(AddEventActivity.this, R.layout.activity_add_event);
        binding.setLifecycleOwner(this);
        binding.setAddEventViewModel(addEventViewModel);
        eventDate = findViewById(R.id.eventDate);
        ll_pic = findViewById(R.id.ll_pic);
        back = findViewById(R.id.back);
        eventImage = findViewById(R.id.eventImage);
        eventTimeEdt = findViewById(R.id.eventTimeEdt);
        eventAddress = findViewById(R.id.eventaddress);
        addEventViewModel.onGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AddEventActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickImage();

        });
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        back.setOnClickListener(view -> onBackPressed());
        eventDate.setOnClickListener(v -> {

            DatePickerDialog dialog = new DatePickerDialog(AddEventActivity.this,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());

            dialog.show();
        });

        eventAddress.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GooglePlacePickerActivity.class);
            startActivityForResult(intent, GooglePlacePickerActivity.REQUEST_LOCATION);
        });
        eventTimeEdt.setOnClickListener(view -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this,
                    (view1, hourOfDay, minute) -> eventTimeEdt.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
            timePickerDialog.show();
        });

        addEventViewModel.OnCancelClick().observe(this, addEvents -> {
            onBackPressed();
    });
        addEventViewModel.getAddEvents().observe(this, addEvents -> {


            if (TextUtils.isEmpty(Objects.requireNonNull(addEvents).getEventName())) {
                clsCommon.showDialogMsg(AddEventActivity.this, "HerbalFax", "Enter event Name", "Ok");
            } else if (eventDate.getText().toString().equals("")) {
                clsCommon.showDialogMsg(AddEventActivity.this, "HerbalFax", "Enter event Date", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addEvents).getEventAddress())) {
                clsCommon.showDialogMsg(AddEventActivity.this, "HerbalFax", "Enter event address", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addEvents).getEventTime())) {
                clsCommon.showDialogMsg(AddEventActivity.this, "HerbalFax", "Enter event time", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(addEvents).getEventDesc())) {
                clsCommon.showDialogMsg(AddEventActivity.this, "HerbalFax", "Enter event desc", "Ok");
            } else {
                callAddEventAPI(addEvents);
            }


        });

    }

    private void callAddEventAPI(AddEvents addEvents) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddEventActivity.this);
        pd.show();
        //create a file to write bitmap data
        File f = new File(getCacheDir(), "event");
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

        RequestBody eventName = RequestBody.create(MediaType.parse("text/plain"), addEvents.getEventName());
        RequestBody eventDate = RequestBody.create(MediaType.parse("text/plain"), addEvents.getEventDate());
        RequestBody eventTime = RequestBody.create(MediaType.parse("text/plain"), addEvents.getEventTime());
        RequestBody eventAddress = RequestBody.create(MediaType.parse("text/plain"), addEvents.getEventAddress());
        RequestBody eventDesc = RequestBody.create(MediaType.parse("text/plain"), addEvents.getEventDesc());
        RequestBody eventDay = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody eventLat = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody eventLong = RequestBody.create(MediaType.parse("text/plain"), longitude);


     /*   Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("EventName", eventName);
        hashMap.put("EventDate", eventDate);
        hashMap.put("EventTime", eventTime);
        hashMap.put("EventAddress", eventAddress);
        hashMap.put("EventDesc", eventDesc);
        hashMap.put("EventDay", eventDay);
        hashMap.put("EventLat", eventLat);
        hashMap.put("EventLong", eventLong);
     */

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("EventImage", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        //  Call<AddEventResponse> call = service.userEventCreate("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap, filePart);
        Call<AddEventResponse> call = service.userEventCreate("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), eventName, eventDate, eventTime, eventDay, eventLat, eventLong, eventDesc, eventAddress, filePart);
        call.enqueue(new Callback<AddEventResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddEventResponse> call, @NonNull Response<AddEventResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {

                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            Intent intent = new Intent(getApplicationContext(), EventsDetailsActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("Error", "" + response.body().getErrors().toString());
                            Log.e("Error", "" + response.body().getErrors());
                            clsCommon.showDialogMsg(AddEventActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddEventActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddEventActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddEventResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddEventActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eventDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode != RESULT_OK) {
                return;
            }


            if (requestCode == GooglePlacePickerActivity.REQUEST_LOCATION) {
                String placeName = data.getStringExtra("place");
                android.util.Log.e("AddStoreActivity.....", "" + placeName);
                String latLng = data.getStringExtra("latLng");
                android.util.Log.e("latLng_add_store", "" + latLng);
                String[] namesList = latLng.split(",");
                latitude = namesList[0];
                longitude = namesList[1];

                android.util.Log.e("latLng_add_store", "" + latitude);
                android.util.Log.e("latLng_add_store", "" + longitude);
                eventAddress.setText(placeName);
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
                    eventImage.setImageBitmap(bitmap);
                    ll_pic.setVisibility(View.GONE);
                } else {
                    ll_pic.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}