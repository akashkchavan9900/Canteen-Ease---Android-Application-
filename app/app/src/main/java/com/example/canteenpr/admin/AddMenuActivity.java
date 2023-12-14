package com.example.canteenpr.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.MainActivity;
import com.example.canteenpr.R;
import com.example.canteenpr.RegisterActivity;
import com.example.canteenpr.model.DataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMenuActivity extends AppCompatActivity {
    EditText et_name;
    EditText et_quantity;
    EditText et_price;
    Button submit;
    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));

        et_name=(EditText)findViewById(R.id.et_name);
        et_quantity=(EditText)findViewById(R.id.et_quantity);
        et_price=(EditText)findViewById(R.id.et_price);
        submit=(Button)findViewById(R.id.submit);

        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String quantity =et_quantity.getText().toString();
                String price = et_price.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(AddMenuActivity.this, "Please Enter Menu Name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(quantity)){
                    Toast.makeText(AddMenuActivity.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(price)){
                    Toast.makeText(AddMenuActivity.this, "Please Enter Price", Toast.LENGTH_SHORT).show();
                }

                else {
                    process_dialog= builder.build();
                    process_dialog.show();
                    addMenu(name,quantity,price);


                }
            }
        });

    }

    private void addMenu(String name, String quantity, String price) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<DataModel> call = api.addMenu(name, quantity, price);
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()){
                    process_dialog.dismiss();
                    DataModel resp =response.body();
                    if (resp != null){
                        if (resp.getRegistrationResult().getMessage().equals("Inserted Successfully")){
                            Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }process_dialog.dismiss();

                    }else {
                        Toast.makeText(getApplicationContext(), "No User Found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    process_dialog.dismiss();
                    System.out.println("Error : " + response.errorBody());
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(getApplicationContext(), "Server Error 404", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Toast.makeText(AddMenuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}