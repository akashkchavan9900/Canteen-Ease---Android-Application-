package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.canteenpr.Api.ApiInterface;
import com.example.canteenpr.Api.ApiUrl;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.model.DataModel;
import com.example.canteenpr.Utility.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {


    EditText et_studentname;
    EditText et_birthdate;
    EditText et_mobileno;
    EditText et_collageId;
    EditText et_address;
    EditText et_pass;
    EditText et_confirmpass;
    Button bt_submit;
    RadioGroup rg_gender;
    RadioButton rt_f;
    RadioButton rt_m;
    Spinner sp_dept;
    Spinner sp_year;
    String Gender="";
    TextView tv_department;
    TextView tv_acadamicyear;
    Button bt_date;

    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_studentname=(EditText)findViewById(R.id.et_fname);
        et_birthdate=(EditText)findViewById(R.id.et_birthdate);
        et_mobileno=(EditText)findViewById(R.id.et_mob);
        et_collageId=(EditText)findViewById(R.id.et_collageid);
        et_address=(EditText)findViewById(R.id.et_address);
        et_pass=(EditText)findViewById(R.id.et_password);
        et_confirmpass=(EditText)findViewById(R.id.et_confirm_password);
        bt_submit=(Button) findViewById(R.id.bt_submit);
        sp_dept=(Spinner)findViewById(R.id.s_department);
        sp_year=(Spinner)findViewById(R.id.s_acadamic_year);
        rg_gender=(RadioGroup)findViewById(R.id.rg_gender);

        bt_date=(Button)findViewById(R.id.bt_date);

        sessionManager= new SessionManager(this);


        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);


        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_m){
                    Gender="Male";
                }
                else if (checkedId==R.id.rb_f){
                    Gender="Female";
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_studentname.getText().toString();
                String dob = et_birthdate.getText().toString();
                String m_number = et_mobileno.getText().toString();
                String college_id = et_collageId.getText().toString();
                String address = et_address.getText().toString();
                String password = et_pass.getText().toString();
                String confirmpassword = et_confirmpass.getText().toString();
                String gender = Gender;
                String department = sp_dept.getSelectedItem().toString();
                String acedemic_year = sp_year.getSelectedItem().toString();

                String wallet="0";

                if (TextUtils.isEmpty(name)) {
                    et_studentname.setError("Please enter name");
                    et_studentname.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(dob)) {
                    et_birthdate.setError("please enter birthdate");
                    et_birthdate.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(m_number)) {
                    et_mobileno.setError("please enter mobile number");
                    et_mobileno.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(college_id)) {
                    et_collageId.setError("please enter collage id number");
                    et_collageId.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(address)) {
                    et_address.setError("please enter address");
                    et_address.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    et_pass.setError("please enter password");
                    et_pass.requestFocus();
                    return;
                } else if (!password.equals(confirmpassword)) {
                    et_confirmpass.setError("password does not match");
                    et_confirmpass.requestFocus();
                    return;
                }
                else if (sp_dept.getSelectedItem().equals("Select Department")) {
                    Toast.makeText(RegisterActivity.this,"Please select department",Toast.LENGTH_SHORT);
                }
                else if (sp_year.getSelectedItem().equals("Select Acadamic Year")) {
                    Toast.makeText(RegisterActivity.this,"Please select year",Toast.LENGTH_SHORT);
                }
                else {
                    process_dialog= builder.build();
                    process_dialog.show();
                    regestrationprocess(name,dob,m_number, college_id,address,password,gender,department,acedemic_year,wallet);

                }
            }
        });

        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datepicker = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_birthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        sp_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String department = parent.getSelectedItem().toString();
               // tv_department.setText(department);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String acedemic_year= parent.getSelectedItem().toString();
                //tv_acadamicyear.setText(acedemic_year);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


    }

    private void regestrationprocess(String name, String dob,String m_number,String college_id,String address,String password, String gender, String department,String acedemic_year,String wallet) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface api=retrofit.create(ApiInterface.class);
        Call<DataModel> call= api.RegistrationApi(name,dob,m_number,college_id,address,password,gender,department,acedemic_year,wallet);
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()){
                    process_dialog.dismiss();
                    DataModel resp =response.body();
                    Log.d("Error",resp.toString());
                    if (resp != null){
                        if (resp.getRegistrationResult().getMessage().equals("Inserted Successfully")){
                            Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
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
                process_dialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}