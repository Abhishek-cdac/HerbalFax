package com.herbal.herbalfax.common_screen.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.profile.Adapter.MydealsAdapter;
import com.herbal.herbalfax.common_screen.profile.Adapter.MyshoppingAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;
import java.util.List;

public class MyShoppingHistoryActivity extends AppCompatActivity {

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private List<Interest> DealsCategory = new ArrayList<>();
    LinearLayoutManager HorizontalLayout;
    RecyclerView  shoppinghistoryitemRecycler;
    MyshoppingAdapter myshoppingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping_history);
        shoppinghistoryitemRecycler = findViewById(R.id.shoppinghistoryitem);
        setMyShoppingHistory();
    }

    private void setMyShoppingHistory() {
        DealsCategory = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        shoppinghistoryitemRecycler.setLayoutManager(RecyclerViewLayoutManager);
        myshoppingAdapter = new MyshoppingAdapter((ArrayList<Interest>) DealsCategory,getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        shoppinghistoryitemRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
//      recyclerView.setHasFixedSize(true);
//      recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        shoppinghistoryitemRecycler.setAdapter(myshoppingAdapter);
    }
}