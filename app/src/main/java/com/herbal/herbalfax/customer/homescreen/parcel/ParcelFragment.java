package com.herbal.herbalfax.customer.homescreen.parcel;

import android.os.Bundle;
import android.text.Html;
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
import com.herbal.herbalfax.customer.homescreen.orders.OrdersViewModel;


public class ParcelFragment extends Fragment {

    private ParcelViewModel parcelViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parcelViewModel =
                ViewModelProviders.of(this).get(ParcelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_parcelservice, container, false);
        final TextView textView = root.findViewById(R.id.tracking_id_txt);
        final TextView nameTxttextView = root.findViewById(R.id.nameTxt);
        String firstnameTxt = "Hi ";
        String nextNameTxt = "<font color='#000000'>Mayuri Dayla</font>";
       String thirdNameTxt = ", Your order has Shipped!";
        nameTxttextView.setText(Html.fromHtml(firstnameTxt + nextNameTxt + thirdNameTxt));

        String first = "Your tracking Id is ";
        String next = "<font color='#000000'>#9B19C.</font>";
        textView.setText(Html.fromHtml(first + next));

//        parcelViewModel.getText().observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}