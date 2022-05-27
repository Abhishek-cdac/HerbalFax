package com.herbal.herbalfax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewVendoreSignUpScNine extends AppCompatActivity {
EditText BusinessNameEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        BusinessNameEdt = findViewById(R.id.BusinessNameEdt);
        BusinessNameEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewVendoreSignUpScNine.this, NewVendorSignUpScEleven.class);
                startActivity(intent);

            }
        });

    }

}