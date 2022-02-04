package com.herbal.herbalfax.customer.homescreen.askfax;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.addaf.AddQuestionAskFaxActivity;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel.AfQuestion;
import com.herbal.herbalfax.customer.homescreen.askfax.aflist.askfaxlistmodel.AskFaxListResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.sharestory.CreateBlog;
import com.herbal.herbalfax.customer.homescreen.askfax.sharestory.addsharestorymodel.AddBlogResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.sharestory.sharestorypredatamodel.AllBlogCategory;
import com.herbal.herbalfax.customer.homescreen.askfax.sharestory.sharestorypredatamodel.ShareStoryPreData;
import com.herbal.herbalfax.customer.homescreen.bottomsheetaddpara.AddParaBottomSheet;
import com.herbal.herbalfax.customer.homescreen.placepicker.GooglePlacePickerActivity;
import com.herbal.herbalfax.databinding.FragmentAskfaxBinding;

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


public class AskFaxFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public int selectedResolutionPosition = -1;

    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    AskFaxAdapter askFaxAdapter;
    CommonClass clsCommon;
    ArrayList<AllBlogCategory> lst_blog_pre_data;

    Bitmap bitmap;
    private String IdBlogCategories;
    EditText categoryEdt;
    String categoryId;
    FragmentAskfaxBinding binding;
    String IdBlog;

    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 150;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AskFaxViewModel askFaxViewModel = ViewModelProviders.of(requireActivity()).get(AskFaxViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setAskFaxViewModel(askFaxViewModel);
        askFaxViewModel.onGalleryClick().observe(requireActivity(), click -> {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(requireActivity(),
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickImage();

        });
        askFaxViewModel.getSaveBlogs().observe(requireActivity(), createBlog -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(createBlog).getBlogTitle())) {
                clsCommon.showDialogMsgFrag(requireActivity(), "HerbalFax", "Enter Blog Title", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(createBlog).getBlogDesc())) {
                clsCommon.showDialogMsgFrag(requireActivity(), "HerbalFax", "Enter Blog description", "Ok");
            } else

                if (binding.categorySpinner.getSelectedItem().equals("")) {
                clsCommon.showDialogMsgFrag(requireActivity(), "HerbalFax", "Enter Category", "Ok");
            } else {
                callAddBlogAPI(createBlog);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_askfax, container, false);
        clsCommon = CommonClass.getInstance();
        callAskFaxQuestionListApi();
        binding.askFaxTxt.setOnClickListener(view -> {
            binding.recyclerViewAskFax.setVisibility(View.VISIBLE);
            binding.llSharestory.setVisibility(View.GONE);
            binding.askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            binding.shareTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            binding.shareStoryTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            binding.askFaxTxt.setTextColor(getResources().getColor(R.color.white));
            binding.shareTxt.setTextColor(getResources().getColor(R.color.black));
            binding.shareStoryTxt.setTextColor(getResources().getColor(R.color.black));

            Drawable img3 = requireContext().getResources().getDrawable(R.drawable.ic_icon_post_white);
            img3.setBounds(0, 0, 60, 60);
            binding.askFaxTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img4 = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img4.setBounds(0, 0, 60, 60);
            binding.shareStoryTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img.setBounds(0, 0, 60, 60);
            binding.shareTxt.setCompoundDrawables(img, null, null, null);
        });


        binding.shareStoryTxt.setOnClickListener(view -> {
            binding.recyclerViewAskFax.setVisibility(View.GONE);
            binding.llSharestory.setVisibility(View.VISIBLE);

            binding.askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            binding.shareTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            binding.shareStoryTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));

            binding.askFaxTxt.setTextColor(getResources().getColor(R.color.black));
            binding.shareTxt.setTextColor(getResources().getColor(R.color.black));
            binding.shareStoryTxt.setTextColor(getResources().getColor(R.color.white));

            Drawable img4 = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover_white);
            img4.setBounds(0, 0, 60, 60);
            binding.shareStoryTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img3 = requireContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img3.setBounds(0, 0, 60, 60);
            binding.askFaxTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img.setBounds(0, 0, 60, 60);
            binding.shareTxt.setCompoundDrawables(img, null, null, null);

        });
        binding.shareTxt.setOnClickListener(view -> {
            binding.llSharestory.setVisibility(View.GONE);
            binding.recyclerViewAskFax.setVisibility(View.GONE);


            binding.askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            binding.shareTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            binding.shareStoryTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

            binding.askFaxTxt.setTextColor(getResources().getColor(R.color.black));
            binding.shareTxt.setTextColor(getResources().getColor(R.color.white));
            binding.shareStoryTxt.setTextColor(getResources().getColor(R.color.black));

            Drawable img4 = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img4.setBounds(0, 0, 60, 60);
            binding.shareStoryTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img3 = requireContext().getResources().getDrawable(R.drawable.ic_icon_ionic_md_chatbubbles);
            img3.setBounds(0, 0, 60, 60);
            binding.askFaxTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover_white);
            img.setBounds(0, 0, 60, 60);
            binding.shareTxt.setCompoundDrawables(img, null, null, null);


        });
        binding.back.setOnClickListener(view -> requireActivity().onBackPressed());
        //  setUpAskFaxRecyclerView();

        binding.addQuestionTxt.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddQuestionAskFaxActivity.class);
            startActivity(intent);
        });
        callAddBlogPreDataAPI();

        binding.categoryEdt.setOnClickListener(view -> {
            try {
                clsCommon.hideKeyboard(view, (AppCompatActivity) getActivity());
                radioButtonDialogWithList("Select Blog Category", lst_blog_pre_data);
                //getResources().getStringArray(R.array.example_items)
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        binding.addPara.setOnClickListener(view1 -> {
            showBottomSheet(IdBlog);
        });
        return binding.getRoot();
    }


    private void showBottomSheet(String adapterPosition) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AddParaBottomSheet sheet = new AddParaBottomSheet(AskFaxFragment.this, adapterPosition);
        sheet.show(fragmentManager, "comment bottom sheet");
    }

    @SuppressLint("IntentReset")
    public void pickImage() {
        @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    public void callAskFaxQuestionListApi() {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "");
        hashMap.put("offset", "");


        Call<AskFaxListResponse> call = service.userAskFaxQuestionsList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<AskFaxListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AskFaxListResponse> call, @NonNull Response<AskFaxListResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        ArrayList<AfQuestion> lst_askFax = (ArrayList<AfQuestion>) response.body().getData().getAfQuestions();

                        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recyclerViewAskFax.setLayoutManager(RecyclerViewLayoutManager);
                        askFaxAdapter = new AskFaxAdapter(lst_askFax, getActivity());
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        binding.recyclerViewAskFax.setLayoutManager(HorizontalLayout);
                        binding.recyclerViewAskFax.setAdapter(askFaxAdapter);

                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AskFaxListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callAddBlogAPI(CreateBlog createBlog) {

        //create a file to write bitmap data
        File f = new File(getActivity().getCacheDir(), "blogs");
        try {
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SessionPref pref = SessionPref.getInstance(getActivity());

        RequestBody BlogCategory = RequestBody.create(MediaType.parse("text/plain"), IdBlogCategories);
        RequestBody BlogTitle = RequestBody.create(MediaType.parse("text/plain"), createBlog.getBlogTitle());
        RequestBody BlogDesc = RequestBody.create(MediaType.parse("text/plain"), createBlog.getBlogDesc());
        RequestBody BlogURL = RequestBody.create(MediaType.parse("text/plain"), createBlog.getBlogURL());

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("BlogImage", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<AddBlogResponse> call = service.userAddBlog("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), BlogCategory, BlogTitle, BlogDesc, BlogURL, filePart);
        call.enqueue(new Callback<AddBlogResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddBlogResponse> call, @NonNull Response<AddBlogResponse> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                            binding.addPara.setVisibility(View.VISIBLE);
                            IdBlog = response.body().getData().getBlogId();

                        } else {
                            clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddBlogResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                 Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void callAddBlogPreDataAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<ShareStoryPreData> call = service.userAddBlogPredata("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<ShareStoryPreData>() {
            @Override
            public void onResponse(@NonNull Call<ShareStoryPreData> call, @NonNull Response<ShareStoryPreData> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        lst_blog_pre_data = (ArrayList<AllBlogCategory>) response.body().getData().getAllBlogCategories();
                        assert lst_blog_pre_data != null;
                        Log.e("lst_blog_pre_data ", "" + lst_blog_pre_data.size());
                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<ShareStoryPreData> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            IdBlogCategories = lst_blog_pre_data.get(i).getIdblogCategories();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void radioButtonDialogWithList(String dialogTitle, final ArrayList<AllBlogCategory> array) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.professional_dialog_layout);

        Button add = dialog.findViewById(R.id.add);
        ImageView cancel = dialog.findViewById(R.id.cancel);
        TextView headingTxt = dialog.findViewById(R.id.headingTxt);
        headingTxt.setText(dialogTitle);
        final RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{getResources().getColor(R.color.text_grey)}
                },
                new int[]{getResources().getColor(R.color.text_grey)}
        );


        for (AllBlogCategory item : array) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setHighlightColor(getResources().getColor(R.color.black));
            radioButton.setText(item.getBCatTitle());
            radioButton.setTextColor(getResources().getColor(R.color.text_grey));
            radioButton.setButtonTintList(myColorStateList);
            radioGroup.addView(radioButton);
        }

        if (selectedResolutionPosition != -1 && array.size() > selectedResolutionPosition) {
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getChildAt(selectedResolutionPosition);
            selectedRadioButton.setChecked(true);
        }


        add.setOnClickListener(view -> {
            int radioButtonID = radioGroup.getCheckedRadioButtonId();
            if (radioButtonID != -1) {
                View radioButton = radioGroup.findViewById(radioButtonID);
                int index = radioGroup.indexOfChild(radioButton);
                binding.categoryEdt.setText(getString(R.string.selected, array.get(index).getBCatTitle()));
                categoryId = array.get(index).getIdblogCategories();
                Log.e("categoryId  blog", "" + categoryId);
                selectedResolutionPosition = index;
            }
            dialog.dismiss();
        });

        cancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
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
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != bitmap) {
                    binding.profileImage.setImageBitmap(bitmap);
                   // ll_pic.setVisibility(View.GONE);
                } else {
                  //  ll_pic.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
