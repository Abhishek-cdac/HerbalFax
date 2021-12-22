package com.herbal.herbalfax.customer.homescreen.products.toprated;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.utils.SpacesItemDecoration;
import com.herbal.herbalfax.customer.homescreen.products.adapter.CustomerProductListAdapter;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.vendor.sellerproduct.productlistmodel.StoreProduct;

import java.util.ArrayList;

public class TopRatedActivity extends AppCompatActivity {
    RecyclerView topRatedRecyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    private Onclick itemClick;
    TopRatedListAdapter topRatedListAdapter;
    ArrayList<StoreProduct> lst_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);
        topRatedRecyclerview = findViewById(R.id.topRatedrecyclerview);
        setUpRecylcerView();
    }
    private void setUpRecylcerView() {

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        topRatedRecyclerview.setLayoutManager(RecyclerViewLayoutManager);

        topRatedListAdapter = new TopRatedListAdapter(lst_product, getApplicationContext(), itemClick);
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        topRatedRecyclerview.setHasFixedSize(true);
        topRatedRecyclerview.addItemDecoration(new SpacesItemDecoration(2));
        topRatedRecyclerview.setAdapter(topRatedListAdapter);
    }
}