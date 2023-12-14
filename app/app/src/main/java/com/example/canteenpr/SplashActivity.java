package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.SessionManager;
import com.example.canteenpr.admin.AdminMainActivity;

public class SplashActivity extends AppCompatActivity {

    String phone = "";
    String role;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSessionManager = new SessionManager(this);
        phone = mSessionManager.getStringData(Constants.KEY_MNUMBER);
        role = mSessionManager.getStringData(Constants.KEY_ROLE);



        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                if (phone.equals("")) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                } else {

                    if (role.equalsIgnoreCase("admin")){
                        Intent intent = new Intent(SplashActivity.this, AdminMainActivity.class);
                        intent.putExtra("cat","Admin Home");
                        startActivity(intent);
                        Toast.makeText(SplashActivity.this, "Welcome Back Admin", Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (role.equalsIgnoreCase("student")){
                        Intent intent = new Intent(SplashActivity.this, dashboard.class);
                        intent.putExtra("cat","Student Home");
                        startActivity(intent);
                        Toast.makeText(SplashActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(SplashActivity.this, "please login first", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }


            }
        }, 2000);
    }
}