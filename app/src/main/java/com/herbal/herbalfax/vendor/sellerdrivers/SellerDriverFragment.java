package com.herbal.herbalfax.vendor.sellerdrivers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.herbal.herbalfax.R;


public class SellerDriverFragment extends Fragment {

    private SellerDriversViewModel sellerDriversViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sellerDriversViewModel =
                ViewModelProviders.of(this).get(SellerDriversViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellerdriver, container, false);
        final TextView textView = root.findViewById(R.id.text_fax);
        sellerDriversViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}