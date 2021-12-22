package com.herbal.herbalfax.common_screen.terms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.terms.termmodel.TermResponse;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.customer.signup.SignUpAsCustomerActivity;
import com.herbal.herbalfax.customer.signup.presignupmodel.AllCountry;
import com.herbal.herbalfax.customer.signup.presignupmodel.AllGender;
import com.herbal.herbalfax.customer.signup.presignupmodel.AllProfession;
import com.herbal.herbalfax.customer.signup.presignupmodel.PreSignUpData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermAndConditionActivity extends AppCompatActivity {
    TextView title, subTitle;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);
        clsCommon = new CommonClass();
        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.subtitle);
        callTermAndConditionAPI();
    }


    private void callTermAndConditionAPI() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<TermResponse> call = service.getTnc(hashMap);
        call.enqueue(new Callback<TermResponse>() {
            @Override
            public void onResponse(Call<TermResponse> call, Response<TermResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        //   title.setText(  Html.fromHtml(response.body().getData().getTnc().getTncTitle()).toString());
                        subTitle.setText(Html.fromHtml(response.body().getData().getTnc().getTncData()).toString());
                        Log.e("", "" + response.body());
                    } else {
                        clsCommon.showDialogMsg(TermAndConditionActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(TermAndConditionActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(TermAndConditionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<TermResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(TermAndConditionActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}