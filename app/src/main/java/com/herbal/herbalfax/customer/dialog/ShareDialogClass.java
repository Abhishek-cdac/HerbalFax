package com.herbal.herbalfax.customer.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.herbal.herbalfax.R;

public class ShareDialogClass extends Dialog implements  android.view.View.OnClickListener{

    ImageView no;
    TextView fb, messenger, twitter, viaEmail, copyLink;
    public Activity c;
    public ShareDialogClass(Activity a) {
        super(a);

        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.share_dialog);
        no =  findViewById(R.id.cancel);
        fb =  findViewById(R.id.facebook);
        messenger =  findViewById(R.id.messenger);
        twitter =  findViewById(R.id.twitter);
        viaEmail =  findViewById(R.id.via_email);
        copyLink =  findViewById(R.id.copy_link);
        fb.setOnClickListener(this);
        no.setOnClickListener(this);
        twitter.setOnClickListener(this);
        messenger.setOnClickListener(this);
        copyLink.setOnClickListener(this);
        viaEmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cancel:
                dismiss();
                break;

            case R.id.facebook:
                dismiss();
                break;
            case R.id.twitter:
                dismiss();
                break;
            case R.id.messenger:
                dismiss();
                break;
            case R.id.copy_link:
                dismiss();
                break;
            case R.id.via_email:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
