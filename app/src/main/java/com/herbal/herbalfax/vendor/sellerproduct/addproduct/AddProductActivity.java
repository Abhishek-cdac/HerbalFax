package com.herbal.herbalfax.vendor.sellerproduct.addproduct;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.herbal.herbalfax.databinding.ActivityAddProductBinding;
import com.herbal.herbalfax.vendor.sellerdeals.adapter.AddImagesAdapter;
import com.herbal.herbalfax.vendor.sellerdeals.adddeal.AddDealsActivity;
import com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel.ProductCategory;
import com.herbal.herbalfax.vendor.sellerproduct.productcategorymodel.ProductCategoryResponse;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.Store;
import com.herbal.herbalfax.vendor.storelist.storelistmodel.StoreListResponse;

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

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AddProductViewModel addProductViewModel;
    private CommonClass clsCommon;
    Spinner categorySpinner, storeSpinner;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 1;
    String IdstoreCategories, IdStore;
    ArrayList<Store> lst_store;
    private ArrayList<Bitmap> productList=new ArrayList<>();
    private AddImagesAdapter addImagesAdapter;
    private TextView msgTxt;
    private RecyclerView recycleView;
    Bitmap bitmap = null;
    private ArrayList<ProductCategory> lst_store_category;
    ImageView product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addProductViewModel = new AddProductViewModel();
        ActivityAddProductBinding binding = DataBindingUtil.setContentView(AddProductActivity.this, R.layout.activity_add_product);
        binding.setLifecycleOwner(this);
        binding.setAddProductViewModel(addProductViewModel);
        clsCommon = CommonClass.getInstance();
        product_image = findViewById(R.id.product_image);
        recycleView=findViewById(R.id.recycleView);
        categorySpinner = findViewById(R.id.categorySpinner);
        storeSpinner = findViewById(R.id.spinner);
        msgTxt=findViewById(R.id.msgTxt);
        callStoreListAPI();
        callStorePreDataAPI();

        addProductViewModel.onGalleryClick().observe(this, click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(AddProductActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            if(productList.size()<5)
            {
                pickImage();
            }else{
                Toast.makeText(AddProductActivity.this,"limit of product is 5",Toast.LENGTH_SHORT).show();
            }


        });
        addProductViewModel.getRegisterProduct().observe(this, newProduct -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductName())) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Enter Product Name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductcost())) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Enter Cost", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductPrice())) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Enter Price", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductDescription())) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Enter Description", "Ok");
            } else if (categorySpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Enter Product category", "Ok");
            } else if (storeSpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Enter Store Name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(newProduct).getProductDistance())) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Enter Phone Distance Range", "Ok");
            } else if (bitmap == null) {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Please select Image", "Ok");

            } else {
              /*  if (type != null && type.equals("edit")) {

                    callUpdateProductAPI(storeId, newProduct, jsonArray.toString(), latitude, longitude);
                } else {

                    callAddProductAPI(newProduct);
                }*/
                callAddProductAPI(newProduct);

            }

        });
        initAdapter();
    }

    private void removeImages(int position) {
            productList.remove(position);
            addImagesAdapter.notifyDataSetChanged();
            if(productList.size()==0)
            {
                recycleView.setVisibility(View.GONE);
                msgTxt.setVisibility(View.VISIBLE);

            }

    }

    public void initAdapter() {

        addImagesAdapter = new AddImagesAdapter(AddProductActivity.this, productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(addImagesAdapter);
        addImagesAdapter.onItemClickMethod((View view, int position) -> {
            int i = view.getId();
            removeImages(position);

        });
    }

    /*  private void callAddProductAPI(NewProduct newProduct) {

          TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
          pd.show();


          File f = new File(getCacheDir(), "storeProduct");
          try {
              f.createNewFile();
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
              bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
              byte[] bitmapdata = bos.toByteArray();

              FileOutputStream fos;
              fos = new FileOutputStream(f);
              fos.write(bitmapdata);
              fos.flush();
              fos.close();
          } catch (Exception e) {
              e.printStackTrace();
          }


          SessionPref pref = SessionPref.getInstance(this);
          RequestBody productName = RequestBody.create(MediaType.parse("text/plain"),newProduct.getProductName());
          RequestBody productDesc = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDescription());
          RequestBody productCategories = RequestBody.create(MediaType.parse("text/plain"), IdstoreCategories);
          RequestBody productPrice = RequestBody.create(MediaType.parse("text/plain"),newProduct.getProductPrice());
          RequestBody productQuantity = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductQuantity());
          RequestBody productCost = RequestBody.create(MediaType.parse("text/plain"),newProduct.getProductcost());
          RequestBody productDistance = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDistance());
          RequestBody storeId = RequestBody.create(MediaType.parse("text/plain"), IdStore);

          Map<String, RequestBody> hashMap = new HashMap<>();
          hashMap.put("ProductStoreId", storeId);
          hashMap.put("ProductCategory", productCategories);
          hashMap.put("ProductName", productName);
          hashMap.put("ProductDesc", productDesc);
          hashMap.put("ProductRate",productPrice);
          hashMap.put("ProductQty", productQuantity);
          hashMap.put("ProductShippingCost",productCost );
          hashMap.put("ProductPerKM", productDistance);

          MultipartBody.Part filePart = MultipartBody.Part.createFormData("ProductPhotos[]", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));

          GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
          Call<CommonResponse> call = service.vendorProductAdd("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), filePart, hashMap);
          call.enqueue(new Callback<CommonResponse>() {
              @Override
              public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                  pd.dismiss();
                  if (response.code() == 200) {
                      Intent intent = new Intent(getApplicationContext(), SellerLandingPageActivity.class);
                      startActivity(intent);
                  } else {
                      new CommonClass().showDialogMsg(AddProductActivity.this, "PlayDate", "An error occurred!", "Ok");
                  }
              }

              @Override
              public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                  t.printStackTrace();
                  pd.dismiss();
              }
          });

      }

     */
    private void callAddProductAPI(NewProduct newProduct) {
        SessionPref pref = SessionPref.getInstance(this);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        //create a file to write bitmap data
        File f = new File(getCacheDir(), "product");
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

        RequestBody productName = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductName());
        RequestBody productDesc = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDescription());
        RequestBody productCategories = RequestBody.create(MediaType.parse("text/plain"), IdstoreCategories);
        RequestBody productPrice = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductPrice());
        RequestBody productQuantity = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductQuantity());
        RequestBody productCost = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductcost());
        RequestBody productDistance = RequestBody.create(MediaType.parse("text/plain"), newProduct.getProductDistance());
        RequestBody storeId = RequestBody.create(MediaType.parse("text/plain"), IdStore);
        RequestBody productType = RequestBody.create(MediaType.parse("text/plain"), "1");

        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("ProductStoreId", storeId);
        hashMap.put("ProductCategory", productCategories);
        hashMap.put("ProductName", productName);
        hashMap.put("ProductDesc", productDesc);
        hashMap.put("ProductRate", productPrice);
        hashMap.put("ProductQty", productQuantity);
        hashMap.put("ProductShippingCost", productCost);
        hashMap.put("ProductPerKM", productDistance);

        hashMap.put("ProductType", productType);

//      ArrayList<File> imageItems = new ArrayList();
//      imageItems.add(f);
////      items.add(f);
//      items.add(f);

//        MultipartBody.Builder builder = new MultipartBody.Builder();
//
//        File f=null;
//        for(int i=0;i<productList.size();i++)
//        {
//
//             f= new File(getCacheDir(), "product");
//            try {
//               Bitmap btm= productList.get(i);
//                f.createNewFile();
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                btm.compress(Bitmap.CompressFormat.JPEG, 40, bos);
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
//
//
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), f);
//            hashMap.put("ProductPhotos[]\"; filename=\"" + f.getName() + "\"", requestBody);
//
////            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
////            requestParam.put("images[]\"; filename=\"" + file.getName() + "\"", requestBody);
//
//        }
        Log.e("hashMap", "" + hashMap);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("ProductPhotos[]", f.getName(), RequestBody.create(MediaType.parse("image/*"),f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<CommonResponse> call = service.vendorProductAdd("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap, filePart);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            clsCommon.showDialogMsg(AddProductActivity.this,
                                    "HerbalFax",
                                    "" + response.body().getMessage(),
                                    getString(R.string.ok));
                            onBackPressed();
                        } else {
                            clsCommon.showDialogMsg(AddProductActivity.this,
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
                        clsCommon.showDialogMsg(AddProductActivity.this,
                                "HerbalFax",
                                jObjError.getString("message"),
                                getString(R.string.ok));
                    } catch (Exception e) {
                        Toast.makeText(AddProductActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddProductActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("IntentReset")
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
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
                                    categorySpinner.setOnItemSelectedListener(AddProductActivity.this);

                                    Log.e("storeCategory ", "" + storeCategory[i]);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddProductActivity.this,
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
                        clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddProductActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<ProductCategoryResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddProductActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
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
                    productList.add(bitmap);
                    if(addImagesAdapter!=null)
                    {
                        if(productList.size()>0)
                        {
                            recycleView.setVisibility(View.VISIBLE);
                            msgTxt.setVisibility(View.GONE);
                        }
                        addImagesAdapter.notifyDataSetChanged();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bitmap) {
//                product_image.setImageBitmap(bitmap);
            } else {
                clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", "Please select Image", "Ok");
            }
        }
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
                                    storeSpinner.setOnItemSelectedListener(AddProductActivity.this);

                                    Log.e("storeCategory ", "" + storeCategory[i]);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddProductActivity.this,
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
                        clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddProductActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddProductActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<StoreListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddProductActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
}