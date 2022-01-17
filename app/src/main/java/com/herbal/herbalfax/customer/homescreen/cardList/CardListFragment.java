package com.herbal.herbalfax.customer.homescreen.cardList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.feed.FeedFragment;
import com.herbal.herbalfax.customer.interfaces.OnInnerFragmentClicks;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;


public class CardListFragment extends Fragment implements OnInnerFragmentClicks {

    private CardListViewModel cardListViewModel;
    RecyclerView cardListRecyclerview;
    private ArrayList<Interest> lst_cardList;
    CardListAdapter cardListAdapter;
    Button add_card_btn;

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardListViewModel =
                ViewModelProviders.of(this).get(CardListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cardlist, container, false);

        cardListRecyclerview = root.findViewById(R.id.cardListRecyclerview);
        add_card_btn = root.findViewById(R.id.add_card_btn);
        setUpCardListRecyclerView();
        add_card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  ReplaceFrag(new AddCardFragment());
            }
        });

        return root;
    }

    private void setUpCardListRecyclerView() {

        lst_cardList = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        cardListRecyclerview.setLayoutManager(RecyclerViewLayoutManager);
        cardListAdapter = new CardListAdapter(lst_cardList, getActivity());
        LinearLayoutManager  HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cardListRecyclerview.setLayoutManager(HorizontalLayout);
        cardListRecyclerview.setAdapter(cardListAdapter);
    }

    @Override
    public void ReplaceFrag(@Nullable Fragment fragment) {
        try {
          //  CurrentFrag = fragment;

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            if (fragmentManager.getFragments().size() > 0) {
                ft.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName());
            }

            ft.commitAllowingStateLoss();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ReplaceFragWithStack(@Nullable Fragment fragment) {
        try {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.hide(new FeedFragment());
            ft.replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName());
            ft.addToBackStack("tags");
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}