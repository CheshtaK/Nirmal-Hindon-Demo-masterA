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
import android.widget.Toast;

import com.example.cheshta.nirmalhindan.HomeActivity;
import com.example.cheshta.nirmalhindan.R;
import com.example.cheshta.nirmalhindan.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class FeedbackActivity extends AppCompatActivity {

    TextView tvFeedback;

    private static final String TAG = "FeedbackActivity";
    private static final int ACTIVITY_NUM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Button b1,b2;
        b1=findViewById(R.id.fb1);

        tvFeedback = findViewById(R.id.tvFeedback);
        tvFeedback.setPaintFlags(tvFeedback.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedbackActivity.this, "Your feedback request has been sent.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(FeedbackActivity.this,AimActivity.class);
                startActivity(i);
                finish();
            }
        });

        Log.d(TAG, "onCreate: Feedback");

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(FeedbackActivity.this, bottomNavigationViewEx);
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
