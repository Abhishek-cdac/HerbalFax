package com.herbal.herbalfax.customer.homescreen.group.creategroup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.MemberAdapter;
import com.herbal.herbalfax.customer.homescreen.group.addmember.AddPeopleToGroupActivity;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.creategrpmodel.CreateGroupModel;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.model.GroupPreData;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.model.GroupPrivacyType;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.model.GroupType;
import com.herbal.herbalfax.customer.homescreen.placepicker.GooglePlacePickerActivity;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.databinding.ActivityCreateGroupBinding;
import com.herbal.herbalfax.vendor.sellerdeals.adddeal.AddDealsActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    MemberAdapter addMemberAdapter;
    RecyclerView memberRecyclerview;
    FrameLayout addmember;
    Button createGroup, cancelBtn;
    EditText groupNameEt, description;
    Spinner groupPrivacySpinner,groupTypeSpinner;
    CreateGroupsViewModel createGroupsViewModel;
String member;
ImageView groupImg, camera;
    private ArrayList<GroupPrivacyType> lst_privacy;
    private ArrayList<GroupType> lst_Type;
    private ArrayList<Interest> lst_member;
    CommonClass clsCommon;
    private String IdGroupType, IdGroupPrivacy;

    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createGroupsViewModel = new CreateGroupsViewModel();
        ActivityCreateGroupBinding binding = DataBindingUtil.setContentView(CreateGroupActivity.this, R.layout.activity_create_group);
        binding.setLifecycleOwner(this);
        binding.setCreateGroupsViewModel(createGroupsViewModel);

        Bundle extras = getIntent().getExtras();
        clsCommon = CommonClass.getInstance();
        if (extras != null) {
            member = getIntent().getExtras().getString("member");

            Log.e("CreateGroupActivity ", ""+ member);
        }
        description = findViewById(R.id.description);
        groupNameEt = findViewById(R.id.groupName);
        groupImg = findViewById(R.id.groupImg);
        createGroup = findViewById(R.id.createGroup);
        cancelBtn = findViewById(R.id.cancelBtn);
        camera = findViewById(R.id.camera);
        groupTypeSpinner = findViewById(R.id.groupTypeSpinner);
        memberRecyclerview = findViewById(R.id.memberrecyclerview);
        groupPrivacySpinner = findViewById(R.id.groupPrivacySpinner);
        addmember = findViewById(R.id.addmember);
        addmember.setOnClickListener(view -> {
            Intent intent= new Intent(CreateGroupActivity.this, AddPeopleToGroupActivity.class);
            startActivity(intent);
        });

        createGroupsViewModel.onGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(CreateGroupActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickImage();

        });

        createGroupsViewModel.getRegisterGroup().observe(this, newGroup -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(newGroup).getGroupName())) {
                clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", "Enter group Name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newGroup).getGroupDesc())) {
                clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", "Enter description", "Ok");
            }  else if (groupPrivacySpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", "Select Privacy", "Ok");
            } else if (groupTypeSpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", "select Type", "Ok");
            } else if (bitmap == null) {
                clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", "Please select Image", "Ok");
            } else {
              /*  if (type != null && type.equals("edit")) {

                    callUpdateProductAPI(storeId, newGroup, jsonArray.toString(), latitude, longitude);
                } else {

                    callAddProductDealAPI(newGroup);
                }*/
                callCreateGroupApi(newGroup, IdGroupType, IdGroupPrivacy);


            }

        });
        cancelBtn.setOnClickListener(view -> {
         onBackPressed();
        });
        callGroupPreDataAPI();
        setUpmemberRecyclerView();
    }
    @SuppressLint("IntentReset")
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    private void callGroupPreDataAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<GroupPreData> call = service.userGroupCreateData("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<GroupPreData>() {
            @Override
            public void onResponse(@NonNull Call<GroupPreData> call, @NonNull Response<GroupPreData> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        try {
                            lst_privacy = (ArrayList<GroupPrivacyType>) response.body().getData().getGroupPrivacyTypes();

                            if (lst_privacy != null && lst_privacy.size() > 0) {
                                String[] storeCategory = new String[lst_privacy.size()];

                                for (int i = 0; i < lst_privacy.size(); i++) {
                                    storeCategory[i] = lst_privacy.get(i).getGPTTitle();
                                    groupPrivacySpinner.setOnItemSelectedListener(CreateGroupActivity.this);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CreateGroupActivity.this,
                                            android.R.layout.simple_spinner_item,
                                            storeCategory);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    groupPrivacySpinner.setAdapter(spinnerArrayAdapter);
                                }

                                lst_Type = (ArrayList<GroupType>) response.body().getData().getGroupTypes();

                                if (lst_Type != null && lst_Type.size() > 0) {
                                    String[] storeType = new String[lst_Type.size()];

                                    for (int i = 0; i < lst_Type.size(); i++) {
                                        storeType[i] = lst_Type.get(i).getGrpTypeTitle();
                                        groupTypeSpinner.setOnItemSelectedListener(CreateGroupActivity.this);
                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(CreateGroupActivity.this,
                                                android.R.layout.simple_spinner_item,
                                                storeType);
                                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                        groupTypeSpinner.setAdapter(spinnerArrayAdapter);
                                    }
                                }
                            }
                            assert lst_privacy != null;
                            assert lst_Type != null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(CreateGroupActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<GroupPreData> call, @NonNull Throwable t) {
                t.printStackTrace();

                Toast.makeText(CreateGroupActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setUpmemberRecyclerView() {
        lst_member = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        memberRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        addMemberAdapter = new MemberAdapter(lst_member, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(CreateGroupActivity.this, LinearLayoutManager.HORIZONTAL, false);
        memberRecyclerview.setLayoutManager(HorizontalLayout);
        memberRecyclerview.setAdapter(addMemberAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            IdGroupType = lst_Type.get(i).getIdgroupTypes();
             IdGroupPrivacy = lst_privacy.get(i).getIdgroupPrivacyTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Bitmap bitmap;
    private void callCreateGroupApi(NewGroup newGroup, String idGroupType, String idGroupPrivacy) {
        SessionPref pref = SessionPref.getInstance(this);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        //create a file to write bitmap data
        File f = new File(getCacheDir(), "group");
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

        RequestBody groupName = RequestBody.create(MediaType.parse("text/plain"), newGroup.groupName);
        RequestBody groupPrivacy = RequestBody.create(MediaType.parse("text/plain"), idGroupPrivacy);
        RequestBody groupMember = RequestBody.create(MediaType.parse("text/plain"), member);
        RequestBody groupType = RequestBody.create(MediaType.parse("text/plain"), idGroupType);
         RequestBody groupDesc = RequestBody.create(MediaType.parse("text/plain"), newGroup.groupDesc);

           Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("GrpName", groupName);
        hashMap.put("GrpDesc", groupDesc );
        hashMap.put("GrpPrivacy", groupPrivacy);
        hashMap.put("GrpType", groupType);
        hashMap.put("GrpUserList", groupMember);
       

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("GrpImage", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<CreateGroupModel> call = service.userGroupCreate("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap, filePart);
        call.enqueue(new Callback<CreateGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateGroupModel> call, @NonNull Response<CreateGroupModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {


                        } else {
                            clsCommon.showDialogMsg(CreateGroupActivity.this,
                                    "HerbalFax",
                                    "" + response.body().getErrors(),
                                    getString(R.string.ok));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(CreateGroupActivity.this,
                                "HerbalFax",
                                jObjError.getString("message"),
                                getString(R.string.ok));
                    } catch (Exception e) {
                        Toast.makeText(CreateGroupActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CreateGroupModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(CreateGroupActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                groupImg.setImageBitmap(bitmap);
                camera.setVisibility(View.GONE);
            } else {
                clsCommon.showDialogMsg(CreateGroupActivity.this, "HerbalFax", "Please select Image", "Ok");
            }
        }
    }
}