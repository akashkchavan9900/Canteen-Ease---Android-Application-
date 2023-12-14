package com.example.canteenpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class Moneytransfer extends AppCompatActivity {

    Button bt_send;

    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneytransfer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));

        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);

        bt_send=(Button)findViewById(R.id.send);
        bt_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                process_dialog= builder.build();
                process_dialog.show();


                Intent intent=new Intent(getApplicationContext(),PinActivity.class);
                intent.putExtra("cat","Pin");
                startActivity(intent);
                process_dialog.dismiss();

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}