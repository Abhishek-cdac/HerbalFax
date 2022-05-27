package com.herbal.herbalfax.vendor.sellerorders;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.DiscoveryAdapter;
import com.herbal.herbalfax.customer.homescreen.group.yourgroupmodel.Group;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import java.util.ArrayList;
import java.util.List;


public class SellerOrderFragment extends Fragment {
    private List<Group> lst_completeOrder = new ArrayList<>();
    private SellerOrdersViewModel sellerOrdersViewModel;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewPending, recyclerViewCompleted;
    private Button newOrder,pendingOrder;
    private Button CompletedOrder;
    Onclick itemClick;
    private CompleteOrderAdapter mAdaptery;
    private  NewOrderAdapter newOrderAdapter;
    private PendingOrderAdapter pendingOrderAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sellerOrdersViewModel = ViewModelProviders.of(this).get(SellerOrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellerorder, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerViewPending = root.findViewById(R.id.recycler_view_pending);
        recyclerViewCompleted = root.findViewById(R.id.recycler_view_completed);
        newOrder = root.findViewById(R.id.neworder);
        pendingOrder = root.findViewById(R.id.pending);
        CompletedOrder = root.findViewById(R.id.Completed);
        callNewOrderAPI();
        newOrder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View view) {
                //  mapLinear.setVisibility(View.GONE);
                pendingOrder.setTextColor(getResources().getColor(R.color.black));
                newOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                pendingOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                CompletedOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                newOrder.setTextColor(getResources().getColor(R.color.white));
                CompletedOrder.setTextColor(getResources().getColor(R.color.black));
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewPending.setVisibility(View.GONE);
                recyclerViewCompleted.setVisibility(View.GONE);
                callNewOrderAPI();
            }
        });

        pendingOrder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View view) {
                // mapLinear.setVisibility(View.VISIBLE);

                pendingOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_green));
                newOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                pendingOrder.setTextColor(getResources().getColor(R.color.white));
                newOrder.setTextColor(getResources().getColor(R.color.black));
                CompletedOrder.setTextColor(getResources().getColor(R.color.black));
                CompletedOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                recyclerViewPending.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                recyclerViewCompleted.setVisibility(View.GONE);
                callPendingOrderAPI();            }
        });
        CompletedOrder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View view) {
                // mapLinear.setVisibility(View.VISIBLE);

                pendingOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                newOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_whiteeeee));
                pendingOrder.setTextColor(getResources().getColor(R.color.black));
                newOrder.setTextColor(getResources().getColor(R.color.black));
                CompletedOrder.setTextColor(getResources().getColor(R.color.white));
                CompletedOrder.setBackground(getResources().getDrawable(R.drawable.rect_button_green));

                recyclerViewPending.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                recyclerViewCompleted.setVisibility(View.VISIBLE);
                callCompletedOrderAPI();
            }
        });

        return root;
    }

    private void callCompletedOrderAPI() {
        mAdaptery = new CompleteOrderAdapter(lst_completeOrder, getActivity(), itemClick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCompleted.setLayoutManager(mLayoutManager);
        recyclerViewCompleted.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCompleted.setAdapter(mAdaptery);

    }
    private void callPendingOrderAPI() {
        pendingOrderAdapter = new PendingOrderAdapter(lst_completeOrder, getActivity(), itemClick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPending.setLayoutManager(mLayoutManager);
        recyclerViewPending.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPending.setAdapter(pendingOrderAdapter);

    }private void callNewOrderAPI() {
        newOrderAdapter = new NewOrderAdapter(lst_completeOrder, getActivity(), itemClick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newOrderAdapter);

    }
}