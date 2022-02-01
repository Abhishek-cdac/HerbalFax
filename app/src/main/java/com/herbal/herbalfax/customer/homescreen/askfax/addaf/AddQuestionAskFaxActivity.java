package com.herbal.herbalfax.customer.homescreen.askfax.addaf;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.askfax.addaf.addaskfaxmodel.AddAskFaxResponse;
import com.herbal.herbalfax.customer.homescreen.askfax.addaf.predatamodel.AfPrivacytype;
import com.herbal.herbalfax.customer.homescreen.askfax.addaf.predatamodel.AskFaxPreDataResponse;
import com.herbal.herbalfax.databinding.ActivityAddQuestionAskFaxBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuestionAskFaxActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView back;
    CommonClass clsCommon;
    Spinner privacySpinner;
    ArrayList<AfPrivacytype> lst_privacy_type;
    String IdafPrivacytypes;

    private ActivityAddQuestionAskFaxBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AddQuestionAskFaxViewModel addQuestionAskFaxViewModel = new AddQuestionAskFaxViewModel();
        binding = DataBindingUtil.setContentView(AddQuestionAskFaxActivity.this, R.layout.activity_add_question_ask_fax);
        binding.setLifecycleOwner(this);
        binding.setAddQuestionAskFaxViewModel(addQuestionAskFaxViewModel);

        clsCommon = CommonClass.getInstance();
        privacySpinner = findViewById(R.id.privacySpinner);
        back = findViewById(R.id.back);
        back.setOnClickListener(view -> onBackPressed());
        callAskFaxPreDataAPI();
        addQuestionAskFaxViewModel.getUser().observe(this, askFaxQue -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(askFaxQue).getQuestion())) {
                new CommonClass().showDialogMsg(AddQuestionAskFaxActivity.this, "HerbalFax", "Enter question", "Ok");
            } else {
                callAddAskFaxApi(askFaxQue);
            }

        });
        addQuestionAskFaxViewModel.getCancelClick().observe(this, cancel -> {
            if (cancel) {
                onBackPressed();
            }
        });

    }

    private void callAddAskFaxApi(AskfaxQue askFaxQue) {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Question", askFaxQue.getQuestion());
        hashMap.put("PrivacyType", IdafPrivacytypes);

        Call<AddAskFaxResponse> call = service.userAskFaxAddQuestions("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<AddAskFaxResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AddAskFaxResponse> call, @NonNull Response<AddAskFaxResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(AddQuestionAskFaxActivity.this, "Question post successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                    } catch (Exception e) {
                        Toast.makeText(AddQuestionAskFaxActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AddAskFaxResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(AddQuestionAskFaxActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callAskFaxPreDataAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<AskFaxPreDataResponse> call = service.userAskFaxAddQuestionData("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken));
        call.enqueue(new Callback<AskFaxPreDataResponse>() {
            @Override
            public void onResponse(Call<AskFaxPreDataResponse> call, Response<AskFaxPreDataResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("PreData..", "" + response.body().getData().getAfPrivacytypes());
                        lst_privacy_type = (ArrayList<AfPrivacytype>) response.body().getData().getAfPrivacytypes();

                        if (lst_privacy_type != null && lst_privacy_type.size() > 0) {
                            String[] privacyType = new String[lst_privacy_type.size()];

                            for (int i = 0; i < lst_privacy_type.size(); i++) {
                                privacyType[i] = lst_privacy_type.get(i).getAFPTTitle();
                                privacySpinner.setOnItemSelectedListener(AddQuestionAskFaxActivity.this);

                                Log.e("storeCategory ", "" + privacyType[i]);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddQuestionAskFaxActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        privacyType);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                privacySpinner.setAdapter(spinnerArrayAdapter);

                            }
                        }


                        assert lst_privacy_type != null;
                        Log.e("lst_privacy_type ", "" + lst_privacy_type.size());
                    } else {
                        clsCommon.showDialogMsg(AddQuestionAskFaxActivity.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(AddQuestionAskFaxActivity.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AddQuestionAskFaxActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<AskFaxPreDataResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddQuestionAskFaxActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            IdafPrivacytypes = lst_privacy_type.get(i).getIdafPrivacytypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}