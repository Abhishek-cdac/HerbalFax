package com.herbal.herbalfax.customer.homescreen.mysocial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.herbal.herbalfax.R;


public class MySocialFragment extends Fragment {

    private MySocialViewModel mySocialViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mySocialViewModel = ViewModelProviders.of(this).get(MySocialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_social, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        mySocialViewModel.getText().observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }
}