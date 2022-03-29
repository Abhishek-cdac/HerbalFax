package com.herbal.herbalfax.customer.homescreen.addcard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.herbal.herbalfax.R;
import com.herbal.herbalfax.customer.homescreen.cardList.CardListViewModel;
import com.herbal.herbalfax.customer.homescreen.feed.FeedFragment;
import com.herbal.herbalfax.customer.interfaces.OnInnerFragmentClicks;
public class AddCardFragment extends Fragment implements OnInnerFragmentClicks {

    private CardListViewModel cardListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardListViewModel = ViewModelProviders.of(this).get(CardListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addcardlist, container, false);
        return root;

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