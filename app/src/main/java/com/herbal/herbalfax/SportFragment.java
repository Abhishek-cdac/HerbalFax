package com.herbal.herbalfax;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.customer.blogs.BlogDetailsActivity;
import com.herbal.herbalfax.customer.interfaces.Onclick;

import java.util.ArrayList;
import java.util.Arrays;

public class SportFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;

    Onclick itemClick;

    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4",
            "Person 5", "Person 6", "Person 7"));

    public SportFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_blogs, container, false);
        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String blogId, String s) {

            }

            @Override
            public void onItemClicks(View view, int position, int i, String blogId) {
                if (i == 20) {
                    Intent intent = new Intent(getActivity(), BlogDetailsActivity.class);

                    startActivity(intent);

                }
            }
        };
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.blog_recycler);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        DailyBlogAdapterTab dailyBlogAdapter1 = new DailyBlogAdapterTab(personNames, getContext(), itemClick);
        recyclerView.setAdapter(dailyBlogAdapter1);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}