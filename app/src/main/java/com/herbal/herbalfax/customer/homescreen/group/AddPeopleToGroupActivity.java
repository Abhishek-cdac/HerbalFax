package com.herbal.herbalfax.customer.homescreen.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.cart.selectdelivery.adapter.CartItemAdapter;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.AddPeopleListAdaptor;
import com.herbal.herbalfax.customer.interfaces.Onclick;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class AddPeopleToGroupActivity extends AppCompatActivity {

    RecyclerView RecyclerAddPeople;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    AddPeopleListAdaptor addPeopleListAdaptor;
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    private LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people_to_group);
        RecyclerAddPeople = findViewById(R.id.recycler_people);

        setaddpeoplelist();
    }

    private void setaddpeoplelist() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerAddPeople.setLayoutManager(RecyclerViewLayoutManager);
        addPeopleListAdaptor = new AddPeopleListAdaptor(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerAddPeople.setAdapter(addPeopleListAdaptor);
    }
}