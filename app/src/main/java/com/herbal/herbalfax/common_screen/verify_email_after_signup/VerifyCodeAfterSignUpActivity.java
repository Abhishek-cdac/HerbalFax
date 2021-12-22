package com.herbal.herbalfax.common_screen.verify_email_after_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.otpverified.SignUpOtpVerifyResponse;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.resendmodel.ResendOTPModel;

import com.herbal.herbalfax.customer.selectInterest.SelectInterestActivity;

import com.herbal.herbalfax.databinding.ActivityVerifyCodeAfterSignUpBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCodeAfterSignUpActivity extends AppCompatActivity {
    String email,userId;
    TextView emailStr;
    private ActivityVerifyCodeAfterSignUpBinding binding;
    private CommonClass clsCommon;
    String maskingEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        VerifyCodeAfterSignUpViewModel verifyCodeAfterSignUpViewModel = new VerifyCodeAfterSignUpViewModel();
        binding = DataBindingUtil.setContentView(VerifyCodeAfterSignUpActivity.this, R.layout.activity_verify_code_after_sign_up);
        binding.setLifecycleOwner(this);
        binding.setVerifyCodeAfterSignUpViewModel(verifyCodeAfterSignUpViewModel);

        emailStr = findViewById(R.id.emailStr);


        Log.e("maskingEmail ", "" + maskingEmail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = getIntent().getExtras().getString("Email");
            userId = getIntent().getExtras().getString("userId");
            maskingEmail = email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");

            Log.e("Email ", "" + email);
            Log.e("userId ", "" + userId);
        }

        emailStr.setText(maskingEmail);

        verifyCodeAfterSignUpViewModel.getUser().observe(this, confirmOtp -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(confirmOtp).getStrOTP())) {

                new CommonClass().showDialogMsg(VerifyCodeAfterSignUpActivity.this, "HerbalFax", "Enter OTP", "Ok");

                binding.loginEmail.requestFocus();
            } else {

                callVerifyOTP(confirmOtp, email);
            }
        });

        verifyCodeAfterSignUpViewModel.getResendClick().observe(this, resend -> {
            if (resend) {
                callResendOTP(email);
            }
        });


    }

    private void callResendOTP(String email) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("user_email", email);  //email


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<ResendOTPModel> call = service.signupResendOtp(hashMap);
        call.enqueue(new Callback<ResendOTPModel>() {
            @Override
            public void onResponse(Call<ResendOTPModel> call, Response<ResendOTPModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        new CommonClass().showDialogMsg(VerifyCodeAfterSignUpActivity.this, "HerbalFax", "" + response.body().getMessage(), "Ok");

                        //   startActivity(new Intent(VerifyCodeAfterSignUpActivity.this, LandingPageActivity.class));

                    } else {
                        clsCommon.showDialogMsg(VerifyCodeAfterSignUpActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(VerifyCodeAfterSignUpActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(VerifyCodeAfterSignUpActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<ResendOTPModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(VerifyCodeAfterSignUpActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callVerifyOTP(ConfirmOtp confirmOtp, String email) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("user_email", email); //
        hashMap.put("signup_otp", confirmOtp.getStrOTP());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<SignUpOtpVerifyResponse> call = service.signupOtpVerify(hashMap);
        call.enqueue(new Callback<SignUpOtpVerifyResponse>() {
            @Override
            public void onResponse(Call<SignUpOtpVerifyResponse> call, Response<SignUpOtpVerifyResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        new CommonClass().showDialogMsg(VerifyCodeAfterSignUpActivity.this, "HerbalFax", "" + response.body().getMessage(), "Ok");

                        startActivity(new Intent(VerifyCodeAfterSignUpActivity.this, SelectInterestActivity.class));

                    } else {
                        clsCommon.showDialogMsg(VerifyCodeAfterSignUpActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(VerifyCodeAfterSignUpActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(VerifyCodeAfterSignUpActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<SignUpOtpVerifyResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(VerifyCodeAfterSignUpActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}