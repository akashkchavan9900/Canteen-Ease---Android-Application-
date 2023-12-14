package com.example.canteenpr.admin;

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
import com.example.canteenpr.Adapter.StudentListAdapter;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.R;
import com.example.canteenpr.Utility.InternetConnection;
import com.example.canteenpr.model.UserListModel;
import com.example.canteenpr.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentListActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    SwipeRefreshLayout mSwipeRefreshLayout;
    StudentListAdapter studentListAdapter;
    ArrayList<UserModel> categories_data = new ArrayList<UserModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));

        recyclerView=findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipetorefresh);

        if (InternetConnection.isInternetAvailable(StudentListActivity.this)) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadMenu();
        } else {
            Toast.makeText(StudentListActivity.this, "Please check your intenet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadMenu() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<UserListModel> call=api.StudentListApi();
        call.enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {

                if (response.isSuccessful()) {
                    List<UserModel> categoryItems = response.body().getUserList();
                    categories_data.clear();
                    recyclerView.removeAllViews();
                    //System.out.println("Student Attendance Data : "+categoryItems);

                    if (categoryItems != null && categoryItems.size() > 0) {
                        mSwipeRefreshLayout.setRefreshing(false);

                        recyclerView.setVisibility(View.VISIBLE);
                        categories_data.addAll(categoryItems);
                        studentListAdapter = new StudentListAdapter(getApplicationContext(), categoryItems);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StudentListActivity.this);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(studentListAdapter);


                    }else{
                        mSwipeRefreshLayout.setRefreshing(false);

                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(StudentListActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserListModel> call, Throwable t) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}