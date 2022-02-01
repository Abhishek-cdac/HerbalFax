package com.herbal.herbalfax.common_screen.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.forgot_password.ForgotPasswordActivity;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.selectiontype.RegistrationSelectionActivity;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.common_screen.verify_email_after_signup.VerifyCodeAfterSignUpActivity;
import com.herbal.herbalfax.customer.homescreen.homedashboard.LandingPageActivity;
import com.herbal.herbalfax.databinding.ActivityLoginBinding;
import com.herbal.herbalfax.vendor.SellerLandingPageActivity;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    boolean isBusiness = false;

    private ActivityLoginBinding binding;
    private CommonClass clsCommon;
    private CheckBox saveLoginCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = CommonClass.getInstance();
        LoginViewModel loginViewModel = new LoginViewModel();
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        binding.loginEmail.setFilters(new InputFilter[]{ignoreFirstWhiteSpace(), new InputFilter.LengthFilter(50)});
        SessionPref pref = SessionPref.getInstance(this);

        saveLoginCheckBox =  findViewById(R.id.saveLoginCheckBox);
      /* Button loginButton = findViewById(R.id.login_button);
       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
*/


        loginViewModel.getUser().observe(this, loginUser -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                // binding.loginEmail.setError("Enter an E-Mail Address");
                new CommonClass().showDialogMsg(LoginActivity.this, "HerbalFax", "Enter an E-Mail Address", "Ok");

                binding.loginEmail.requestFocus();
            } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                new CommonClass().showDialogMsg(LoginActivity.this, "HerbalFax", "Enter a Password", "Ok");

                binding.loginPassword.requestFocus();
            } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                new CommonClass().showDialogMsg(LoginActivity.this, "HerbalFax", "Enter at least 3 Digit password", "Ok");

                binding.loginPassword.requestFocus();
            } else {
                callLoginAPI(loginUser);
            }

        });


        loginViewModel.getRegisterUser().observe(this, register -> {

            if (register) {
                startActivity(new Intent(LoginActivity.this, RegistrationSelectionActivity.class));
            } else {
                // call API

            }

        });

        loginViewModel.getForgotClick().observe(this, forgot -> {
            if (forgot) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        //   finish();
        super.onBackPressed();

    }

    /**
     *
     * @param loginUser
     */
    private void callLoginAPI(@NonNull LoginUser loginUser) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("user_email", loginUser.getStrEmailAddress());
        hashMap.put("user_password", loginUser.getStrPassword());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.login(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        checkForTheLastActivity(response.body());
                    } else {
                        clsCommon.showDialogMsg(LoginActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(LoginActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Contract(" -> new")
    private InputFilter ignoreFirstWhiteSpace() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }
                }
                return null;
            }
        };
    }

    private void checkForTheLastActivity(LoginResponse body) {
        try {
            User user = body.getData().getUser();
            if (user.getUEmailVerified().equals("0")) {
                Intent intent = new Intent(getApplicationContext(), VerifyCodeAfterSignUpActivity.class);
                intent.putExtra("Email", user.getUEmailAddress());
                intent.putExtra("userId", user.getIdusers());
                startActivity(intent);

                Log.e("", "" + user.getUserType());
            } else {
                logicAfterUserType(user);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void logicAfterUserType(User user) {
        isBusiness = user.getUserType().equals("2");
        SessionPref.getInstance(LoginActivity.this).saveLoginUser(
                user.getIdusers(),
                user.getUserType(),
                user.getUFullName(),
                user.getUDOB(),
                user.getUGender(),
                user.getUCity(),
                user.getUEmailAddress(),
                user.getUEmailVerified(),
                user.getUMobNoCode(),
                user.getUMobNo(),
                user.getUProPic(),
                user.getUActive(),
                user.getUDevToken(),
                user.getJwtToken(),
                user.getProfTitle()
        );

        Intent mIntent;
        Context mContext = LoginActivity.this;

        if(user.getUserType().equals("1")){
            mIntent = new Intent(mContext, LandingPageActivity.class);
            //SessionPref.getInstance(mContext).saveBoolKeyVal(LoginVerified, true);
        }
        else{
            mIntent = new Intent(mContext, SellerLandingPageActivity.class);
            //SessionPref.getInstance(mContext).saveBoolKeyVal(LoginVerified, true);
        }
        mIntent.putExtra("JwtToken", user.getJwtToken());
        startActivity(mIntent);
        finish();


    }


}