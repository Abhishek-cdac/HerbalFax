package com.herbal.herbalfax.signupnew;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.customer.signup.SignUpAsCustomerActivity;
import com.herbal.herbalfax.customer.signup.presignupmodel.AllCountry;
import com.herbal.herbalfax.customer.signup.presignupmodel.AllProfession;
import com.herbal.herbalfax.customer.signup.presignupmodel.PreSignUpData;
import com.herbal.herbalfax.databinding.ActivityNewCustomerSignUpScOneBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCustomerSignUpScOne extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ActivityNewCustomerSignUpScOneBinding binding;
    private CommonClass clsCommon;
    int userType;
    EditText profession;
    private ArrayList<AllProfession> lst_profession;
    public int selectedResolutionPosition = -1;
    EditText birthdayEdt;
    final Calendar myCalendar = Calendar.getInstance();

    String professionalId;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsCommon = new CommonClass();
        SignUpNewAsCustomerViewModel signUpAsCustomerViewModel = new SignUpNewAsCustomerViewModel();
        binding = DataBindingUtil.setContentView(NewCustomerSignUpScOne.this, R.layout.activity_new_customer_sign_up_sc_one);
        binding.setLifecycleOwner(this);
        binding.setSignUpNewAsCustomerViewModel(signUpAsCustomerViewModel);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userType = getIntent().getExtras().getInt("selectedType");
            Log.e("data ", "" + userType);
        }
        signUpAsCustomerViewModel.OnNextHereClick().observe(this, nextUser -> {
            Intent mIntent = new Intent(NewCustomerSignUpScOne.this, NewCustomerSignUpScTwo.class);
            startActivity(mIntent);
        });
        birthdayEdt = findViewById(R.id.birthday);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        birthdayEdt.setOnClickListener(v -> {

            DatePickerDialog dialog = new DatePickerDialog(NewCustomerSignUpScOne.this,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            dialog.show();
        });

        callPreSignUpDataAPI();


        profession = findViewById(R.id.professionedt);
        profession.setOnClickListener(view -> {
            try {
                clsCommon.hideKeyboard(view, NewCustomerSignUpScOne.this);
                radioButtonDialogWithList(getString(R.string.app_name), lst_profession);  //getResources().getStringArray(R.array.example_items)
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdayEdt.setText(sdf.format(myCalendar.getTime()));
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void radioButtonDialogWithList(String dialogTitle, final ArrayList<AllProfession> array) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.professional_dialog_layout);

        Button add = dialog.findViewById(R.id.add);
        EditText other = dialog.findViewById(R.id.otherprofession);
        ImageView cancel = dialog.findViewById(R.id.cancel);

        final RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{getResources().getColor(R.color.text_grey)}
                },
                new int[]{getResources().getColor(R.color.text_grey)}
        );


        for (AllProfession item : array) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setHighlightColor(getResources().getColor(R.color.black));
            radioButton.setText(item.getProfTitle());
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
                    profession.setText(getString(R.string.selected, array.get(index).getProfTitle()));
                    professionalId = array.get(index).getIdprofessions();
                    Log.e("professionalId  ", "" + professionalId);
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


    private void callPreSignUpDataAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<PreSignUpData> call = service.signupPreData(hashMap);
        call.enqueue(new Callback<PreSignUpData>() {
            @Override
            public void onResponse(Call<PreSignUpData> call, Response<PreSignUpData> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        Log.e("", "" + response.body());
                        lst_profession = (ArrayList<AllProfession>) response.body().getData().getAllProfessions();
                     //   lst_country = (ArrayList<AllCountry>) response.body().getData().getAllCountries();

/*
                        if (lst_country != null && lst_country.size() > 0) {
                            String[] countryCode = new String[lst_country.size()];

                            for (int i = 0; i < lst_country.size(); i++) {
                                countryCode[i] = lst_country.get(i).getCounCode();
                                spinner.setOnItemSelectedListener(NewCustomerSignUpScOne.this);

                                Log.e("countryCode ", "" + countryCode[i]);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NewCustomerSignUpScOne.this,
                                        android.R.layout.simple_spinner_item,
                                        countryCode);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);

                            }
                        }


                        Log.e("lst_country ", "" + lst_country.size());*/
                    } else {
                        clsCommon.showDialogMsg(NewCustomerSignUpScOne.this, "HerbalFax", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(NewCustomerSignUpScOne.this, "HerbalFax", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(NewCustomerSignUpScOne.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<PreSignUpData> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(NewCustomerSignUpScOne.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item

        try {
            String item = adapterView.getItemAtPosition(i).toString();
            ((TextView) adapterView.getChildAt(i)).setTextColor(Color.parseColor("#8E8E8E"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}