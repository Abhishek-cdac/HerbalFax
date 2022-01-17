package com.herbal.herbalfax.customer.homescreen.group;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.DiscoveryAdapter;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.YourGroupAdapter;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;
import java.util.List;


public class GroupsFragment extends Fragment {
    private List<Interest> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewdiscovery;
    private TextView yourgroup;
    private TextView discovery;
    private YourGroupAdapter mAdapter;
    private DiscoveryAdapter mAdaptery;
    private TextView creategroup;
    private GroupsViewModel groupsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     //   groupsViewModel = ViewModelProviders.of(this).get(GroupsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerViewdiscovery = root.findViewById(R.id.recycler_view_discover);
        yourgroup =  root.findViewById(R.id.yourgroup);
        discovery =  root.findViewById(R.id.discovery);
        creategroup =  root.findViewById(R.id.creategroup);

        yourgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewdiscovery.setVisibility(View.GONE);
            }
        });

        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*openDiscovery();*/
                recyclerViewdiscovery.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(intent);
            }
        });
        setyourgroup();
       // prepareMovieData();
        setdiscovery();
        return root;
    }

    private void setyourgroup() {
        mAdapter = new YourGroupAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
    private void setdiscovery() {
        mAdaptery = new DiscoveryAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewdiscovery.setLayoutManager(mLayoutManager);
        recyclerViewdiscovery.setItemAnimator(new DefaultItemAnimator());
        recyclerViewdiscovery.setAdapter(mAdaptery);
    }
   /* private void prepareMovieData() {
        Movie movie = new Movie(R.drawable.one,"Mad Max: Fury Road");
        movieList.add(movie);

        movie = new Movie(R.drawable.two,"Inside Out");
        movieList.add(movie);

        movie = new Movie(R.drawable.three,"Star Wars: Episode VII - The Force Awakens");
        movieList.add(movie);

        movie = new Movie(R.drawable.four,"Shaun the Sheep");
        movieList.add(movie);

        movie = new Movie(R.drawable.five,"Shaun the Sheep");
        movieList.add(movie);
    }*/
}