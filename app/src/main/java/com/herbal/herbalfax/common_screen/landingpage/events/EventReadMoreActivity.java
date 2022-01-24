package com.herbal.herbalfax.common_screen.landingpage.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.adapter.AddCommentsAdapter;
import com.herbal.herbalfax.customer.bottomsheet.BlogsBottomSheet;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.AddPeopleListAdaptor;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class EventReadMoreActivity extends AppCompatActivity {
    ImageView addcomments;
    RecyclerView RecyclerAddPeopleComment;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    AddCommentsAdapter addPeoplecommentAdaptor;
    ArrayList<Interest> lst_cart_item = new ArrayList<>();
    private LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_read_more);
        addcomments = findViewById(R.id.addcomments);
        RecyclerAddPeopleComment = findViewById(R.id.addcommentsprofile);
            setaddpeoplecomment();

        addcomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(0);

            }
        });

    }
    private void showBottomSheet(int adapterPosition) {
        // boolean notification = lst.get(adapterPosition).getNotifyStatus().equals("On");

        FragmentManager fragmentManager = getSupportFragmentManager();
        AddCommentbottomsheet sheet = new AddCommentbottomsheet(EventReadMoreActivity.this, adapterPosition);
        sheet.show(fragmentManager, "comment bottom sheet");
    }

    private void setaddpeoplecomment() {
        lst_cart_item = new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerAddPeopleComment.setLayoutManager(RecyclerViewLayoutManager);
        addPeoplecommentAdaptor = new AddCommentsAdapter(lst_cart_item, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerAddPeopleComment.setAdapter(addPeoplecommentAdaptor);
    }
}