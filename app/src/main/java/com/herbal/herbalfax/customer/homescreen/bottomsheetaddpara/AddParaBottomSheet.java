package com.herbal.herbalfax.customer.homescreen.bottomsheetaddpara;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.AskFaxFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddParaBottomSheet extends BottomSheetDialogFragment {
    String IdBlog;
    AskFaxFragment askFaxFragment;
    LinearLayout uploadImg;
    EditText comments;
    Button submitComment, cancelBtn;
    SessionPref pref;
    Bitmap bitmap;
    ImageView profileImage;


    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 150;
    public AddParaBottomSheet(AskFaxFragment askFaxFragment, String IdBlog) {
        this.askFaxFragment = askFaxFragment;
        this.IdBlog = IdBlog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.frag_add_paragraph_bottom_sheet, container, false);
        pref = SessionPref.getInstance(getActivity());

        profileImage = view.findViewById(R.id.profileImage);
       uploadImg = view.findViewById(R.id.uploadImg);
        comments = view.findViewById(R.id.comments);
        submitComment = view.findViewById(R.id.addbtn);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(requireActivity(),
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);
                pickImage();
            }
        });
        submitComment.setOnClickListener(view1 -> {
            if (!comments.getText().toString().equals("")) {
                callAddParagraphApi(IdBlog, comments.getText().toString());
            } else {
                Toast.makeText(askFaxFragment.getActivity(), "Please Enter Description", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

    @SuppressLint("IntentReset")
    public void pickImage() {
        @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }
    private void callAddParagraphApi(String idBlog, String comments) {
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
            FileOutputStream fos;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        RequestBody BlogId = RequestBody.create(MediaType.parse("text/plain"), idBlog);
        RequestBody BlogDesc = RequestBody.create(MediaType.parse("text/plain"), comments);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("BPara_Image", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));

        Call<CommonResponse> call = service.userAddBlogParagraph("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), BlogId, BlogDesc, filePart);
        call.enqueue(new Callback<CommonResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Comment Added", Toast.LENGTH_SHORT).show();
                        // eventReadMoreActivity.callEventCommentAPI(idEvents);
                        dismiss();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //  clsCommon.showDialogMsgFrag(c, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

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
                    profileImage.setImageBitmap(bitmap);
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
