package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {

    LinearLayout tv_profile;
    LinearLayout tv_transfer;
    LinearLayout tv_recharge;
    LinearLayout tv_history;
    LinearLayout tv_menu;
    CardView tv_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));


        tv_profile=(LinearLayout)findViewById(R.id.profile);
        tv_transfer=(LinearLayout)findViewById(R.id.transfer);
        tv_recharge=(LinearLayout)findViewById(R.id.recharge);
        tv_history=(LinearLayout)findViewById(R.id.history);
        tv_menu=(LinearLayout)findViewById(R.id.tv_menu);
        tv_pay=(CardView) findViewById(R.id.tv_pay);


        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Profileintent=new Intent(dashboard.this,Profile.class);
                Profileintent.putExtra("cat"," My Profile");
                startActivity(Profileintent);
            }
        });

        tv_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Transferrintent=new Intent(dashboard.this,Transfer.class);
                Transferrintent.putExtra("cat","Transfer");
                startActivity(Transferrintent);
            }
        });

        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Rechargeintent=new Intent(dashboard.this,Recharge.class);
                Rechargeintent.putExtra("cat","Recharge");
                startActivity(Rechargeintent);
            }
        });

        tv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Historyintent=new Intent(dashboard.this,History.class);
                Historyintent.putExtra("cat","History");
                startActivity(Historyintent);
            }
        });

        tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Menuintent=new Intent(dashboard.this,Menucard.class);
                Menuintent.putExtra("cat","Menu");
                startActivity(Menuintent);
            }
        });


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Rechargeintent=new Intent(dashboard.this,PayActivity.class);
                Rechargeintent.putExtra("cat","Pay Money");
                startActivity(Rechargeintent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu,add items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);//Menu ResourceFile
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(dashboard.this,MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


}