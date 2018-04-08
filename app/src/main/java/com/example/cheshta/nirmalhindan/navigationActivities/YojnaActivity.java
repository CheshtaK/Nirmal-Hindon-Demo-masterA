package com.example.cheshta.nirmalhindan.navigationActivities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cheshta.nirmalhindan.R;
import com.example.cheshta.nirmalhindan.adapter.YojnaAdapter;
import com.example.cheshta.nirmalhindan.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class YojnaActivity extends AppCompatActivity {

    private static final String TAG = "YojnaActivity";
    private static final int ACTIVITY_NUM = 2;
    ArrayList<YojnaAdapter> YognadetailsList=YojnaAdapter.genRandCourses(5);
    TextView tvYojana;
    ListView lvYognadetailsDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yojana);

        tvYojana = findViewById(R.id.tvYojana);
        tvYojana.setPaintFlags(tvYojana.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Log.d(TAG, "onCreate: Yojana");
        lvYognadetailsDetails = (ListView) findViewById(R.id.l3);
        YojnaActivity.YognadetailsAdapter  YognadetailsAdapter = new YojnaActivity.YognadetailsAdapter();
        lvYognadetailsDetails.setAdapter(YognadetailsAdapter);

        setupBottomNavigationView();
    }
    class YognadetailsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return YognadetailsList.size();
        }

        @Override
        public YojnaAdapter getItem(int position) {
            return YognadetailsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater li = getLayoutInflater();
            convertView = li.inflate(R.layout.yognadetailsdetails, parent, false);


            TextView tvName = (TextView) convertView.findViewById(R.id.tvYognadetailsname);
            TextView tvdesc = (TextView) convertView.findViewById(R.id.tvYognadetailsdesc);


            YojnaAdapter thisYognadetails = getItem(position);

            tvName.setText(thisYognadetails.getName());
            tvdesc.setText(thisYognadetails.getDescription());

            return convertView;
        }

    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(YojnaActivity.this, bottomNavigationViewEx);
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
