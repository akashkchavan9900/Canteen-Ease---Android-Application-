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

import com.example.canteenpr.Adapter.MenuAdapter;
import com.example.canteenpr.Adapter.ProfileAdapter;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.InternetConnection;
import com.example.canteenpr.model.MenuList;
import com.example.canteenpr.model.MenuModel;
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


public class Menucard extends AppCompatActivity {


    RecyclerView recyclerView;

    SwipeRefreshLayout mSwipeRefreshLayout;
    MenuAdapter menuAdapter;
    ArrayList<MenuModel> categories_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menucard);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));

        recyclerView=findViewById(R.id.menu_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipetorefresh);
        if (InternetConnection.isInternetAvailable(Menucard.this)) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadMenu();
        } else {
            Toast.makeText(Menucard.this, "Please check your intenet connection", Toast.LENGTH_SHORT).show();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetConnection.isInternetAvailable(Menucard.this)) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    loadMenu();
                } else {
                    Toast.makeText(Menucard.this, "Please check your intenet connection", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void loadMenu() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<MenuList>call=api.userMenuApi();
        call.enqueue(new Callback<MenuList>() {
            @Override
            public void onResponse(Call<MenuList> call, Response<MenuList> response) {
                if (response.isSuccessful()) {
                    List<MenuModel> categoryItems = response.body().getTotalmenu();
                    categories_data.clear();
                    recyclerView.removeAllViews();
                    //System.out.println("Student Attendance Data : "+categoryItems);

                    if (categoryItems != null && categoryItems.size() > 0) {
                        mSwipeRefreshLayout.setRefreshing(false);

                        recyclerView.setVisibility(View.VISIBLE);
                        categories_data.addAll(categoryItems);
                        menuAdapter = new MenuAdapter(getApplicationContext(),categoryItems);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Menucard.this);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(menuAdapter);


                    }else{
                        mSwipeRefreshLayout.setRefreshing(false);

                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(Menucard.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    // error case
                    mSwipeRefreshLayout.setRefreshing(false);

                    recyclerView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(Menucard.this, "Fail to retrive data", Toast.LENGTH_SHORT).show();
                    System.out.println("Error : " + response.errorBody());
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(Menucard.this, "Server Error 404", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoginActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            //Toast.makeText(LoginActivity.this, "Error 500", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Menucard.this, "server broken", Toast.LENGTH_SHORT).show();

                            break;
                        default:
                            Toast.makeText(Menucard.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<MenuList> call, Throwable t) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}

