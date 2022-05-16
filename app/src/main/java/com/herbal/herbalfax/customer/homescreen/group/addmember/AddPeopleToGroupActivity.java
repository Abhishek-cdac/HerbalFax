package com.herbal.herbalfax.customer.homescreen.group.addmember;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.R;
import com.herbal.herbalfax.api.GetDataService;
import com.herbal.herbalfax.api.RetrofitClientInstance;
import com.herbal.herbalfax.common_screen.dialog.TransparentProgressDialog;
import com.herbal.herbalfax.common_screen.utils.CommonClass;
import com.herbal.herbalfax.common_screen.utils.session.SessionPref;
import com.herbal.herbalfax.customer.homescreen.group.Adaptor.AddPeopleListAdaptor;
import com.herbal.herbalfax.customer.homescreen.group.creategroup.CreateGroupActivity;
import com.herbal.herbalfax.customer.homescreen.group.addmember.model.User;
import com.herbal.herbalfax.customer.homescreen.group.addmember.model.UserListModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPeopleToGroupActivity extends AppCompatActivity {

    RecyclerView RecyclerAddPeople;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    AddPeopleListAdaptor addPeopleListAdaptor;
    ArrayList<User> lst_membar = new ArrayList<>();
    private LinearLayoutManager HorizontalLayout;
    CommonClass clsCommon;
    ImageView selectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people_to_group);
        RecyclerAddPeople = findViewById(R.id.recycler_people);
        selectBtn = findViewById(R.id.selectBtn);

        clsCommon = new CommonClass();

        callUserListAPI();
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addPeopleListAdaptor.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < addPeopleListAdaptor.getSelected().size(); i++) {
                        stringBuilder.append(addPeopleListAdaptor.getSelected().get(i).getIdusers());
                        stringBuilder.append(",");
                    }
                    Log.e("AddPeople", "" + stringBuilder.toString().trim());
                    Intent intent = new Intent(AddPeopleToGroupActivity.this, CreateGroupActivity.class);
                    intent.putExtra("member", stringBuilder.toString().trim());
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("AddPeople", "No Selection");
                }
            }
        });
    }

    private void callUserListAPI() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(AddPeopleToGroupActivity.this);
        pd.show();

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "30");
        hashMap.put("offset", "");
        hashMap.put("search_key", "");

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<UserListModel> call = service.userGetUserList("Bearer " + pref.getStringVal(SessionPref.LoginJwtoken), hashMap);
        call.enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(@NonNull Call<UserListModel> call, @NonNull Response<UserListModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        lst_membar = (ArrayList<User>) response.body().getData().getUsers();
                        if (lst_membar == null) {
                            lst_membar = new ArrayList<>();
                        }
                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                        RecyclerAddPeople.setLayoutManager(RecyclerViewLayoutManager);
                        addPeopleListAdaptor = new AddPeopleListAdaptor(lst_membar, getApplicationContext());
                        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerAddPeople.setAdapter(addPeopleListAdaptor);

                    } else {
                        clsCommon.showDialogMsg(AddPeopleToGroupActivity.this, "HerbalFax", "" + response.body().getErrors(), "Ok");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserListModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AddPeopleToGroupActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}