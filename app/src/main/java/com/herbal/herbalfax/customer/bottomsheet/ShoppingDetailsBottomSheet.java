package com.herbal.herbalfax.customer.bottomsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.addcard.AddCardActivity;
import com.herbal.herbalfax.customer.homescreen.addcard.AddCardFragment;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.CheckOutActivity;
import com.herbal.herbalfax.customer.homescreen.cart.selectdeliveryaddress.SelectDeliveryAddressActivity;
import com.herbal.herbalfax.customer.homescreen.feed.FeedAdapter;

public class ShoppingDetailsBottomSheet extends BottomSheetDialogFragment {

    SelectDeliveryAddressActivity selectDeliveryAddressActivity;
    CheckOutActivity checkOutActivity;
    String subTotalStr, shippingStr, taxStr, total ,orders_Type, idAddress;
    TextView subTotal, Shipping, tax, Total;


    public ShoppingDetailsBottomSheet(SelectDeliveryAddressActivity selectDeliveryAddressActivity, String subTotalStr, String shipping, String taxStr, String total, String idAddress, String orders_Type) {
        this.selectDeliveryAddressActivity = selectDeliveryAddressActivity;
        this.subTotalStr = subTotalStr;
        this.shippingStr = shipping;
        this.taxStr = taxStr;
        this.total = total;
        this.idAddress = idAddress;
        this.orders_Type = orders_Type;
    }

    public ShoppingDetailsBottomSheet(CheckOutActivity checkOutActivity, String subTotalStr, String shipping, String taxStr, String total, String orders_Type) {
        this.checkOutActivity = checkOutActivity;
       this.subTotalStr = subTotalStr;
       this.shippingStr = shipping;
       this.taxStr = taxStr;
       this.total = total;
       this.orders_Type = orders_Type;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.shopping_detail_bottom_sheet, container, false);
        Button creditButton, debitButton;

        subTotal = view.findViewById(R.id.subTotal);
        Shipping = view.findViewById(R.id.Shipping);
        tax = view.findViewById(R.id.tax);
        Total = view.findViewById(R.id.Total);
        creditButton = view.findViewById(R.id.credit_button);
        debitButton = view.findViewById(R.id.debit_button);

        subTotal.setText("$"+subTotalStr);
        Shipping.setText("$"+"0"); //
        tax.setText("$"+taxStr);
        Total.setText("$"+total);

        creditButton.setOnClickListener(v -> {

            Intent intent= new Intent(getActivity(), AddCardActivity.class);
            intent.putExtra("orders_Type", orders_Type);
            intent.putExtra("idAddress", idAddress);
            intent.putExtra("CardType", "1");
            intent.putExtra("CardName", "CREDIT CARD");
            startActivity(intent);

            dismiss();
        });
        debitButton.setOnClickListener(v -> {
            Intent intent= new Intent(getActivity(), AddCardActivity.class);
            intent.putExtra("orders_Type", orders_Type);
            intent.putExtra("idAddress", idAddress);
            intent.putExtra("CardType", "2");
            intent.putExtra("CardName", "DEBIT CARD");
            startActivity(intent);
            dismiss();
        });

        return view;
    }
}
