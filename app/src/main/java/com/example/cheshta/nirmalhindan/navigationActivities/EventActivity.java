package com.example.cheshta.nirmalhindan.navigationActivities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cheshta.nirmalhindan.HomeActivity;
import com.example.cheshta.nirmalhindan.R;
import com.example.cheshta.nirmalhindan.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity";
    private static final int ACTIVITY_NUM = 1;
    TextView tvEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        tvEvents = findViewById(R.id.tvEvents);

        tvEvents.setPaintFlags(tvEvents.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button b1,b2,bb;
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventActivity.this,CurrentEventActivity.class);
                startActivity(i);
                finish();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventActivity.this,FutureEventActivity.class);
                startActivity(i);
                finish();
            }
        });

        Log.d(TAG, "onCreate: Event");

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(EventActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    public void onBackPressed() {
        Intent intent =new Intent(this,AimActivity.class);
        startActivity(intent);
        finish();
    }
}
