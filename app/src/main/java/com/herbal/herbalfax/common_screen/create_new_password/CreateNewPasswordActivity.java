package com.herbal.herbalfax.common_screen.create_new_password;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.login.LoginActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.databinding.ActivityCreateNewPasswordBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewPasswordActivity extends AppCompatActivity {
    String emailStr, otpStr;
    private ActivityCreateNewPasswordBinding binding;
    private CommonClass clsCommon;
    ImageView iv_show_password;
    EditText login_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        CreateNewPasswordViewModel ViewModel = new CreateNewPasswordViewModel();
        binding = DataBindingUtil.setContentView(CreateNewPasswordActivity.this, R.layout.activity_create_new_password);
        binding.setLifecycleOwner(this);
        binding.setCreateNewPasswordViewModel(ViewModel);
        iv_show_password = findViewById(R.id.iv_show_password);
        login_Password = findViewById(R.id.login_Password);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            emailStr = getIntent().getExtras().getString("email");
            otpStr = getIntent().getExtras().getString("otp");
            Log.e("VerifyEmailData ", "" + emailStr + "  " + otpStr);
        }

        ViewModel.getUser().observe(this, changePassword -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(changePassword).getStrPassword())) {
                // binding.loginEmail.setError("Enter an E-Mail Address");
                new CommonClass().showDialogMsg(CreateNewPasswordActivity.this, "HerbalFax", "Enter Password", "Ok");

                binding.loginPassword.requestFocus();
            } else if (TextUtils.isEmpty(Objects.requireNonNull(changePassword).getStrNewPassword())) {
                //   binding.loginPassword.setError("Enter a Password");
                new CommonClass().showDialogMsg(CreateNewPasswordActivity.this, "HerbalFax", "Enter new Password", "Ok");

                binding.loginPassword1.requestFocus();
            }
            else if (!binding.loginPassword.getText().toString().equals(binding.loginPassword1.getText().toString())) {
                clsCommon.showDialogMsg(CreateNewPasswordActivity.this, "HerbalFax", "Please enter correct password", "Ok");
            }
            else {
                callResetPasswordApi(emailStr, otpStr, changePassword);
            }
        });
        iv_show_password.setOnClickListener(v -> {

            if (login_Password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                (iv_show_password).setImageResource(R.drawable.ic_visibility);

                //Show Password
                login_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                (iv_show_password).setImageResource(R.drawable.ic_visibility_off);

                //Hide Password
                login_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });

    }

    private void callResetPasswordApi(String emailStr, String otpStr, ChangePassword changePassword) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("user_email", emailStr);
        hashMap.put("user_otp", otpStr);
        hashMap.put("UPassword", changePassword.getStrPassword());
        hashMap.put("ReUPassword", changePassword.getStrNewPassword());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<UpdatePasswordResponse> call = service.updatePassword(hashMap);
        call.enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdatePasswordResponse> call, @NonNull Response<UpdatePasswordResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        startActivity(new Intent(CreateNewPasswordActivity.this, LoginActivity.class));

                    } else {
                        clsCommon.showDialogMsg(CreateNewPasswordActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(CreateNewPasswordActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(CreateNewPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<UpdatePasswordResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(CreateNewPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}