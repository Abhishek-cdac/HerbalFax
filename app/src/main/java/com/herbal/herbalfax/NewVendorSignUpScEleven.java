package com.herbal.herbalfax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class NewVendorSignUpScEleven extends AppCompatActivity {
    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_sign_up_sc11);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewVendorSignUpScEleven.this, NewVendorsSignUpAddYourBusiness.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.add_business_recycler);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        AddBusinessAdapter addbusinessAdapter = new AddBusinessAdapter(NewVendorSignUpScEleven.this, personNames);
        recyclerView.setAdapter(addbusinessAdapter);
    }
}