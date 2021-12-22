package com.herbal.herbalfax.customer.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.common_screen.landingpage.CommonLandingActivity;
import com.herbal.herbalfax.common_screen.landingpage.adapter.TrendingAdapter;
import com.herbal.herbalfax.customer.interfaces.OnInnerFragmentClicks;
import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView notificationRecyclerView;
    private NotificationData[] notification_listnew = new NotificationData[]{
            new NotificationData("Comment"),
            new NotificationData("Like"),
            new NotificationData("Comment"),
            new NotificationData("Follow"),
            new NotificationData("Like"),
            new NotificationData("Follow")};
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    NotificationAdapter notificationAdapter;
    ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notificationRecyclerView = findViewById(R.id.rv_notification);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           onBackPressed();
                                       }
                                   }
        );
        setUpNotificationRecyclerView();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpNotificationRecyclerView() {
        //  notification_listnew= new ArrayList<>();
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
        notificationAdapter = new NotificationAdapter(notification_listnew, getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
        notificationRecyclerView.setLayoutManager(HorizontalLayout);
        notificationRecyclerView.setAdapter(notificationAdapter);

    }
}