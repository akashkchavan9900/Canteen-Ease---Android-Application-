package com.example.canteenpr.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.canteenpr.R;
import com.example.canteenpr.Utility.Constants;
import com.example.canteenpr.Utility.SessionManager;

import java.security.Key;

public class PkIdActivity extends AppCompatActivity {
    TextView pk_id;
    String pkid;
    SessionManager sessionManager;
    MaterialDialog process_dialog;
    MaterialDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("cat"));

        pkid=intent.getExtras().getString("pk_id");
        SessionManager sessionManager=new SessionManager(this);

        pk_id=(TextView)findViewById(R.id.pkid);

        pk_id.setText(pkid);

        sessionManager.putStringData(Constants.KEY_PKID,pkid);

        builder= new MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true,0);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}