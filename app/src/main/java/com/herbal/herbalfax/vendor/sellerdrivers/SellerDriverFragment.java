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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.AddPeopleListAdaptor;
import com.herbal.herbalfax.customer.selectInterest.Interest;
import com.herbal.herbalfax.customer.store.adapter.StoreDealsAdapter;

import java.util.ArrayList;


public class SellerDriverFragment extends Fragment {

    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    private SellerDriversViewModel sellerDriversViewModel;
    RecyclerView sellerMyDriverAdapter;
    private LinearLayoutManager RecyclerViewLayoutManager;
    SellerMyDriverAdapter sellermyDriveradapter;
    private LinearLayoutManager HorizontalLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sellerDriversViewModel =
                ViewModelProviders.of(this).get(SellerDriversViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellerdriver, container, false);
        /*final TextView textView = root.findViewById(R.id.text_fax);*/
        sellerMyDriverAdapter = root.findViewById(R.id.mydriversprofile);

        setMyDriveradapter();
        return root;
    }
    private void setMyDriveradapter() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        sellerMyDriverAdapter.setLayoutManager(RecyclerViewLayoutManager);
        sellermyDriveradapter = new SellerMyDriverAdapter(lst_cart_item, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sellerMyDriverAdapter.setAdapter(sellermyDriveradapter);
    }

}