package com.example.cheshta.nirmalhindan;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView tvNH;
    ImageView ivNH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvNH = findViewById(R.id.tvNH);
        ivNH = findViewById(R.id.ivNH);

        tvNH.setPaintFlags(tvNH.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ivNH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent languageIntent = new Intent(MainActivity.this, LanguageActivity.class);
                startActivity(languageIntent);
                finish();
            }
        });
    }
}
