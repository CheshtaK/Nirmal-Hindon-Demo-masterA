package com.example.cheshta.nirmalhindan.galleryActivities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cheshta.nirmalhindan.R;
import com.example.cheshta.nirmalhindan.navigationActivities.AimActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends AppCompatActivity {
    Button publish;
    ImageView images;
    Uri uri;
    private StorageReference storageRef;
    File storeImage;
    FirebaseDatabase database ;
    DatabaseReference myRef ;


    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        database= FirebaseDatabase.getInstance();

            myRef = database.getReference("imagesUrl").child("admin");


        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        initialisation();
        storageRef = FirebaseStorage.getInstance().getReference();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),100);

        listeners();
    }
    public void listeners()
    {


        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadfile();
            }
        });
    }
    public void uploadfile()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        name="img_"+timeStamp+".jpg";
        StorageReference riversRef = storageRef.child("request/"+name);


        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.d("uri", "onSuccess: "+downloadUrl);
                        myRef.push().setValue(downloadUrl.toString());
                        Toast.makeText(Camera.this, "Request for file upload has been sent", Toast.LENGTH_SHORT).show();
                       Intent intent=new Intent(Camera.this,AimActivity.class);
                       startActivity(intent);
                       finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Snackbar.make(images,"Unsuccessfull upload", BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode==100 && resultCode==RESULT_OK)
      {
          try {
              uri=data.getData();
              if(data==null)
              {
                  finish();
              }
              Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
              images.setImageBitmap(bitmap);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
    }


    public void initialisation()
    {

        publish=findViewById(R.id.publish);
        images=findViewById(R.id.capturedImage);
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this, AimActivity.class);
        startActivity(intent);
        finish();
    }
}
