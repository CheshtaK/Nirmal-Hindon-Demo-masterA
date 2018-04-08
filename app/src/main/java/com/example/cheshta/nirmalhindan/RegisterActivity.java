package com.example.cheshta.nirmalhindan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity { FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks; // to check whether verification has been completed or failed
    private PhoneAuthProvider.ForceResendingToken mResendToken; // to re verify the phone number
    EditText edNewMobNum,edCode,edName,edNewPassword,edConfirmNewPassword,edOccupation;
    Button btnCreateNew,btnVerify,btnSendOTP;
    TextView tvResendCode,tvLogin;
    LinearLayout registerLayout,verifyLayout,sendLayout,successLayout;
    public static final String TAG = "RegisterActivity";
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    String mobNumber = "+91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edNewMobNum = findViewById(R.id.edNewMobNum);
        edCode = findViewById(R.id.edCode);
        edName = findViewById(R.id.edName);
        edNewPassword= findViewById(R.id.edNewPassword);
        edConfirmNewPassword = findViewById(R.id.edConfirmNewPassword);
        edOccupation = findViewById(R.id.edOccupation);

        btnCreateNew = findViewById(R.id.btnCreateNew);
        btnVerify = findViewById(R.id.btnVerify);
        btnSendOTP = findViewById(R.id.btnSendOTP);

        tvResendCode = findViewById(R.id.tvResendCode);
        tvLogin = findViewById(R.id.tvLogin);

        registerLayout = findViewById(R.id.registerLayout);
        verifyLayout = findViewById(R.id.verifyLayout);
        sendLayout = findViewById(R.id.sendLayout);
        successLayout = findViewById(R.id.successLayout);

        verifyLayout.setVisibility(View.GONE);
        disableView(registerLayout);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please wait this may take a while.");

        //mobNumber = mobNumber.concat(edNewMobNum.getText().toString());

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //Toast.makeText(RegisterActivity.this, "Verification Completed!", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegisterActivity.this, "Verification Failed!", Toast.LENGTH_SHORT).show();
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Log.d(TAG, "onVerificationFailed: " + "Invalid Credential" + e.getLocalizedMessage());
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.d(TAG, "onVerificationFailed: " + "SMS Quota exceeded");
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                Toast.makeText(RegisterActivity.this, "Verification code has been sent to your Number!", Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;
                sendLayout.setVisibility(View.GONE);
                verifyLayout.setVisibility(View.VISIBLE);
            }
        };

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        edNewMobNum.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        RegisterActivity.this,               // Activity (for callback binding)
                        mCallbacks,         // OnVerificationStateChangedCallbacks
                        mResendToken);
                Toast.makeText(RegisterActivity.this, "Verification code has been sent to your Number!", Toast.LENGTH_SHORT).show();
            }

        });

        btnCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edName.getText().toString().isEmpty() && !edNewPassword.getText().toString().isEmpty() && !edConfirmNewPassword.getText().toString().isEmpty() && !edOccupation.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(edNewMobNum.getText().toString().concat("@nirmal.com"),edNewPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                        HashMap<String, String> userMap = new HashMap<>();
                                        userMap.put("Name", edName.getText().toString());
                                        userMap.put("Occupation",edOccupation.getText().toString());

                                        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(RegisterActivity.this, "Registration Successfull!!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                                                }
                                                else{
                                                    Toast.makeText(RegisterActivity.this, "Cannot create your account. Please check the form and try again", Toast.LENGTH_LONG).show();
                                                    Log.e(TAG, "onComplete: " + task.getException());
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(RegisterActivity.this,task.getException().getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else if(!edNewPassword.getText().toString().equals(edConfirmNewPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password Don't Match!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "All fields are necessary!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edNewMobNum.getText().toString().isEmpty()){
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            edNewMobNum.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            RegisterActivity.this,
                            mCallbacks
                    );
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Enter the Mobile Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edCode.getText().toString().isEmpty()){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, edCode.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Enter Verification Code!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            verifyLayout.setVisibility(View.GONE);
                            successLayout.setVisibility(View.VISIBLE);
                            enableView(registerLayout);

                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(RegisterActivity.this, "Invalid Code!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void disableView(LinearLayout layout){
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(false);
        }
    }

    public void enableView(LinearLayout layout){
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(true);
        }
    }
}
