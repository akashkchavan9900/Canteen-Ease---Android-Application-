package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.canteenpr.Adapter.HistoryAdapter;
import com.example.canteenpr.Adapter.ProfileAdapter;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.InternetConnection;
import com.example.canteenpr.Utility.SessionManager;
import com.example.canteenpr.model.HistoryList;
import com.example.canteenpr.model.HistoryModel;
import com.example.canteenpr.model.TotalList;
import com.example.canteenpr.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History extends AppCompatActivity {


    RecyclerView recyclerView;

    SwipeRefreshLayout mSwipeRefreshLayout;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryModel> categories_data = new ArrayList<>();


    SessionManager  sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));

        sessionManager=new SessionManager(this);



        recyclerView=findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipetorefresh);
        recyclerView.setEnabled(false);


        if (InternetConnection.isInternetAvailable(History.this)) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadHistory(sessionManager.getStringData(Constants.KEY_MNUMBER));
        } else {
            Toast.makeText(History.this, "Please check your intenet connection", Toast.LENGTH_SHORT).show();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetConnection.isInternetAvailable(History.this)) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    loadHistory(sessionManager.getStringData(Constants.KEY_MNUMBER));
                } else {
                    Toast.makeText(History.this, "Please check your intenet connection", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void loadHistory(String mnumber) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<HistoryList> call=api.historyApi(sessionManager.getStringData(Constants.KEY_MNUMBER));

        call.enqueue(new Callback<HistoryList>() {
            @Override
            public void onResponse(Call<HistoryList> call, Response<HistoryList> response) {
                if (response.isSuccessful()) {
                    List<HistoryModel> categoryItems = response.body().getHistrorydetails();
                    categories_data.clear();
                    recyclerView.removeAllViews();
                    //System.out.println("Student Attendance Data : "+categoryItems);

                    if (categoryItems != null && categoryItems.size() > 0) {
                        mSwipeRefreshLayout.setRefreshing(false);

                        recyclerView.setVisibility(View.VISIBLE);
                        categories_data.addAll(categoryItems);
                        historyAdapter = new HistoryAdapter(getApplicationContext(),categoryItems);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(History.this);
                        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(historyAdapter);


                    }else{
                        mSwipeRefreshLayout.setRefreshing(false);

                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(History.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    // error case
                    mSwipeRefreshLayout.setRefreshing(false);

                    recyclerView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(History.this, "Fail to retrive data", Toast.LENGTH_SHORT).show();
                    System.out.println("Error : " + response.errorBody());
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(History.this, "Server Error 404", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoginActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            //Toast.makeText(LoginActivity.this, "Error 500", Toast.LENGTH_SHORT).show();
                            Toast.makeText(History.this, "server broken", Toast.LENGTH_SHORT).show();

                            break;
                        default:
                            Toast.makeText(History.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryList> call, Throwable t) {

            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}