package com.example.cheshta.nirmalhindan.galleryActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cheshta.nirmalhindan.R;
import com.example.cheshta.nirmalhindan.navigationActivities.AimActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.widget.GridLayout.HORIZONTAL;

public class Gallery extends AppCompatActivity {

     StorageReference storageRef;
    RecyclerView.Adapter adapter;
    DatabaseReference myRef ;
    FirebaseDatabase database ;
    ArrayList<String> userurls =new ArrayList<>();
    ArrayList<POJO> adminurls =new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicgallery);
        database= FirebaseDatabase.getInstance();


        progressBar=findViewById(R.id.pgprogress);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView=findViewById(R.id.publicgallerylist);
        if (POJO.role.equals("user"))
        {
            myRef= database.getReference("imagesUrl").child("user");
        }
        else
        {
            myRef= database.getReference("imagesUrl").child("admin");
        }

        // use a linear layout manager
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        recyclerView.addItemDecoration(itemDecor);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(mLayoutManager);



        adapter = new CustomiseGallery(adminurls,Gallery.this,userurls,myRef,database);
        gallery();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);




            }
        },5000);



    }
    public void gallery()
    {
        String role= POJO.role;
        if(role.equals("admin"))
        {
            dlistadmin();
        }
        else
        {

            dlistuser();
        }
    }

    public void dlistadmin()
    {


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sp:  dataSnapshot.getChildren())
                {
                    Log.d("children", "child: "+dataSnapshot.getChildrenCount());
                    POJO obj=new POJO();
                    obj.key=sp.getKey();
                    obj.value=sp.getValue().toString();
                    adminurls.add(obj);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void dlistuser()
    {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sp:  dataSnapshot.getChildren())
                {
                    Log.d("children", "child: "+dataSnapshot.getChildrenCount());
                    userurls.add(sp.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this, AimActivity.class);
        startActivity(intent);
        finish();
    }
}
