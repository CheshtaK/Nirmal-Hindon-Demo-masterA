package com.example.cheshta.nirmalhindan.navigationActivities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cheshta.nirmalhindan.R;

public class CurrentEventActivity extends AppCompatActivity {
    TextView tvCurrentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr);

        tvCurrentEvent = findViewById(R.id.tvCurrentEvent);
        tvCurrentEvent.setPaintFlags(tvCurrentEvent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(this,EventActivity.class);
        startActivity(intent);
        finish();
    }
}
