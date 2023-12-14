package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.SessionManager;
import com.example.canteenpr.model.DataModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayActivity extends AppCompatActivity {
    TextInputEditText et_amount,et_mobile_number,et_password;
    Button btn_submit;

    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;
    String session_password;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("cat"));

        et_amount= findViewById(R.id.et_amount);
        et_password= findViewById(R.id.et_password);
        et_mobile_number= findViewById(R.id.et_mobile_number);
        btn_submit = findViewById(R.id.btn_submit);

        sessionManager= new SessionManager(this);


        session_password=sessionManager.getStringData(Constants.KEY_PASSWORD);
        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = et_amount.getText().toString();
                String receiver_phone = et_mobile_number.getText().toString();
                String sender_phone = sessionManager.getStringData(Constants.KEY_MNUMBER);
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(amount)) {
                    et_amount.setError("please enter amount");
                    et_amount.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(receiver_phone)){
                    et_mobile_number.setError("please enter amount");
                    et_mobile_number.requestFocus();
                    return;
                }
                else {
                    process_dialog= builder.build();
                    process_dialog.show();
                    sucsesstransaction(sender_phone,receiver_phone,amount,password);
                }


            }
        });


    }
    private void sucsesstransaction(String sender_phone, String receiver_phone, String amount,String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface api=retrofit.create(ApiInterface.class);
        Call<DataModel> call= api.paymentApi(sender_phone,receiver_phone,amount,password);
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()) {
                    process_dialog.dismiss();
                    DataModel resp=response.body();
                    if (resp != null){
                        if (resp.getRegistrationResult().getMessage().equals("Inserted Successfully"))

                        {
                            Toast.makeText(PayActivity.this,"send Sucsessfully",Toast.LENGTH_SHORT);
                            Intent intent= new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(PayActivity.this,"Something went wrong",Toast.LENGTH_SHORT);
                        }
                        process_dialog.dismiss();
                    }
                    else{
                        Toast.makeText(PayActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    process_dialog.dismiss();
                    System.out.println("Error"+response.errorBody());

                    switch (response.code()){

                        case 404:
                            Toast.makeText(PayActivity.this, "Server error 404", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(PayActivity.this, "Server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(PayActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Toast.makeText(PayActivity.this, "unknown error", Toast.LENGTH_SHORT).show();

            }
        });



    }

    public boolean onOptionsItemSelected (MenuItem item){
            onBackPressed();
            return true;
        }
    }