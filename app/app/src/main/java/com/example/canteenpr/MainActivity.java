package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.canteenpr.Adapter.AdminProfileAdapter;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.SessionManager;
import com.example.canteenpr.admin.AdminMainActivity;
import com.example.canteenpr.admin.AdminProfileActivity;
import com.example.canteenpr.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText et_m_number, etpass;
    Button btlogin;
    TextView tvregister;
    Spinner usertype;
    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;
    SessionManager mSessionManager;
    LinearLayout llRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_m_number=(EditText)findViewById(R.id.editText1);
        etpass=(EditText)findViewById(R.id.etpass);
        btlogin=(Button)findViewById(R.id.bt_login);
        tvregister=(TextView)findViewById(R.id.register);
        usertype=(Spinner)findViewById(R.id.u_type);
        llRegister=(LinearLayout) findViewById(R.id.llRegister);

        mSessionManager=new SessionManager(this);
        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registerintent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(Registerintent);
            }
        });

        usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(usertype.getSelectedItem().equals("Admin")){
                    llRegister.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                return;
            }
        });

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_number=et_m_number.getText().toString().trim();
                String password=etpass.getText().toString().trim();


                if (TextUtils.isEmpty(m_number)) {
                    et_m_number.setError("Please enter mobile number");
                    et_m_number.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(password)) {
                    etpass.setError("please enter password");
                    etpass.requestFocus();
                    return;
                }else if (usertype.getSelectedItem().equals("Admin")){
                    process_dialog= builder.build();
                    process_dialog.show();
                    AdminLoginProcess(m_number,password);
                }
                else if(usertype.getSelectedItem().equals("User")){
                    process_dialog= builder.build();
                    process_dialog.show();
                    LoginProcess(m_number,password);
                }else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void AdminLoginProcess(String m_number, String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface api=retrofit.create(ApiInterface.class);
        Call<UserModel> call =api.adminLoginApi(m_number,password);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    process_dialog.dismiss();
                    UserModel resp = response.body();
                    if(resp!=null){
                        if(resp.getMessage().equals("success")){
                            mSessionManager.putStringData(Constants.KEY_PKID, resp.getPkid());
                            mSessionManager.putStringData(Constants.KEY_NAME, resp.getName());
                            mSessionManager.putStringData(Constants.KEY_ROLE, "admin");
                            mSessionManager.putStringData(Constants.KEY_GENDER, resp.getGender());
                            mSessionManager.putStringData(Constants.KEY_DOB, resp.getBdate());
                            mSessionManager.putStringData(Constants.KEY_MNUMBER,et_m_number.getText().toString());
                            mSessionManager.putStringData(Constants.KEY_DEPARTMENT, resp.getDepartment());
                            mSessionManager.putStringData(Constants.KEY_ACEDEMIC_YEAR, resp.getAcadamic_year());
                            mSessionManager.putStringData(Constants.KEY_COLLEGE_ID, resp.getColg_id());
                            mSessionManager.putStringData(Constants.KEY_ADDRESS, resp.getAddress());
                            mSessionManager.putStringData(Constants.KEY_PASSWORD, etpass.getText().toString());
                            goToAdminHome();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    process_dialog.dismiss();
                    System.out.println("Error"+response.errorBody());
                    switch (response.code()){
                        case 404:
                            Toast.makeText(MainActivity.this, "Server error 404", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(MainActivity.this, "Server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                process_dialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                process_dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoginProcess(String m_number, String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface api=retrofit.create(ApiInterface.class);
        Call<UserModel> call =api.userLoginApi(m_number,password);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    process_dialog.dismiss();
                    UserModel resp = response.body();
                    if(resp!=null){
                        if(resp.getMessage().equals("success"))
                        {
                            mSessionManager.putStringData(Constants.KEY_PKID, resp.getPkid());
                            mSessionManager.putStringData(Constants.KEY_NAME, resp.getName());
                            mSessionManager.putStringData(Constants.KEY_GENDER, resp.getGender());
                            mSessionManager.putStringData(Constants.KEY_DOB, resp.getBdate());
                            mSessionManager.putStringData(Constants.KEY_ROLE, "student");
                            mSessionManager.putStringData(Constants.KEY_MNUMBER,et_m_number.getText().toString());
                            mSessionManager.putStringData(Constants.KEY_DEPARTMENT, resp.getDepartment());
                            mSessionManager.putStringData(Constants.KEY_ACEDEMIC_YEAR, resp.getAcadamic_year());
                            mSessionManager.putStringData(Constants.KEY_COLLEGE_ID, resp.getColg_id());
                            mSessionManager.putStringData(Constants.KEY_ADDRESS, resp.getAddress());
                            mSessionManager.putStringData(Constants.KEY_PASSWORD, etpass.getText().toString());
                            goToHome();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    process_dialog.dismiss();
                    System.out.println("Error"+response.errorBody());
                    switch (response.code()){
                        case 404:
                            Toast.makeText(MainActivity.this, "Server error 404", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(MainActivity.this, "Server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                process_dialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                process_dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void goToHome() {
        Intent intent=new Intent(MainActivity.this,dashboard.class);
        intent.putExtra("cat","Student Home");
        startActivity(intent);
        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void goToAdminHome() {
        Intent intent=new Intent(MainActivity.this, AdminMainActivity.class);
        intent.putExtra("cat","Canteen Owner Home");
        startActivity(intent);
        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}