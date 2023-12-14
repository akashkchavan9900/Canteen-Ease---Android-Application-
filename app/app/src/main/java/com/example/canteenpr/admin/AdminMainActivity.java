package com.example.canteenpr.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenpr.History;
import com.example.canteenpr.MainActivity;
import com.example.canteenpr.Profile;
import com.example.canteenpr.R;
import com.example.canteenpr.RegisterActivity;
import com.example.canteenpr.dashboard;

public class AdminMainActivity extends AppCompatActivity {

    ImageView profile;
    LinearLayout add_menu,history,recharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));

       // profile=(ImageView) findViewById(R.id.profile);
        add_menu=(LinearLayout) findViewById(R.id.add_menu);
        history=(LinearLayout) findViewById(R.id.history);
        recharge=(LinearLayout) findViewById(R.id.recharge);

      /*  profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),AdminProfileActivity.class);
                startActivity(intent);
            }
        });*/
        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddMenuintent=new Intent(AdminMainActivity.this, AddMenuActivity.class);
                AddMenuintent.putExtra("cat","Add Menu");
                startActivity(AddMenuintent);

            }
        });
       /* recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent Rechargeintent=new Intent(AdminMainActivity.this, AdminRecharegeActivity.class);
                    Rechargeintent.putExtra("cat","Recharge");
                    startActivity(Rechargeintent);

                }
        });*/
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Rechargeintent=new Intent(AdminMainActivity.this, AdminHistoryActivity.class);
                Rechargeintent.putExtra("cat","History");
                startActivity(Rechargeintent);

            }
        });
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Student_listintent=new Intent(AdminMainActivity.this, StudentListActivity.class);
                Student_listintent.putExtra("cat","Student List");
                startActivity(Student_listintent);

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
                Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}