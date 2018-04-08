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

public class DonationActivity extends AppCompatActivity {

    private static final String TAG = "DonationActivity";
    private static final int ACTIVITY_NUM = 3;
    TextView tvDonationHead;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DonationActivity.this, "Your response has been submitted\n      THANK YOU!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DonationActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        tvDonationHead = findViewById(R.id.tvDonationHead);
        tvDonationHead.setPaintFlags(tvDonationHead.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        /*Button b;
        b=findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DonationActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });*/
        Log.d(TAG, "onCreate: Donation");

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(DonationActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
