package com.example.canteenpr.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenpr.Adapter.AdminProfileAdapter;
import com.example.canteenpr.Adapter.ProfileAdapter;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.Profile;
import com.example.canteenpr.R;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.InternetConnection;
import com.example.canteenpr.Utility.SessionManager;
import com.example.canteenpr.model.AdminList;
import com.example.canteenpr.model.AdminModel;
import com.example.canteenpr.model.TotalList;
import com.example.canteenpr.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    AdminProfileAdapter adminProfileAdapter;
    ArrayList<AdminModel> categories_data = new ArrayList<>();
    SessionManager sessionManager;
    TextView profile_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));



        sessionManager=new SessionManager(this);
        recyclerView=findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipetorefresh);

        profile_details=(TextView)findViewById(R.id.profile_detail);
        profile_details.setText(sessionManager.getStringData(Constants.KEY_MNUMBER));
        if (InternetConnection.isInternetAvailable(AdminProfileActivity.this)) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadProfile(sessionManager.getStringData(Constants.KEY_MNUMBER));
        } else {
            Toast.makeText(AdminProfileActivity.this, "Please check your intenet connection", Toast.LENGTH_SHORT).show();
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetConnection.isInternetAvailable(AdminProfileActivity.this)) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    loadProfile(sessionManager.getStringData(Constants.KEY_MNUMBER));
                } else {
                    Toast.makeText(AdminProfileActivity.this, "Please check your intenet connection", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void loadProfile(String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<AdminList> call=api.AdminProfileApi(sessionManager.getStringData(Constants.KEY_MNUMBER));
        call.enqueue(new Callback<AdminList>() {
            @Override
            public void onResponse(Call<AdminList> call, Response<AdminList> response) {
                if (response.isSuccessful())    {
                    List<AdminModel> categoryItems = response.body().getTotaldetails();
                    categories_data.clear();
                    recyclerView.removeAllViews();
                    //System.out.println("Student Attendance Data : "+categoryItems);

                    if (categoryItems != null && categoryItems.size() > 0) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        recyclerView.setVisibility(View.VISIBLE);
                        categories_data.addAll(categoryItems);
                        adminProfileAdapter = new AdminProfileAdapter(getApplicationContext(),categoryItems);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AdminProfileActivity.this);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adminProfileAdapter );


                    }else{
                        mSwipeRefreshLayout.setRefreshing(false);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(AdminProfileActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    // error case
                    mSwipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(AdminProfileActivity.this, "Fail to retrive data", Toast.LENGTH_SHORT).show();
                    System.out.println("Error : " + response.errorBody());
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(AdminProfileActivity.this, "Server Error 404", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoginActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            //Toast.makeText(LoginActivity.this, "Error 500", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AdminProfileActivity.this, "server broken", Toast.LENGTH_SHORT).show();

                            break;
                        default:
                            Toast.makeText(AdminProfileActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<AdminList> call, Throwable t) {

            }
        });

    }

}