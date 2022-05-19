package com.herbal.herbalfax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewVendorSignUpScThirteen extends AppCompatActivity {
Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vendor_sign_up_sc13);

        add_button= findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewVendorSignUpScThirteen.this, NewVendorSignUpScFourteen.class);
                startActivity(intent);

            }
        });
    }
}