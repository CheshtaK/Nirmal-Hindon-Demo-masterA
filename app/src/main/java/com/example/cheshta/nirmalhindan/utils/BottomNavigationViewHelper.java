package com.example.cheshta.nirmalhindan.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.cheshta.nirmalhindan.R;
import com.example.cheshta.nirmalhindan.galleryActivities.Gallery;
import com.example.cheshta.nirmalhindan.navigationActivities.AimActivity;
import com.example.cheshta.nirmalhindan.navigationActivities.DonationActivity;
import com.example.cheshta.nirmalhindan.navigationActivities.EventActivity;
import com.example.cheshta.nirmalhindan.navigationActivities.FeedbackActivity;
import com.example.cheshta.nirmalhindan.navigationActivities.YojnaActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by chesh on 4/6/2018.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);

        bottomNavigationViewEx.setIconSize(30,30);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    /*case R.id.ic_home:
                        Intent intent0 = new Intent(context, HomeActivity.class);
                        context.startActivity(intent0);
                        break;*/
                    case R.id.ic_gal:
                        Intent intent1 = new Intent(context, Gallery.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_event:
                        Intent intent2 = new Intent(context, EventActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_yojana:
                        Intent intent3 = new Intent(context, YojnaActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_donation:
                        Intent intent4 = new Intent(context, DonationActivity.class);
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_feedback:
                        Intent intent5 = new Intent(context, FeedbackActivity.class);
                        context.startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }
}
