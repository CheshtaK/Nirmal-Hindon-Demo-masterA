package com.example.cheshta.nirmalhindan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheshta.nirmalhindan.galleryActivities.POJO;
import com.example.cheshta.nirmalhindan.navigationActivities.AimActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin,btnCreate,btnMusicPlay,btnMusicPause,btnMusicStop,btnShowPassword,btnHidePassword;
    EditText edMobNum,edPassword;
    TextView tvForgotPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    String mobNumberLogin;
    Boolean isPlaying = false;
    MediaPlayer getSoundMP;

    @Override
    protected void onPause() {
        super.onPause();
        getSoundMP.stop();
        getSoundMP = MediaPlayer.create(LoginActivity.this,R.raw.nirmal_music);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSoundMP.stop();
        getSoundMP = MediaPlayer.create(LoginActivity.this,R.raw.nirmal_music);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnCreate = findViewById(R.id.btnCreate);
        btnLogin = findViewById(R.id.btnLogin);
        btnMusicPlay = findViewById(R.id.btnMusicPlay);
        btnMusicPause = findViewById(R.id.btnMusicPause);
        btnMusicStop = findViewById(R.id.btnMusicStop);
        edMobNum =  findViewById(R.id.edMobNum);
        edPassword = findViewById(R.id.edPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        //mobNumberLogin = "+91".concat(edMobNum.getText().toString());

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait while we check your credentials.");

        getSoundMP = MediaPlayer.create(LoginActivity.this,R.raw.nirmal_music);

            btnMusicPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        getSoundMP.start();
                }
            });
            btnMusicPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSoundMP.pause();
                }
            });
            btnMusicStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSoundMP.stop();
                    getSoundMP = MediaPlayer.create(LoginActivity.this,R.raw.nirmal_music);
                }
            });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!edMobNum.getText().toString().isEmpty() && !edPassword.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(edMobNum.getText().toString().concat("@nirmal.com"),edPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                        if (edPassword.getText().toString().equals("akhildixit12"))
                                        {
                                            POJO.role="admin";
                                        }
                                        Intent intent = new Intent(LoginActivity.this,AimActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Enter Email and Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        btnCreate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent=new Intent(LoginActivity.this,AimActivity.class);
                intent.putExtra("adminP","true");
                POJO.role="admin";
                startActivity(intent);
                return false;
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Work In Progress!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
