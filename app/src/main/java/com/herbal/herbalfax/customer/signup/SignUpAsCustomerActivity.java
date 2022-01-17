package com.herbal.herbalfax.customer.signup;

import static com.herbal.herbalfax.api.RetrofitClientInstance.DEVICE_PLATFORM;
import static com.herbal.herbalfax.api.RetrofitClientInstance.DEVICE_TYPE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.terms.TermAndConditionActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.VerifyCodeAfterSignUpActivity;
import com.herbal.herbalfax.customer.signup.presignupmodel.AllCountry;
import com.herbal.herbalfax.customer.signup.presignupmodel.AllProfession;
import com.herbal.herbalfax.customer.signup.presignupmodel.PreSignUpData;
import com.herbal.herbalfax.customer.signup.signupmodel.RegisterResult;
import com.herbal.herbalfax.customer.signup.signupmodel.User;
import com.herbal.herbalfax.databinding.ActivitySignUpAsCustomerBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpAsCustomerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText profession;
    public int selectedResolutionPosition = -1;
    private ActivitySignUpAsCustomerBinding binding;
    private CommonClass clsCommon;
    private ArrayList<AllProfession> lst_profession;
    private ArrayList<AllCountry> lst_country = new ArrayList<>();
    int userType;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 150;
    private SessionPref pref;
    RadioButton genderradioButton;
    RadioGroup radioGroup;
    Spinner spinner;
    CheckBox termCheckBox;
    EditText birthdayEdt;
    String professionalId;
    String genderInt;
    ImageView profile_image;
    FrameLayout ll_image;
    final Calendar myCalendar = Calendar.getInstance();
    Bitmap bitmap = null;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = SessionPref.getInstance(this);

        clsCommon = new CommonClass();
        SignUpAsCustomerViewModel signUpAsCustomerViewModel = new SignUpAsCustomerViewModel();
        binding = DataBindingUtil.setContentView(SignUpAsCustomerActivity.this, R.layout.activity_sign_up_as_customer);
        binding.setLifecycleOwner(this);
        binding.setSignUpAsCustomerViewModel(signUpAsCustomerViewModel);
        Bundle extras = getIntent().getExtras();
        radioGroup = findViewById(R.id.radioGroup1);
        termCheckBox = findViewById(R.id.termCheckBox);
        birthdayEdt = findViewById(R.id.birthday);
        ll_image = findViewById(R.id.ll_image);
        profile_image = findViewById(R.id.profile_image);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        birthdayEdt.setOnClickListener(v -> {

            DatePickerDialog dialog = new DatePickerDialog(SignUpAsCustomerActivity.this,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            dialog.show();
        });

        spinner = findViewById(R.id.spinner);
        callPreSignUpDataAPI();


        if (extras != null) {
            userType = getIntent().getExtras().getInt("selectedType");
            Log.e("data ", "" + userType);
        }

        signUpAsCustomerViewModel.onGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(SignUpAsCustomerActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickImage();

        });


//        ll_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String[] PERMISSIONS = {
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                };
//                ActivityCompat.requestPermissions(SignUpAsCustomerActivity.this,
//                        PERMISSIONS,
//                        ALL_PERMISSIONS_RESULT);
//
//
//                pickImage();
//            }
//        });

        signUpAsCustomerViewModel.OnLoginHereClick().observe(this, loginUser -> {
            Intent mIntent = new Intent(SignUpAsCustomerActivity.this, LoginActivity.class);
            startActivity(mIntent);
        });


        signUpAsCustomerViewModel.getRegisterUser().observe(this, registerUser -> {

            int selectedId = radioGroup.getCheckedRadioButtonId();
            genderradioButton = findViewById(selectedId);
            if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getFullName())) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Full Name", "Ok");
            } else if (birthdayEdt.getText().toString().equals("")) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter BirthDate", "Ok");
            } else if (selectedId == -1) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Select Gender", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getCity())) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter City", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getZipCode())) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Zipcode", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getEmail())) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Email", "Ok");
            } else if (spinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Country code", "Ok");
            } else if (profession.getText().toString().equals("")) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Profession", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPhoneNo())) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Phone Number", "Ok");
            } else if ((registerUser).getPhoneNo().length() < 10) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Proper Phone Number", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPassword())) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Enter Password", "Ok");
            } else if (!binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Please enter correct password", "Ok");
            } else if (!termCheckBox.isChecked()) {
                clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "Please agree Term & conditions", "Ok");
            } else {
                callSignUpAPI(registerUser, userType);
            }


        });

        TextView term = findViewById(R.id.term);
        term.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TermAndConditionActivity.class);
            startActivity(intent);
        });
        profession = findViewById(R.id.professionedt);
        profession.setOnClickListener(view -> {
            try {
                clsCommon.hideKeyboard(view, SignUpAsCustomerActivity.this);
                radioButtonDialogWithList(getString(R.string.app_name), lst_profession);  //getResources().getStringArray(R.array.example_items)
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdayEdt.setText(sdf.format(myCalendar.getTime()));
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    private String getFCM() {
        String fcmID = pref.getStringVal(SessionPref.LoginUserFCMID);
        if (fcmID == null) {
            fcmID = "99999999";// no fcmid
        }
        return fcmID;
    }

    private String getDeviceID() {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
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
                }
                else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != bitmap) {
                    profile_image.setImageBitmap(bitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void nextPage(RegisterUser registerUser) {
        Intent mIntent = new Intent(SignUpAsCustomerActivity.this, VerifyCodeAfterSignUpActivity.class);
        mIntent.putExtra("Email", registerUser.getEmail());
        startActivity(mIntent);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void radioButtonDialogWithList(String dialogTitle, final ArrayList<AllProfession> array) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.professional_dialog_layout);

        Button add = dialog.findViewById(R.id.add);
        EditText other = dialog.findViewById(R.id.otherprofession);
        ImageView cancel = dialog.findViewById(R.id.cancel);

        final RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{getResources().getColor(R.color.text_grey)}
                },
                new int[]{getResources().getColor(R.color.text_grey)}
        );


        for (AllProfession item : array) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setHighlightColor(getResources().getColor(R.color.black));
            radioButton.setText(item.getProfTitle());
            radioButton.setTextColor(getResources().getColor(R.color.text_grey));
            radioButton.setButtonTintList(myColorStateList);
            radioGroup.addView(radioButton);
        }

        if (selectedResolutionPosition != -1 && array.size() > selectedResolutionPosition) {
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getChildAt(selectedResolutionPosition);
            selectedRadioButton.setChecked(true);
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                if (radioButtonID != -1) {
                    View radioButton = radioGroup.findViewById(radioButtonID);
                    int index = radioGroup.indexOfChild(radioButton);
                    profession.setText(getString(R.string.selected, array.get(index).getProfTitle()));
                    professionalId = array.get(index).getIdprofessions();
                    Log.e("professionalId  ", "" + professionalId);
                    selectedResolutionPosition = index;
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void callPreSignUpDataAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<PreSignUpData> call = service.signupPreData(hashMap);
        call.enqueue(new Callback<PreSignUpData>() {
            @Override
            public void onResponse(Call<PreSignUpData> call, Response<PreSignUpData> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        Log.e("", "" + response.body());
                        lst_profession = (ArrayList<AllProfession>) response.body().getData().getAllProfessions();
                        lst_country = (ArrayList<AllCountry>) response.body().getData().getAllCountries();


                        if (lst_country != null && lst_country.size() > 0) {
                            String[] countryCode = new String[lst_country.size()];

                            for (int i = 0; i < lst_country.size(); i++) {
                                countryCode[i] = lst_country.get(i).getCounCode();
                                spinner.setOnItemSelectedListener(SignUpAsCustomerActivity.this);

                                Log.e("countryCode ", "" + countryCode[i]);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SignUpAsCustomerActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        countryCode);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);

                            }
                        }


                        Log.e("lst_country ", "" + lst_country.size());
                    } else {
                        clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(SignUpAsCustomerActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<PreSignUpData> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(SignUpAsCustomerActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item

        try {
            String item = adapterView.getItemAtPosition(i).toString();
            ((TextView) adapterView.getChildAt(i)).setTextColor(Color.parseColor("#8E8E8E"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void callSignUpAPI(RegisterUser registerUser, int userType) {
        String genderStr = String.valueOf(genderradioButton.getText());

        try {
            if (genderStr.equals("Male")) {
                genderInt = "1";
            } else if (genderStr.equals("Female")) {
                genderInt = "2";
            } else {
                genderInt = "3";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(SignUpAsCustomerActivity.this);
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


//      RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), f));
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), registerUser.getFullName());
        RequestBody UDOB = RequestBody.create(MediaType.parse("text/plain"), birthdayEdt.getText().toString());
        RequestBody UGender = RequestBody.create(MediaType.parse("text/plain"), genderInt);
        RequestBody UCity = RequestBody.create(MediaType.parse("text/plain"), registerUser.getCity());
        RequestBody UEmailAddress = RequestBody.create(MediaType.parse("text/plain"), registerUser.getEmail());
        RequestBody UZipCode = RequestBody.create(MediaType.parse("text/plain"), registerUser.getZipCode());
        RequestBody UProfessionOther = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody UIAgree = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody UProfession = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(professionalId));
        RequestBody UMobNoCode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinner.getSelectedItem()));
        RequestBody UMobNo = RequestBody.create(MediaType.parse("text/plain"), registerUser.getPhoneNo());
        RequestBody UPassword = RequestBody.create(MediaType.parse("text/plain"), registerUser.getPassword());
        RequestBody UserType = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userType));
        RequestBody UDevUID = RequestBody.create(MediaType.parse("text/plain"), getDeviceID());
        RequestBody UDevToken = RequestBody.create(MediaType.parse("text/plain"), getFCM());
        RequestBody UDevType = RequestBody.create(MediaType.parse("text/plain"), DEVICE_TYPE);
        RequestBody UDevPlatform = RequestBody.create(MediaType.parse("text/plain"), DEVICE_PLATFORM);

        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("UFullName", name);
        hashMap.put("UDOB", UDOB);
        hashMap.put("UGender", UGender);  //1- male , 2 - female ,3 - other
        hashMap.put("UCity", UCity);
        hashMap.put("UDevType", UDevType);
        hashMap.put("UDevPlatform", UDevPlatform);
        hashMap.put("UEmailAddress", UEmailAddress);
        hashMap.put("UZipCode", UZipCode);
        hashMap.put("UProfessionOther", UProfessionOther);
        hashMap.put("UIAgree", UIAgree);
        hashMap.put("UProfession", UProfession);
        hashMap.put("UMobNoCode", UMobNoCode); //professionalId
        hashMap.put("UMobNo", UMobNo);
        hashMap.put("UPassword", UPassword);
        hashMap.put("UserType", UserType);
        hashMap.put("UDevUID", UDevUID);
        hashMap.put("UDevToken", UDevToken);
        // hashMap.put("UProPic", fbody);


        MultipartBody.Part filePart = MultipartBody.Part.createFormData("UProPic", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<RegisterResult> call = service.signUp(hashMap, filePart);
        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResult> call, @NonNull Response<RegisterResult> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {

                            User user = response.body().getData().getUser();
                            SessionPref.getInstance(SignUpAsCustomerActivity.this).saveLoginUser(
                                    user.getIdusers(),
                                    user.getUserType(),
                                    user.getUFullName(),
                                    user.getUDOB(),
                                    user.getUGender(),
                                    user.getUCity(),
                                    user.getUEmailAddress(),
                                    user.getUEmailVerified(),
                                    user.getUMobNoCode(),
                                    user.getUMobNo(),
                                    user.getUProPic(),
                                    user.getUActive(),
                                    user.getUDevToken(),
                                    user.getJwtToken(),
                                    user.getProfTitle()
                            );
                            try {
                                emailVerifyDialog1 cdd = new emailVerifyDialog1(SignUpAsCustomerActivity.this, registerUser);
                                cdd.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //    nextPage(registerUser);
                        } else {
                            Log.e("Error", "" + response.body().getErrors().toString());
                            Log.e("Error", "" + response.body().getErrors());
                            clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.e("ErrorElse", "" + jObjError.toString());

                        Log.e("ErrorElse", "" + jObjError.getString("errors"));
                        Log.e("ErrorElse", "" + jObjError.getString("message"));
                        clsCommon.showDialogMsg(SignUpAsCustomerActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(SignUpAsCustomerActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RegisterResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(SignUpAsCustomerActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class emailVerifyDialog1 extends Dialog implements android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes;
        ImageView no;
        TextView text_dialog2;
        RegisterUser registerUser;

        public emailVerifyDialog1(Activity a, RegisterUser registerUser) {
            super(a);
            this.registerUser = registerUser;
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.email_verification_dialog);
            yes = findViewById(R.id.send_button1);
            text_dialog2 = findViewById(R.id.text_dialog2);
            String email = registerUser.getEmail();
            String maskingEmail = email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
            Log.e("maskingEmail ", "" + maskingEmail);

            text_dialog2.setText(maskingEmail);
            no = findViewById(R.id.cancel);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.send_button1:

                    nextPage(registerUser);
                    c.finish();
                    break;
                case R.id.cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }


}
