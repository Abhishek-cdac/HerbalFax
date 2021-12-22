package com.herbal.herbalfax.vendor.sellerorders;

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


public class SellerOrderFragment extends Fragment {

    private SellerOrdersViewModel sellerOrdersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sellerOrdersViewModel =
                ViewModelProviders.of(this).get(SellerOrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellerorder, container, false);
        final TextView textView = root.findViewById(R.id.text_fax);
        sellerOrdersViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}