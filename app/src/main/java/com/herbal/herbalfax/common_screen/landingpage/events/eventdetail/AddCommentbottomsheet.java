package com.herbal.herbalfax.common_screen.landingpage.events.eventdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.commonmodel.CommonResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentbottomsheet extends BottomSheetDialogFragment {
    String IdEvents;
    EventReadMoreActivity eventReadMoreActivity;
    ImageView profile_image;
    TextView userName, profession;
    EditText comments;
    Button submitComment;
    SessionPref pref;
    public AddCommentbottomsheet(EventReadMoreActivity eventReadMoreActivity, String IdEvents) {
        this.eventReadMoreActivity = eventReadMoreActivity;
        this.IdEvents = IdEvents;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.frag_add_comments_bottom_sheet, container, false);
//      setStyle(STYLE_NORMAL, R.style.BottomSheet);
        profile_image = view.findViewById(R.id.profile_image);
        pref = SessionPref.getInstance(eventReadMoreActivity);

        userName = view.findViewById(R.id.userName);
        profession = view.findViewById(R.id.profession);
        comments = view.findViewById(R.id.comments);
        submitComment = view.findViewById(R.id.submitComment);
        userName.setText(pref.getStringVal(SessionPref.LoginUserfullName));
        profession.setText(pref.getStringVal(SessionPref.LoginProfession));

        submitComment.setOnClickListener(view1 -> {
            if (!comments.getText().toString().equals("")) {
                callAddCommentApi(IdEvents, comments.getText().toString());
            }

        });
        return view;
    }

    private void callAddCommentApi(String idEvents, String comments) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("IdEvents", idEvents);
        hashMap.put("Comments", comments);

        Call<CommonResponse> call = service.userEventAddComment("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                       Toast.makeText(eventReadMoreActivity, "Comment Added", Toast.LENGTH_SHORT).show();
                        eventReadMoreActivity.callEventCommentAPI(idEvents);
                        dismiss();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //  clsCommon.showDialogMsgFrag(c, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(eventReadMoreActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(eventReadMoreActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
