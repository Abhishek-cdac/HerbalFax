package com.herbal.herbalfax.signupnewvendor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.NewVendorSignUpScEleven;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.addcard.AddCardActivity;

public class ClaimBottomSheet extends BottomSheetDialogFragment {
    //  private final int index;


    public ClaimBottomSheet(NewVendorSignUpScEight newVendorSignUpScEight) {
        //  this.index = adapterPosition;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.frag_claim_bottom_sheet, container, false);
        Button no_btn;
        no_btn = view.findViewById(R.id.no_btn);

        no_btn.setOnClickListener(v ->{

                Intent intent= new Intent(getActivity(), NewVendorSignUpScEleven.class);
                startActivity(intent);
                }
           );

        return view;
    }


}
