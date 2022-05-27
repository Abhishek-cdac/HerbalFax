package com.herbal.herbalfax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewVendorSignUpScFourteen extends AppCompatActivity {
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_sign_up_sc14);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewVendorSignUpScFourteen.this, NewVendorSignUpScFifteen.class);
                startActivity(intent);
            }
        });
    }
}