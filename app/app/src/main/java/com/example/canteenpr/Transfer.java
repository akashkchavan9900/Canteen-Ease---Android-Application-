package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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


public class Transfer extends AppCompatActivity {

    private static final int PICK_CONTACT = 1;
    private static final int REQUEST_READ_CONTACTS = 2;
    public static final int REQUEST_SHOW_DETAILS = 3;

    TextInputEditText et_amount,et_mobile_number,et_password,et_s_phone;
    TextView btn_search_contact;
    Button btn_submit;


    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;
    String session_password;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("cat"));

        et_amount= findViewById(R.id.et_amount);
        et_s_phone= findViewById(R.id.et_s_phone);
        et_password= findViewById(R.id.et_password);
        et_mobile_number= findViewById(R.id.et_mobile_number);
        btn_search_contact= findViewById(R.id.btn_search_contact);
        btn_submit = findViewById(R.id.btn_submit);


        sessionManager= new SessionManager(this);

        et_s_phone.setText(sessionManager.getStringData(Constants.KEY_MNUMBER));

        session_password=sessionManager.getStringData(Constants.KEY_PASSWORD);
        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);


        btn_search_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_READ_CONTACTS);
                } else {

                    // Permission has already been granted
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                }
            }
        });


    btn_submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String amount = et_amount.getText().toString();
            String reciver_phone = et_mobile_number.getText().toString();
            String sender_phone = et_s_phone.getText().toString();
            String password = et_password.getText().toString();

            if (TextUtils.isEmpty(amount)) {
                et_amount.setError("please enter amount");
                et_amount.requestFocus();
                return;
            }else if (TextUtils.isEmpty(reciver_phone)){
                et_mobile_number.setError("please enter amount");
                et_mobile_number.requestFocus();
                return;
            }else if (! password.equals(session_password)){
                et_password.setError("please enter valid Password");
                et_password.requestFocus();
                return;
            }
            else {
                process_dialog= builder.build();
                process_dialog.show();
                sucsesstransaction(sender_phone,reciver_phone,amount);
            }


        }
    });




    }

    private void sucsesstransaction(String sender_phone,String reciver_phone, String amount) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface api=retrofit.create(ApiInterface.class);
        Call<DataModel> call= api.TransferApi(sender_phone,reciver_phone,amount);

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()) {
                    process_dialog.dismiss();
                    DataModel resp=response.body();
                    if (resp != null){
                        if (resp.getRegistrationResult().getMessage().equals("Inserted Sucessfully"))
                        {
                            Toast.makeText(Transfer.this,"Register Sucsessfully",Toast.LENGTH_SHORT);
                            Intent intent= new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(Transfer.this,"Something went wrong",Toast.LENGTH_SHORT);
                        }
                        process_dialog.dismiss();
                    }
                    else{
                        Toast.makeText(Transfer.this, "No user found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    process_dialog.dismiss();
                    System.out.println("Error"+response.errorBody());

                    switch (response.code()){

                        case 404:
                            Toast.makeText(Transfer.this, "Server error 404", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(Transfer.this, "Server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(Transfer.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            Cursor cursor = null;
            try {
                String phoneNo = null;
                String name = null;
                // getData() method will have the Content Uri of the selected contact
                Uri uri = data.getData();
                //Query the content uri
                cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                // column index of the phone number
                int phoneIndex = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER);
                // column index of the contact name
                int nameIndex = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                phoneNo = cursor.getString(phoneIndex);
                name = cursor.getString(nameIndex);

                et_mobile_number.setText(phoneNo);

            } catch (Exception e) {
                Toast.makeText(this, "Error to choose contact", Toast.LENGTH_SHORT).show();
            }
        }
        }
        public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}