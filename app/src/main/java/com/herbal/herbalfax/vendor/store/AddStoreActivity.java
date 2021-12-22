package com.herbal.herbalfax.vendor.store;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.placepicker.GooglePlacePickerActivity;
import com.herbal.herbalfax.databinding.ActivityAddStoreBinding;
import com.herbal.herbalfax.vendor.SellerLandingPageActivity;
import com.herbal.herbalfax.vendor.sellerproduct.addproduct.AddProductActivity;
import com.herbal.herbalfax.vendor.store.storecategory.AllStoreCategory;
import com.herbal.herbalfax.vendor.store.storecategory.StoreAddPreData;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.Store;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreBusinessHr;
import com.herbal.herbalfax.vendor.storedetail.storedetailmodel.StoreDetailResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStoreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 1;
    public final static int PICK_PHOTO_FOR_LOGO = 2;
    public final static int PICK_PHOTO_FOR_STORE_IMAGES = 3;
    private CommonClass clsCommon;
    private ActivityAddStoreBinding binding;
    private ArrayList<AllStoreCategory> lst_store_category;
    EditText locations;
    Spinner CategorySpinner;
    ImageView certificate_image, logo_image, storephoto_image;
    EditText mondayStartTime, mondayEndtTime;
    CheckBox mondayCB, tuesdayCB, wednesdayCB, thursdayCB, fridayCB, saturdayCB, sundayCB;
    EditText tuesdayStartTime, tuesdayEndtTime, wednesdayStartTime, wednesdayEndtTime, thursdayStartTime, thursdayEndtTime,
            fridayStartTime, fridayEndtTime, saturdayStartTime, saturdayEndtTime, sundayStartTime, sundayEndtTime;
    String latitude = "", longitude = "";
    JSONArray jsonArray;
    TextView headerTxt;
    String mondayStatus = "0";
    String tuesdayStatus = "0";
    String wednesdayStatus = "0";
    String thursdayStatus = "0";
    String fridayStatus = "0";
    String saturdayStatus = "0";
    String sundayStatus = "0";
    String IdstoreCategories;
    String dow, type;
    String starttime = "";
    String endtime = "";
    String storeId;
    AddStoreViewModel addStoreViewModel;
    Button button;
    Bitmap bitmap = null;
    Bitmap bitmap1 = null;
    Bitmap bitmap2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_store);

        addStoreViewModel = new AddStoreViewModel();
        binding = DataBindingUtil.setContentView(AddStoreActivity.this, R.layout.activity_add_store);
        binding.setLifecycleOwner(this);
        binding.setAddStoreViewModel(addStoreViewModel);
        clsCommon = CommonClass.getInstance();
        type = getIntent().getStringExtra("type");
        headerTxt = findViewById(R.id.headerTxt);
        button = findViewById(R.id.button);

        if (type != null && type.equals("edit")) {
            storeId = getIntent().getStringExtra("storeId");

            headerTxt.setText("Edit Your Store");
            button.setText("UPDATE STORE");
            callSellerStoreDetailAPI(storeId);
        } else {
            headerTxt.setText("Add Your Store");
            button.setText("ADD STORE");

        }

        mondayCB = findViewById(R.id.mondayCB);
        tuesdayCB = findViewById(R.id.tuesdayCB);
        wednesdayCB = findViewById(R.id.wednesdayCB);
        thursdayCB = findViewById(R.id.thursdayCB);
        fridayCB = findViewById(R.id.fridayCB);
        saturdayCB = findViewById(R.id.saturdayCB);
        sundayCB = findViewById(R.id.sundayCB);

        mondayStartTime = findViewById(R.id.mondayStartTime);
        mondayEndtTime = findViewById(R.id.mondayEndtTime);
        tuesdayStartTime = findViewById(R.id.tuesdayStartTime);
        tuesdayEndtTime = findViewById(R.id.tuesdayEndtTime);
        wednesdayStartTime = findViewById(R.id.wednesdayStartTime);
        wednesdayEndtTime = findViewById(R.id.wednesdayEndtTime);
        thursdayStartTime = findViewById(R.id.thursdayStartTime);
        thursdayEndtTime = findViewById(R.id.thursdayEndtTime);
        fridayStartTime = findViewById(R.id.fridayStartTime);
        fridayEndtTime = findViewById(R.id.fridayEndtTime);
        saturdayStartTime = findViewById(R.id.saturdayStartTime);
        saturdayEndtTime = findViewById(R.id.saturdayEndtTime);
        sundayStartTime = findViewById(R.id.sundayStartTime);
        sundayEndtTime = findViewById(R.id.sunayEndtTime);

        logo_image = findViewById(R.id.logo_image);
        storephoto_image = findViewById(R.id.storephoto_image);
        certificate_image = findViewById(R.id.certificate_image);

        CategorySpinner = findViewById(R.id.spinner);
        callStorePreDataAPI();
        locations = findViewById(R.id.locations);

        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GooglePlacePickerActivity.class);
                //noinspection deprecation
                startActivityForResult(intent, GooglePlacePickerActivity.REQUEST_LOCATION);
            }
        });

        addStoreViewModel.onGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AddStoreActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickImage();

        });
        addStoreViewModel.onLogoGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AddStoreActivity.this,
                    PERMISSIONS, ALL_PERMISSIONS_RESULT);
            pickImageLogo();

        });
        addStoreViewModel.onStoreGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AddStoreActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickImageStoreImages();

        });

        mondayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mondayStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        mondayEndtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mondayEndtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);  //Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        tuesdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tuesdayStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        tuesdayEndtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tuesdayEndtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        wednesdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        wednesdayStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        wednesdayEndtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        wednesdayEndtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        thursdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        thursdayStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        thursdayEndtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        thursdayEndtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        fridayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fridayStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        fridayEndtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fridayEndtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        saturdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        saturdayStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        saturdayEndtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        saturdayEndtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        sundayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        sundayStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        sundayEndtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        sundayEndtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        mondayCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    mondayStatus = "0";
                    dow = "1";
                } else {
                    mondayStatus = "1";
                    dow = "1";
                }
            }
        });
        tuesdayCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    tuesdayStatus = "0";
                    dow = "2";
                } else {
                    tuesdayStatus = "1";
                    dow = "2";
                }
            }
        });
        wednesdayCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!compoundButton.isChecked()) {
                    wednesdayStatus = "0";
                    dow = "3";
                } else {
                    wednesdayStatus = "1";
                    dow = "3";
                }
            }
        });
        thursdayCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    thursdayStatus = "0";
                    dow = "4";
                } else {
                    thursdayStatus = "1";
                    dow = "4";
                }
            }
        });
        fridayCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    fridayStatus = "0";
                    dow = "5";
                } else {
                    fridayStatus = "1";
                    dow = "5";
                }
            }
        });
        saturdayCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    saturdayStatus = "0";
                    dow = "6";
                } else {
                    saturdayStatus = "1";
                    dow = "6";
                }
            }
        });
        sundayCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    sundayStatus = "0";
                    dow = "7";
                } else {
                    sundayStatus = "1";
                    dow = "7";
                }
            }
        });


        addStoreViewModel.getRegisterStore().observe(this, newStore -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(newStore).getStoreFullName())) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Enter Store Full Name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newStore).getStoreLocation())) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Enter Location", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newStore).getStoreLicenseNo())) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Enter LicenseNo", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newStore).getStoreDesc())) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Enter Description", "Ok");
            } else if (CategorySpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Enter Store type", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newStore).getStoreDistanceRange())) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Enter Phone Distance Range", "Ok");
            }else if (bitmap == null) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Please select certificate Image", "Ok");

            } else if (bitmap1 == null) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Please select store logo", "Ok");

            } else if (bitmap2 == null) {
                clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "Please select store photo", "Ok");

            }  else {
                if (type != null && type.equals("edit")) {
                    makeJSON();
                    callUpdateStoreAPI(storeId, newStore, jsonArray.toString(), latitude, longitude);
                } else {
                    makeJSON();
                    callAddStoreAPI(newStore, jsonArray.toString(), latitude, longitude);
                }


            }

        });
    }

    private void callUpdateStoreAPI(String storeId, NewStore newStore, String storeTimeArray, String latitude, String longitude) {

        Log.e("jsonArray", "" + storeTimeArray);
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddStoreActivity.this);
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

        File storeLogo = new File(getCacheDir(), "storeLogo");
        try {
            storeLogo.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = null;
            fos = new FileOutputStream(storeLogo);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File storeImage = new File(getCacheDir(), "storeImage");
        try {
            storeImage.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = null;
            fos = new FileOutputStream(storeImage);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//      RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), f));
        RequestBody storeName = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreFullName());
        RequestBody storeType = RequestBody.create(MediaType.parse("text/plain"), IdstoreCategories);
        RequestBody StoreId = RequestBody.create(MediaType.parse("text/plain"), storeId);

        RequestBody storeLocation = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreLocation());
        RequestBody storeLicenseNo = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreLicenseNo());
        RequestBody StoreDesc = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreDesc());
        RequestBody storeDistance = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreDistanceRange());
        RequestBody StoreLat = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody StoreLong = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody storeEmailAddress = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StorePhone = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StoreContactName = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StoreContactDesignation = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StoreTimings = RequestBody.create(MediaType.parse("text/plain"), storeTimeArray);


        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("StoreId", StoreId);
        hashMap.put("StoreName", storeName);
        hashMap.put("StoreCategory", storeType);
        hashMap.put("StoreLocation", storeLocation);  //1- male , 2 - female ,3 - other
        hashMap.put("StoreLat", StoreLat);
        hashMap.put("StoreLong", StoreLong);
        hashMap.put("StoreLicNo", storeLicenseNo);
        hashMap.put("StoreDesc", StoreDesc);
        hashMap.put("StoreDeliveryRange", storeDistance);
        hashMap.put("StoreEmail", storeEmailAddress);
        hashMap.put("StoreContactName", StoreContactName);
        hashMap.put("StorePhone", StorePhone);
        hashMap.put("StoreContactDesignation", StoreContactDesignation);
        hashMap.put("StoreTimings", StoreTimings);
        Log.e("hashMap", "" + hashMap);

        /*   --- StoreLicCertificate, StorePhotos[], StoreLogo are Image ---*/

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("StoreLicCertificate", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        MultipartBody.Part StorePhotos = MultipartBody.Part.createFormData("StorePhotos[]", storeImage.getName(), RequestBody.create(MediaType.parse("image/*"), storeImage));
        MultipartBody.Part StoreLogo = MultipartBody.Part.createFormData("StoreLogo", storeLogo.getName(), RequestBody.create(MediaType.parse("image/*"), storeLogo));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<CommonResponse> call = service.storeUpdate("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap, filePart, StorePhotos, StoreLogo);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            Intent intent = new Intent(getApplicationContext(), SellerLandingPageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("Error", "" + response.body().getErrors().toString());
                            Log.e("Error", "" + response.body().getErrors());
                            clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void callSellerStoreDetailAPI(String storeId) {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("StoreId", storeId);

//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getApplicationContext());
//        pd.show();
        Call<StoreDetailResponse> call = service.storeDetails("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<StoreDetailResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call<StoreDetailResponse> call, @NonNull Response<StoreDetailResponse> response) {
                // pd.cancel();
                if (response.code() == 200) {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        final Store store = response.body().getData().getStore();

                        addStoreViewModel.storefullName.setValue(store.getStoreName());
                        addStoreViewModel.storetype.setValue(store.getStoreCategory());
                        addStoreViewModel.storelicense.setValue(store.getStoreLicNo());
                        addStoreViewModel.storedistance.setValue(store.getStoreDeliveryRange());

                        if (store.getStoreLogo() != null) {
                            if (!store.getStoreLogo().equals("")) {
                                Picasso.get().load(store.getStoreLogo())
                                        .into(logo_image);
                            }
                        }
                        if (store.getStoreLicCertificate() != null) {
                            if (!store.getStoreLicCertificate().equals("")) {
                                Picasso.get().load(store.getStoreLicCertificate())
                                        .into(certificate_image);
                            }
                        }
                        locations.setText(store.getStoreLocation());
                        addStoreViewModel.description.setValue(store.getStoreDesc());
                        latitude = store.getStoreLat();
                        longitude = store.getStoreLong();
                        ArrayList<StoreBusinessHr> lst_store_business_hrs = (ArrayList<StoreBusinessHr>) response.body().getData().getStoreBusinessHrs();

                        Log.e("lst_store_detail", "inside");
                      /*  if (lst_store_business_hrs == null) {
                            lst_store_business_hrs = new ArrayList<>();
                        }

                        if (lst_store_business_hrs.get(0).getSBHDOW().equals("7")) {
                            if (lst_store_business_hrs.get(0).getSBHStatus().equals("7")) {
                                mondayCB.isChecked();
                                mondayStartTime.setText(lst_store_business_hrs.get(0).getSBHStartTime());
                                mondayEndtTime.setText(lst_store_business_hrs.get(0).getSBHEndTime());
                            }
                        }*/


                        /*ArrayList<StorePhoto> lst_store_photos = (ArrayList<StorePhoto>) response.body().getData().getStorePhotos();


                        if (lst_store_photos == null) {
                            lst_store_photos = new ArrayList<>();
                        }
                        if (lst_store_photos.get(0).getSPhotoPath() != null) {
                            if (!lst_store_photos.get(0).getSPhotoPath().equals("")) {
                                Picasso.get().load(lst_store_photos.get(0).getSPhotoPath())
                                        .into(storephoto_image);
                            }
                        }*/


                    } else {

                        clsCommon.showDialogMsgFrag(AddStoreActivity.this
                                , "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(AddStoreActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(@NotNull Call<StoreDetailResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                //pd.cancel();
                Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void callAddStoreAPI(NewStore newStore, String storeTimeArray, String latitude, String longitude) {
        Log.e("jsonArray", "" + storeTimeArray);
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddStoreActivity.this);
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

        File storeLogo = new File(getCacheDir(), "storeLogo");
        try {
            storeLogo.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = null;
            fos = new FileOutputStream(storeLogo);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File storeImage = new File(getCacheDir(), "storeImage");
        try {
            storeImage.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = null;
            fos = new FileOutputStream(storeImage);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//      RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), f));
        RequestBody storeName = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreFullName());
        RequestBody storeType = RequestBody.create(MediaType.parse("text/plain"), IdstoreCategories);

        RequestBody storeLocation = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreLocation());
        RequestBody storeLicenseNo = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreLicenseNo());
        RequestBody StoreDesc = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreDesc());
        RequestBody storeDistance = RequestBody.create(MediaType.parse("text/plain"), newStore.getStoreDistanceRange());
        RequestBody StoreLat = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody StoreLong = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody storeEmailAddress = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StorePhone = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StoreContactName = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StoreContactDesignation = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody StoreTimings = RequestBody.create(MediaType.parse("text/plain"), storeTimeArray);


        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("StoreName", storeName);
        hashMap.put("StoreCategory", storeType);
        hashMap.put("StoreLocation", storeLocation);  //1- male , 2 - female ,3 - other
        hashMap.put("StoreLat", StoreLat);
        hashMap.put("StoreLong", StoreLong);
        hashMap.put("StoreLicNo", storeLicenseNo);
        hashMap.put("StoreDesc", StoreDesc);
        hashMap.put("StoreDeliveryRange", storeDistance);
        hashMap.put("StoreEmail", storeEmailAddress);
        hashMap.put("StoreContactName", StoreContactName);
        hashMap.put("StorePhone", StorePhone);
        hashMap.put("StoreContactDesignation", StoreContactDesignation);
        hashMap.put("StoreTimings", StoreTimings);
        Log.e("hashMap", "" + hashMap);

        /*   --- StoreLicCertificate, StorePhotos[], StoreLogo are Image ---*/

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("StoreLicCertificate", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        MultipartBody.Part StorePhotos = MultipartBody.Part.createFormData("StorePhotos[]", storeImage.getName(), RequestBody.create(MediaType.parse("image/*"), storeImage));
        MultipartBody.Part StoreLogo = MultipartBody.Part.createFormData("StoreLogo", storeLogo.getName(), RequestBody.create(MediaType.parse("image/*"), storeLogo));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<CommonResponse> call = service.storeAdd("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap, filePart, StorePhotos, StoreLogo);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            Intent intent = new Intent(getApplicationContext(), SellerLandingPageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("Error", "" + response.body().getErrors().toString());
                            Log.e("Error", "" + response.body().getErrors());
                            clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callStorePreDataAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<StoreAddPreData> call = service.storeAddPreData("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<StoreAddPreData>() {
            @Override
            public void onResponse(Call<StoreAddPreData> call, Response<StoreAddPreData> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("PreData..", "" + response.body().getData().getAllStoreCategories());
                        lst_store_category = (ArrayList<AllStoreCategory>) response.body().getData().getAllStoreCategories();

                        if (lst_store_category != null && lst_store_category.size() > 0) {
                            String[] storeCategory = new String[lst_store_category.size()];

                            for (int i = 0; i < lst_store_category.size(); i++) {
                                storeCategory[i] = lst_store_category.get(i).getSCatTitle();
                                CategorySpinner.setOnItemSelectedListener(AddStoreActivity.this);

                                Log.e("storeCategory ", "" + storeCategory[i]);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddStoreActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        storeCategory);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                CategorySpinner.setAdapter(spinnerArrayAdapter);

                            }
                        }


                        assert lst_store_category != null;
                        Log.e("lst_store_category ", "" + lst_store_category.size());
                    } else {
                        clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddStoreActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<StoreAddPreData> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddStoreActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            IdstoreCategories = lst_store_category.get(i).getIdstoreCategories();
            Log.e("onItemSelected", "" + IdstoreCategories);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GooglePlacePickerActivity.REQUEST_LOCATION && resultCode ==
                Activity.RESULT_OK) {
            String placeName = data.getStringExtra("place");
            Log.e("AddStoreActivity.....", "" + placeName);
            String latLng = data.getStringExtra("latLng");
            Log.e("latLng_add_store", "" + latLng);
            String[] namesList = latLng.split(",");
            latitude = namesList[0];
            longitude = namesList[1];

            Log.e("latLng_add_store", "" + latitude);
            Log.e("latLng_add_store", "" + longitude);
            locations.setText(placeName);
        }

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode ==
                Activity.RESULT_OK) {
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
                certificate_image.setImageBitmap(bitmap);
            }
        }

        if (requestCode == PICK_PHOTO_FOR_LOGO && resultCode ==
                Activity.RESULT_OK) {
            if (data.getData() == null) {
                bitmap1 = (Bitmap) data.getExtras().get("data");
            } else {
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bitmap1) {
                logo_image.setImageBitmap(bitmap1);
            }

        }

        if (requestCode == PICK_PHOTO_FOR_STORE_IMAGES && resultCode ==
                Activity.RESULT_OK) {

            if (data.getData() == null) {
                bitmap2 = (Bitmap) data.getExtras().get("data");
            } else {
                try {
                    bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bitmap2) {
                storephoto_image.setImageBitmap(bitmap2);
            }
        }
    }


    public void makeJSON() {
        JSONObject mondayJson = new JSONObject();
        try {
            mondayJson.put("dow", "1");
            mondayJson.put("status", mondayStatus);
            mondayJson.put("starttime", mondayStartTime.getText().toString());
            mondayJson.put("endtime", mondayEndtTime.getText().toString());
        } catch (JSONException e) {

            e.printStackTrace();
        }

        JSONObject tuesdayJson = new JSONObject();
        try {
            tuesdayJson.put("dow", "2");
            tuesdayJson.put("status", tuesdayStatus);
            tuesdayJson.put("starttime", tuesdayStartTime.getText().toString());
            tuesdayJson.put("endtime", tuesdayEndtTime.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject wednesdayJson = new JSONObject();
        try {
            wednesdayJson.put("dow", "3");
            wednesdayJson.put("status", wednesdayStatus);
            wednesdayJson.put("starttime", wednesdayStartTime.getText().toString());
            wednesdayJson.put("endtime", wednesdayEndtTime.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject thursdayJson = new JSONObject();
        try {
            thursdayJson.put("dow", "4");
            thursdayJson.put("status", thursdayStatus);
            thursdayJson.put("starttime", thursdayStartTime.getText().toString());
            thursdayJson.put("endtime", thursdayEndtTime.getText().toString());
        } catch (JSONException e) {

            e.printStackTrace();
        }
        JSONObject fridayJson = new JSONObject();
        try {
            fridayJson.put("dow", "5");
            fridayJson.put("status", fridayStatus);
            fridayJson.put("starttime", fridayStartTime.getText().toString());
            fridayJson.put("endtime", fridayEndtTime.getText().toString());
        } catch (JSONException e) {

            e.printStackTrace();
        }
        JSONObject saturdayJson = new JSONObject();
        try {
            saturdayJson.put("dow", "6");
            saturdayJson.put("status", saturdayStatus);
            saturdayJson.put("starttime", saturdayStartTime.getText().toString());
            saturdayJson.put("endtime", saturdayEndtTime.getText().toString());
        } catch (JSONException e) {

            e.printStackTrace();
        }
        JSONObject sundayJson = new JSONObject();
        try {
            sundayJson.put("dow", "7");
            sundayJson.put("status", sundayStatus);
            sundayJson.put("starttime", sundayStartTime.getText().toString());
            sundayJson.put("endtime", sundayEndtTime.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray = new JSONArray();
        jsonArray.put(mondayJson);
        jsonArray.put(tuesdayJson);
        jsonArray.put(wednesdayJson);
        jsonArray.put(thursdayJson);
        jsonArray.put(fridayJson);
        jsonArray.put(saturdayJson);
        jsonArray.put(sundayJson);

    }

    @SuppressLint("IntentReset")
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @SuppressLint("IntentReset")
    public void pickImageLogo() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent1.setType("image/*");
        startActivityForResult(intent1, PICK_PHOTO_FOR_LOGO);
    }

    @SuppressLint("IntentReset")
    public void pickImageStoreImages() {
        Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent2.setType("image/*");
        startActivityForResult(intent2, PICK_PHOTO_FOR_STORE_IMAGES);
    }
}