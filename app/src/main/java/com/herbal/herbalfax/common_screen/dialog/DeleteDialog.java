package com.herbal.herbalfax.common_screen.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class DeleteDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView yes, no;
    ImageView Close;
    String productId;

    public DeleteDialog(Activity a, String productId) {
        super(a);

        this.c = a;
        this.productId = productId;
    }

    public DeleteDialog(Context mContext) {
        super(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_dialog);
        yes = findViewById(R.id.delete);
        no = findViewById(R.id.cancel);
        Close = findViewById(R.id.closeImg);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        Close.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                callDeleteProductAPI(productId);
                dismiss();
                break;
            case R.id.cancel:
            case R.id.closeImg:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void callDeleteProductAPI(String productId) {

        SessionPref pref = SessionPref.getInstance(c);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("ProductId", productId);


        Call<CommonResponse> call = service.vendorProductDelete("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("ProductId data success ", "" + productId);

                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //  clsCommon.showDialogMsgFrag(c, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(c, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<CommonResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(c, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
