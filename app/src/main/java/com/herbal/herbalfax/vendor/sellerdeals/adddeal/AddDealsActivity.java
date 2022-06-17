package com.herbal.herbalfax.vendor.sellerdeals.adddeal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.placepicker.GooglePlacePickerActivity;
import com.herbal.herbalfax.databinding.ActivityAddDealsBinding;
import com.herbal.herbalfax.vendor.SellerLandingPageActivity;
import com.herbal.herbalfax.vendor.sellerdeals.adapter.AddImagesAdapter;
import com.herbal.herbalfax.vendor.sellerproduct.addproduct.AddProductActivity;
import com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel.ProductCategory;
import com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel.ProductCategoryResponse;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.Store;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.StoreListResponse;
import com.theartofdev.edmodo.cropper.CropImage;

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

public class AddDealsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AddDealsViewModel addDealsViewModel;
    private CommonClass clsCommon;
    Spinner categorySpinner, storeSpinner;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 1;
    private Bitmap bitmap = null;
    private File mFile;
    private Uri selectedImage;
    private ArrayList<Bitmap> productList=new ArrayList<>();
    private ArrayList<Uri> listUri=new ArrayList<>();
    private ArrayList<File> fileList=new ArrayList<>();
    private AddImagesAdapter addImagesAdapter;
    private RecyclerView recycleView;
    private TextView msgTxt;
    ImageView product_image, back;
    String IdstoreCategories, IdStore;
    ArrayList<Store> lst_store;
    private ArrayList<ProductCategory> lst_store_category;
    final Calendar myCalendar = Calendar.getInstance();
    EditText expirydate;
    EditText locations;
    String latitude = "", longitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDealsViewModel = new AddDealsViewModel();
        ActivityAddDealsBinding binding = DataBindingUtil.setContentView(AddDealsActivity.this, R.layout.activity_add_deals);
        binding.setLifecycleOwner(this);
        binding.setAddDealsViewModel(addDealsViewModel);
        clsCommon = CommonClass.getInstance();
        product_image = findViewById(R.id.product_image);
        msgTxt=findViewById(R.id.msgTxt);
        recycleView=findViewById(R.id.recycleView);
        categorySpinner = findViewById(R.id.categorySpinner);
        expirydate = findViewById(R.id.expirydate);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        storeSpinner = findViewById(R.id.spinner);
        callStorePreDataAPI();
        callStoreListAPI();

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

        locations = findViewById(R.id.locations);

        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GooglePlacePickerActivity.class);
                startActivityForResult(intent, GooglePlacePickerActivity.REQUEST_LOCATION);
            }
        });

        expirydate.setOnClickListener(v -> {

            DatePickerDialog dialog = new DatePickerDialog(AddDealsActivity.this,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());

            dialog.show();
        });

        addDealsViewModel.onGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AddDealsActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            if(fileList.size()<5)
            {
                pickImage();
            }else{
                Toast.makeText(AddDealsActivity.this,"limit of product is 5",Toast.LENGTH_SHORT).show();
            }


        });

        addDealsViewModel.getRegisterProduct().observe(this, newProduct -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductDealName())) {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Enter Deal Name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductDealPrice())) {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Enter Price", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductDealDate())) {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Enter Expiry Date", "Ok");
            } else if (categorySpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Enter Deal category", "Ok");
            } else if (storeSpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Enter Store Name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductDealLocation())) {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Enter Location", "Ok");
            } else if (bitmap == null) {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Please select Image", "Ok");
            } else {
              /*  if (type != null && type.equals("edit")) {

                    callUpdateProductAPI(storeId, newProduct, jsonArray.toString(), latitude, longitude);
                } else {

                    callAddProductDealAPI(newProduct);
                }*/
                callAddProductDealAPI(newProduct);
            }

        });
        initAdapter();

    }

    private void removeImages(int position) {
        productList.remove(position);
        fileList.remove(position);
        addImagesAdapter.notifyDataSetChanged();

        if(productList.size()==0)
        {
            recycleView.setVisibility(View.GONE);
            msgTxt.setVisibility(View.VISIBLE);

        }

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        expirydate.setText(sdf.format(myCalendar.getTime()));
    }

    private void callAddProductDealAPI(NewDeals newProduct) {

        SessionPref pref = SessionPref.getInstance(this);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        //create a file to write bitmap data
//        File f = new File(getCacheDir(), "product");
//        try {
//            f.createNewFile();
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
//            byte[] bitmapdata = bos.toByteArray();
//            //write the bytes in file
//            FileOutputStream fos = null;
//            fos = new FileOutputStream(f);
//            fos.write(bitmapdata);
//            fos.flush();
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        RequestBody productName = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDealName());
        RequestBody productCategories = RequestBody.create(MediaType.parse("text/plain"), IdstoreCategories);
        RequestBody storeId = RequestBody.create(MediaType.parse("text/plain"), IdStore);
        RequestBody productPrice = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDealPrice());
        RequestBody productQuantity = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDealQuantityNo());
        RequestBody productType = RequestBody.create(MediaType.parse("text/plain"), "2");
        RequestBody productDateExpiry = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDealDate());
        RequestBody ProductLocation = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDealLocation());

        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody ProductDesc = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody ProductShippingCost = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody productDistance = RequestBody.create(MediaType.parse("text/plain"), "");

        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("ProductName", productName);
        hashMap.put("ProductStoreId", storeId);
        hashMap.put("ProductCategory", productCategories);
        hashMap.put("ProductRate", productPrice);
        hashMap.put("Product_Expiry", productDateExpiry);
        hashMap.put("ProductQty", productQuantity);
        hashMap.put("ProductShippingCost", ProductShippingCost);
        hashMap.put("ProductPerKM", productDistance);
        hashMap.put("ProductType", productType);
        hashMap.put("ProductDesc", ProductDesc);
        hashMap.put("ProductLocation", ProductLocation);
        hashMap.put("ProductLat", Latitude);
        hashMap.put("ProductLong", Longitude);
        Log.e("DealsHashMap", "" + hashMap);
//      ArrayList<File> imageItems = new ArrayList();
//      imageItems.add(f);
////      items.add(f);
//      items.add(f);
        MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[productList.size()];
        File f;
        for(int i=0;i<fileList.size();i++)
        {
//            f = new File(listUri.get(i).getPath());
//             f= new File(getCacheDir(), "product");
//            try {
//                Bitmap bitmaps=productList.get(i);
//                f.createNewFile();
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bitmaps.compress(Bitmap.CompressFormat.JPEG, 40, bos);
//                byte[] bitmapdata = bos.toByteArray();
//                //write the bytes in file
//                FileOutputStream fos = null;
//                fos = new FileOutputStream(f);
//                fos.write(bitmapdata);
//                fos.flush();
//                fos.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), f);
//            multipartTypedOutput[i] = MultipartBody.Part.createFormData("imageFiles[]", f.getPath(), surveyBody);

            f=fileList.get(i);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), f);
            multipartTypedOutput[i] = MultipartBody.Part.createFormData("ProductPhotos[]", f.getPath(), surveyBody);
        }
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("ProductPhotos[]", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<CommonResponse> call = service.vendorProductAdd("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap, multipartTypedOutput);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            clsCommon.showDialogMsg(AddDealsActivity.this,
                                    "HerbalFax",
                                    "" + response.body().getMessage(),
                                    getString(R.string.ok));
                            Intent intent= new Intent(AddDealsActivity.this, SellerLandingPageActivity.class);
                            startActivity(intent);

                        } else {
                            clsCommon.showDialogMsg(AddDealsActivity.this,
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
                        clsCommon.showDialogMsg(AddDealsActivity.this,
                                "HerbalFa    x",
                                jObjError.getString("message"),
                                getString(R.string.ok));
                    } catch (Exception e) {
                        Toast.makeText(AddDealsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddDealsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void initAdapter() {
        addImagesAdapter = new AddImagesAdapter(AddDealsActivity.this, productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddDealsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(addImagesAdapter);
        addImagesAdapter.onItemClickMethod((View view, int position) -> {
            int i = view.getId();
            removeImages(position);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("IntentReset")
    public void pickImage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    private void callStoreListAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("VendorId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<StoreListResponse> call = service.storeList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<StoreListResponse>() {
            @Override
            public void onResponse(Call<StoreListResponse> call, Response<StoreListResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("PreData..", "" + response.body().getData().getStores());

                        try {
                            lst_store = (ArrayList<Store>) response.body().getData().getStores();

                            if (lst_store != null && lst_store.size() > 0) {
                                String[] storeCategory = new String[lst_store.size()];

                                for (int i = 0; i < lst_store.size(); i++) {
                                    storeCategory[i] = lst_store.get(i).getStoreName();
                                    storeSpinner.setOnItemSelectedListener(AddDealsActivity.this);

                                    Log.e("storeCategory ", "" + storeCategory[i]);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddDealsActivity.this,
                                            android.R.layout.simple_spinner_item,
                                            storeCategory);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    storeSpinner.setAdapter(spinnerArrayAdapter);

                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddDealsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<StoreListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddDealsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Set file.
     *
     * @param file the file
     */
    public void setFile(File file) {
        this.mFile = file;
    }

    /**
     * Get file.
     *
     * @return the file
     */
    public File getFile() {
        return mFile;
    }


    private void callStorePreDataAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<ProductCategoryResponse> call = service.userStoreProductCategoryList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<ProductCategoryResponse>() {
            @Override
            public void onResponse(Call<ProductCategoryResponse> call, Response<ProductCategoryResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("PreData..", "" + response.body().getData().getProductCategories());

                        try {
                            lst_store_category = (ArrayList<ProductCategory>) response.body().getData().getProductCategories();

                            if (lst_store_category != null && lst_store_category.size() > 0) {
                                String[] storeCategory = new String[lst_store_category.size()];

                                for (int i = 0; i < lst_store_category.size(); i++) {
                                    storeCategory[i] = lst_store_category.get(i).getSPCTitle();
                                    categorySpinner.setOnItemSelectedListener(AddDealsActivity.this);

                                    Log.e("DealsStoreCategory ", "" + storeCategory[i]);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddDealsActivity.this,
                                            android.R.layout.simple_spinner_item,
                                            storeCategory);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    categorySpinner.setAdapter(spinnerArrayAdapter);

                                }
                            }


                            assert lst_store_category != null;
                            Log.e("lst_store_category ", "" + lst_store_category.size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddDealsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<ProductCategoryResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddDealsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
        ((TextView) adapterView.getChildAt(0)).setTextSize(15);

        try {
            IdStore = lst_store.get(i).getIdstores();
            Log.e("onItemSelected", "" + IdStore);
            IdstoreCategories = lst_store_category.get(i).getIdstoreProductCategories();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GooglePlacePickerActivity.REQUEST_LOCATION && resultCode ==
                Activity.RESULT_OK) {
            String placeName = data.getStringExtra("place");
            Log.e("AddDealActivity.....", "" + placeName);
            String latLng = data.getStringExtra("latLng");
            Log.e("latLng_add_store", "" + latLng);
            String[] namesList = latLng.split(",");
            latitude = namesList[0];
            longitude = namesList[1];

            Log.e("latLng_add_deal", "" + latitude);
            Log.e("latLng_add_deal", "" + longitude);
            locations.setText(placeName);
        }
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode ==
                Activity.RESULT_OK) {
            if (data.getData() == null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                 selectedImage = data.getData();
                listUri.add(selectedImage);
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    productList.add(bitmap);
                    selectedImage = data.getData();
                    Uri selectedImage = data.getData();
                    CropImage.activity(selectedImage).setFixAspectRatio(false).setAspectRatio(5, 5).start(this);
//                    if(addImagesAdapter!=null)
//                    {
//                        if(productList.size()>0)
//                        {
//                            recycleView.setVisibility(View.VISIBLE);
//                            msgTxt.setVisibility(View.GONE);
//                        }
//                        addImagesAdapter.notifyDataSetChanged();
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bitmap) {
//                product_image.setImageBitmap(bitmap);
            } else {
                clsCommon.showDialogMsg(AddDealsActivity.this, "HerbalFax", "Please select Image", "Ok");
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());
                setFile(file);
                fileList.add(file);

                if(addImagesAdapter!=null)
                {
                    if(productList.size()>0)
                    {
                        recycleView.setVisibility(View.VISIBLE);
                        msgTxt.setVisibility(View.GONE);
                    }
                    addImagesAdapter.notifyDataSetChanged();
                }

                String profilePicture = resultUri.getPath();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

            }

        }
    }
}