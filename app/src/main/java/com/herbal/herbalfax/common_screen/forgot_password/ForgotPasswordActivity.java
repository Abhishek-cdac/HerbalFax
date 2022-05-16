package com.herbal.herbalfax.common_screen.forgot_password;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.email_verification.VerifyEmailActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.resendmodel.ResendOTPModel;
import com.herbal.herbalfax.databinding.ActivityForgotPasswordBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = CommonClass.getInstance();
        ForgotPasswordViewModel forgotPasswordViewModel = new ForgotPasswordViewModel();
        binding = DataBindingUtil.setContentView(ForgotPasswordActivity.this, R.layout.activity_forgot_password);
        binding.setLifecycleOwner(this);
        binding.setForgotPasswordViewModel(forgotPasswordViewModel);


        forgotPasswordViewModel.getConfirmEmail().observe(this, confirmEmail -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(confirmEmail).getStrEmail())) {
                new CommonClass().showDialogMsg(ForgotPasswordActivity.this, "HerbalFax", "Enter Email", "Ok");
                binding.loginEmail.requestFocus();
            } else {
                callResendOTP(confirmEmail);
            }
        });


    }

    private void callResendOTP(ConfirmEmail confirmEmail) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("user_email", confirmEmail.getStrEmail());  //email

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<ResendOTPModel> call = service.signupResendOtp(hashMap);
        call.enqueue(new Callback<ResendOTPModel>() {
            @Override
            public void onResponse(@NonNull Call<ResendOTPModel> call, @NonNull Response<ResendOTPModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        new CommonClass().showDialogMsg(ForgotPasswordActivity.this, "HerbalFax", "" + response.body().getMessage(), "Ok");

                        String OTP = String.valueOf(response.body().getData().getOtp());
                        String email = response.body().getData().getUserEmail();
                        Intent intent = new Intent(getApplicationContext(), VerifyEmailActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("otp", OTP);
                        startActivity(intent);
                    } else {
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<ResendOTPModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}