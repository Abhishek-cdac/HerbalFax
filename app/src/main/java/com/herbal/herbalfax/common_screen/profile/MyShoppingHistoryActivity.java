package com.herbal.herbalfax.common_screen.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.profile.Adapter.MyshoppingAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;
import java.util.List;

public class MyShoppingHistoryActivity extends AppCompatActivity {

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    RecyclerView  shoppingHistoryItemRecycler;
    MyshoppingAdapter myshoppingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping_history);
        shoppingHistoryItemRecycler = findViewById(R.id.shoppinghistoryitem);
        setMyShoppingHistory();
    }

    private void setMyShoppingHistory() {
        ArrayList<Interest> dealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        shoppingHistoryItemRecycler.setLayoutManager(RecyclerViewLayoutManager);
        myshoppingAdapter = new MyshoppingAdapter(dealsCategory,getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        shoppingHistoryItemRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        shoppingHistoryItemRecycler.setAdapter(myshoppingAdapter);
    }
}