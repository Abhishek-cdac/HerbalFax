package com.herbal.herbalfax.customer.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.feed.FeedAdapter;

public class MoreBottomSheet extends BottomSheetDialogFragment {
    private final FeedAdapter feedAdapter;
    private final int index;


    public MoreBottomSheet(FeedAdapter feedAdapter, int adapterPosition) {
        this.feedAdapter = feedAdapter;
        this.index = adapterPosition;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.frag_more_bottom_sheet, container, false);
        TextView txt_copyLink, txt_mute, txt_shareTo, txt_turnOnNotification, txt_unfollow, txt_report;

        txt_copyLink = view.findViewById(R.id.copylink_txt);
        txt_mute = view.findViewById(R.id.mute_txt);
        txt_shareTo = view.findViewById(R.id.shareto_txt);
        txt_turnOnNotification = view.findViewById(R.id.turnoff_notification_txt);
        txt_unfollow = view.findViewById(R.id.unfollow_txt);
        txt_report = view.findViewById(R.id.report_txt);

        txt_mute.setOnClickListener(v -> {
           // ref.onGallerySelect();
            dismiss();
        });
        txt_copyLink.setOnClickListener(v -> {
            dismiss();
        });
        txt_shareTo.setOnClickListener(v -> {
            dismiss();
        });
        txt_report.setOnClickListener(v -> {
            dismiss();
        });
        txt_turnOnNotification.setOnClickListener(v -> {
            dismiss();
        });
        txt_unfollow.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }
}
