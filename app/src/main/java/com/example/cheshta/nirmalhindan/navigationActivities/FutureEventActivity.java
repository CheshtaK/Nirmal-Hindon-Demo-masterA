package com.example.cheshta.nirmalhindan.navigationActivities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cheshta.nirmalhindan.R;

public class FutureEventActivity extends AppCompatActivity {
    TextView tvUpcomingEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);
        tvUpcomingEvent = findViewById(R.id.tvUpcomingEvent);
        tvUpcomingEvent.setPaintFlags(tvUpcomingEvent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
    public void onBackPressed() {
        Intent intent =new Intent(this,EventActivity.class);
        startActivity(intent);
        finish();
    }
}
