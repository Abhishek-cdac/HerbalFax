package com.herbal.herbalfax.vendor.sellernotification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.vendor.sellerdrivers.SellerMyDriverAdapter;

import java.util.ArrayList;


public class SellerNotificationFragment extends Fragment {

    ArrayList<Interest> lst_notification = new ArrayList<>();
    private SellerNotificationViewModel sellerNotificationViewModel;
    RecyclerView notificatonRecyclerView;
    private LinearLayoutManager RecyclerViewLayoutManager;
    SellerNotificationAdapter sellerNotificationAdapter;
    private LinearLayoutManager HorizontalLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sellerNotificationViewModel =
                ViewModelProviders.of(this).get(SellerNotificationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_seller_notification, container, false);
        /*final TextView textView = root.findViewById(R.id.text_fax);*/
        notificatonRecyclerView = root.findViewById(R.id.notification_recyler);

        setMyDriveradapter();
        return root;
    }
    private void setMyDriveradapter() {
        lst_notification = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        notificatonRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
        sellerNotificationAdapter = new SellerNotificationAdapter(lst_notification, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        notificatonRecyclerView.setAdapter(sellerNotificationAdapter);
    }

}