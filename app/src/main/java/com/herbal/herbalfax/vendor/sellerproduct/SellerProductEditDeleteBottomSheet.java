package com.herbal.herbalfax.vendor.sellerproduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.dialog.DeleteDialog;
import com.herbal.herbalfax.vendor.sellerproduct.editproduct.EditProductActivity;

public class SellerProductEditDeleteBottomSheet extends BottomSheetDialogFragment {
    //  private final int index;
    TextView edit_txt, delete_txt;
    String productId;

    public SellerProductEditDeleteBottomSheet(SellerProductFragment sellerProductFragment, String productId) {
        //  this.index = adapterPosition;
        this.productId = productId;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.product_edit_delete_bottom_sheet, container, false);
        Button edtBtn, deleteBtn;
        edit_txt = view.findViewById(R.id.edit_txt);
        delete_txt = view.findViewById(R.id.delete_txt);
        delete_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DeleteDialog deleteDialog = new DeleteDialog(getActivity(), productId);
                    deleteDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        edit_txt.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), EditProductActivity.class);
                    startActivity(intent);
                    dismiss();

                }
        );

        return view;
    }


}
