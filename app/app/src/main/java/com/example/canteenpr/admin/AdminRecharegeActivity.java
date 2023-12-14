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
import com.example.canteenpr.R;
import com.example.canteenpr.Transfer;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.SessionManager;
import com.example.canteenpr.dashboard;
import com.example.canteenpr.model.DataModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminRecharegeActivity extends AppCompatActivity {

    TextInputEditText et_pk,et_amount,et_mobile_number,et_password;
    Button btn_submit;
    String pk_id;
    String phone;

    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_recharege);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));
        phone=intent.getExtras().getString("Phone");
        pk_id=intent.getExtras().getString("pk_id");




        et_mobile_number=(TextInputEditText) findViewById(R.id.et_mobile_number);
        et_amount=(TextInputEditText) findViewById(R.id.et_amount);
        et_password=(TextInputEditText) findViewById(R.id.et_password);
        btn_submit=(Button) findViewById(R.id.btn_submit);
        et_mobile_number.setText(phone);



        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner_id = "1";
                String student_id = pk_id;
                String amount = et_amount.getText().toString();
                String m_number =et_mobile_number.getText().toString();
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(amount)) {
                    et_amount.setError("please enter amount");
                    et_amount.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(m_number)){
                    et_mobile_number.setError("please enter number");
                    et_mobile_number.requestFocus();
                    return;
                }else if (! password.equals(password)){
                    et_password.setError("please enter valid Password");
                    et_password.requestFocus();
                    return;
                }
                else {
                    process_dialog= builder.build();
                    process_dialog.show();
                    sucsesstransaction(owner_id,student_id,amount,m_number,password);
                }
            }
        });

    }

    private void sucsesstransaction(String owner_id,String student_id,String amount, String m_number, String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface api=retrofit.create(ApiInterface.class);
        Call<DataModel> call= api.StudentRechargeApi(owner_id,student_id,amount,m_number,password);
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()) {
                    process_dialog.dismiss();
                    DataModel resp=response.body();
                    if (resp != null){
                        if (resp.getRegistrationResult().getMessage().equals("Recharge Successfully"))
                        {
                            Toast.makeText(AdminRecharegeActivity.this,"Recharge Sucsessfully",Toast.LENGTH_SHORT);
                            Intent intent= new Intent(getApplicationContext(), AdminMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(AdminRecharegeActivity.this,"Something went wrong",Toast.LENGTH_SHORT);
                        }
                        process_dialog.dismiss();
                    }
                    else{
                        Toast.makeText(AdminRecharegeActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    process_dialog.dismiss();
                    System.out.println("Error"+response.errorBody());

                    switch (response.code()){

                        case 404:
                            Toast.makeText(AdminRecharegeActivity.this, "Server error 404", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(AdminRecharegeActivity.this, "Server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(AdminRecharegeActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}