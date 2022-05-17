package com.herbal.herbalfax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class NewCustomerSignUpSc11 extends AppCompatActivity {
    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_sign_up_sc11);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.add_business_recycler);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        AddBusinessAdapter addbusinessAdapter = new AddBusinessAdapter(NewCustomerSignUpSc11.this, personNames);
        recyclerView.setAdapter(addbusinessAdapter);
    }
}