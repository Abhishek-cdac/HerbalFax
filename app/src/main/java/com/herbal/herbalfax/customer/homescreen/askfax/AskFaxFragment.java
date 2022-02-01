package com.herbal.herbalfax.customer.homescreen.askfax;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.herbal.herbalfax.customer.homescreen.askfax.sharestorypredatamodel.AllBlogCategory;
import com.herbal.herbalfax.customer.homescreen.askfax.sharestorypredatamodel.ShareStoryPreData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AskFaxFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public int selectedResolutionPosition = -1;
    private AskFaxViewModel askFaxViewModel;
    ImageView back;
    LinearLayout ll_sharestory;
    RecyclerView recyclerViewAskFax;
    LinearLayoutManager HorizontalLayout;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    AskFaxAdapter askFaxAdapter;
    TextView askFaxTxt, shareStoryTxt, shareTxt, addQuestionTxt;
    CommonClass clsCommon;
    ArrayList<AllBlogCategory> lst_blog_pre_data;
    Spinner categorySpinner;
    Bitmap bitmap;
    private String IdBlogCategories;
    EditText categoryEdt;
    String categoryId;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        askFaxViewModel = ViewModelProviders.of(this).get(AskFaxViewModel.class);
        View root = inflater.inflate(R.layout.fragment_askfax, container, false);
        clsCommon = CommonClass.getInstance();
        addQuestionTxt = root.findViewById(R.id.addQuestionTxt);
        ll_sharestory = root.findViewById(R.id.ll_sharestory);
        askFaxTxt = root.findViewById(R.id.askFaxTxt);
        categorySpinner = root.findViewById(R.id.categorySpinner);
        shareStoryTxt = root.findViewById(R.id.shareStoryTxt);
        shareTxt = root.findViewById(R.id.shareTxt);
        recyclerViewAskFax = root.findViewById(R.id.recyclerViewAskFax);
        back = root.findViewById(R.id.back);
        back.setOnClickListener(view -> requireActivity().onBackPressed());
        //  setUpAskFaxRecyclerView();
        callAskFaxQuestionListApi();
        addQuestionTxt.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddQuestionAskFaxActivity.class);
            startActivity(intent);
        });
        callAddBlogPreDataAPI();

        categoryEdt = root.findViewById(R.id.categoryEdt);
        categoryEdt.setOnClickListener(view -> {
            try {
                clsCommon.hideKeyboard(view, (AppCompatActivity) getActivity());
                radioButtonDialogWithList("Select Blog Category", lst_blog_pre_data);  //getResources().getStringArray(R.array.example_items)
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        askFaxTxt.setOnClickListener(view -> {


            recyclerViewAskFax.setVisibility(View.VISIBLE);
            ll_sharestory.setVisibility(View.GONE);
            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            shareTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            shareStoryTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            askFaxTxt.setTextColor(getResources().getColor(R.color.white));
            shareTxt.setTextColor(getResources().getColor(R.color.black));
            shareStoryTxt.setTextColor(getResources().getColor(R.color.black));

            Drawable img3 = requireContext().getResources().getDrawable(R.drawable.ic_icon_post_white);
            img3.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img4 = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img4.setBounds(0, 0, 60, 60);
            shareStoryTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img.setBounds(0, 0, 60, 60);
            shareTxt.setCompoundDrawables(img, null, null, null);


        });
        shareStoryTxt.setOnClickListener(view -> {
            recyclerViewAskFax.setVisibility(View.GONE);
            ll_sharestory.setVisibility(View.VISIBLE);

            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            shareTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            shareStoryTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));

            askFaxTxt.setTextColor(getResources().getColor(R.color.black));
            shareTxt.setTextColor(getResources().getColor(R.color.black));
            shareStoryTxt.setTextColor(getResources().getColor(R.color.white));

            Drawable img4 = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover_white);
            img4.setBounds(0, 0, 60, 60);
            shareStoryTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img3 = requireContext().getResources().getDrawable(R.drawable.ic_icon_post_black);
            img3.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img.setBounds(0, 0, 60, 60);
            shareTxt.setCompoundDrawables(img, null, null, null);

        });
        shareTxt.setOnClickListener(view -> {
            ll_sharestory.setVisibility(View.GONE);
            recyclerViewAskFax.setVisibility(View.GONE);

            askFaxTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
            shareTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
            shareStoryTxt.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));

            askFaxTxt.setTextColor(getResources().getColor(R.color.black));
            shareTxt.setTextColor(getResources().getColor(R.color.white));
            shareStoryTxt.setTextColor(getResources().getColor(R.color.black));

            Drawable img4 = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover);
            img4.setBounds(0, 0, 60, 60);
            shareStoryTxt.setCompoundDrawables(img4, null, null, null);

            Drawable img3 = requireContext().getResources().getDrawable(R.drawable.ic_icon_ionic_md_chatbubbles);
            img3.setBounds(0, 0, 60, 60);
            askFaxTxt.setCompoundDrawables(img3, null, null, null);

            Drawable img = requireContext().getResources().getDrawable(R.drawable.ic_icon_discover_white);
            img.setBounds(0, 0, 60, 60);
            shareTxt.setCompoundDrawables(img, null, null, null);

        });

        return root;
    }

    private void callAskFaxQuestionListApi() {

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
                        recyclerViewAskFax.setLayoutManager(RecyclerViewLayoutManager);
                        askFaxAdapter = new AskFaxAdapter(lst_askFax, getActivity());
                        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewAskFax.setLayoutManager(HorizontalLayout);
                        recyclerViewAskFax.setAdapter(askFaxAdapter);

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


    private void callAddBlogAPI() {


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        //create a file to write bitmap data
        File f = new File(getActivity().getCacheDir(), "blogs");
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

        SessionPref pref = SessionPref.getInstance(getActivity());

        RequestBody BlogCategory = RequestBody.create(MediaType.parse("text/plain"), IdBlogCategories);
        RequestBody BlogTitle = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody BlogDesc = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody BlogURL = RequestBody.create(MediaType.parse("text/plain"), "");

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("BlogImage", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<CommonResponse> call = service.userAddBlog("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), BlogCategory, BlogTitle, BlogDesc, BlogURL, filePart);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();

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
            public void onFailure(@NotNull Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
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
            public void onResponse(Call<ShareStoryPreData> call, Response<ShareStoryPreData> response) {
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
        EditText other = dialog.findViewById(R.id.otherprofession);
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


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                if (radioButtonID != -1) {
                    View radioButton = radioGroup.findViewById(radioButtonID);
                    int index = radioGroup.indexOfChild(radioButton);
                    categoryEdt.setText(getString(R.string.selected, array.get(index).getBCatTitle()));
                    categoryId = array.get(index).getIdblogCategories();
                    Log.e("categoryId  blog", "" + categoryId);
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
}
