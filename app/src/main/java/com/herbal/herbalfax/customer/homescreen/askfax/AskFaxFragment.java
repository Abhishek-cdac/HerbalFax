package com.herbal.herbalfax.customer.homescreen.askfax;

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
import com.herbal.herbalfax.customer.homescreen.explorestrain.ExploreStrainViewModel;


public class AskFaxFragment extends Fragment {

    private AskFaxViewModel askFaxViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        askFaxViewModel =
                ViewModelProviders.of(this).get(AskFaxViewModel.class);
        View root = inflater.inflate(R.layout.fragment_askfax, container, false);
        final TextView textView = root.findViewById(R.id.text_fax);
        askFaxViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}