package com.herbal.herbalfax.common_screen.landingpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.blogs.BlogListAdapter;

public class AddCommentbottomsheet extends BottomSheetDialogFragment {
    /*private final BlogListAdapter blogListAdapter;*/
    private final int index;


    public AddCommentbottomsheet(EventReadMoreActivity blogListAdapter, int adapterPosition) {
        /*this.blogListAdapter = blogListAdapter;*/
        this.index = adapterPosition;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.frag_add_comments_bottom_sheet, container, false);
//      setStyle(STYLE_NORMAL, R.style.BottomSheet);

       /* TextView todayDeal_txt, deal_heading_txt, bought_txt, address_txt, validity_txt;
        ImageView deal_Img;
        Button dealPrice_btn;

        todayDeal_txt = view.findViewById(R.id.todayDeal_txt);
        deal_heading_txt = view.findViewById(R.id.deal_heading_txt);
        address_txt = view.findViewById(R.id.address_txt);
        bought_txt = view.findViewById(R.id.bought_txt);
        dealPrice_btn = view.findViewById(R.id.dealPrice_btn);
        validity_txt = view.findViewById(R.id.validity_txt);
        deal_Img = view.findViewById(R.id.deal_Img);

        todayDeal_txt.setOnClickListener(v -> {
            // ref.onGallerySelect();
            dismiss();
        });
        deal_heading_txt.setOnClickListener(v -> {
            dismiss();
        });
        address_txt.setOnClickListener(v -> {
            dismiss();
        });
        bought_txt.setOnClickListener(v -> {
            dismiss();
        });
        validity_txt.setOnClickListener(v -> {
            dismiss();
        });
        deal_Img.setOnClickListener(v -> {
            dismiss();
        });
        dealPrice_btn.setOnClickListener(v -> {
            dismiss();
        });*/

        return view;
    }
}
