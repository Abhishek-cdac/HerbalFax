package com.herbal.herbalfax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.herbal.herbalfax.common_screen.login.LoginActivity;

public class NewVendorSignUpScFifteen extends AppCompatActivity {
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vendor_sign_up_sc15);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(NewVendorSignUpScFifteen.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}