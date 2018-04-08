package com.example.cheshta.nirmalhindan.navigationActivities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheshta.nirmalhindan.HomeActivity;
import com.example.cheshta.nirmalhindan.LoginActivity;
import com.example.cheshta.nirmalhindan.MainActivity;
import com.example.cheshta.nirmalhindan.R;
import com.example.cheshta.nirmalhindan.galleryActivities.Camera;
import com.example.cheshta.nirmalhindan.utils.BottomNavigationViewHelper;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class AimActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener {

    //private static final String TAG = "AimActivity";
    FirebaseAuth mAuth;
    ImageView ivCamera;
    ImageView ivLogout;
    private static final int ACTIVITY_NUM = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "aim1.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    TextView tvAim;
    boolean bool = false;


    public void sendToLogin() {
        Intent intent=getIntent();
        if(!intent.getStringExtra("admin").equals("true"))
        {
            startActivity(new Intent(AimActivity.this, MainActivity.class));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aim);
        Log.d(TAG, "onCreate: Aim");
        mAuth = FirebaseAuth.getInstance();
        pdfView = (PDFView) findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);


        ivCamera = findViewById(R.id.ivCamera);
        ivLogout = findViewById(R.id.ivLogout);

        setupBottomNavigationView();

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(AimActivity.this, Camera.class));
                finish();
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(AimActivity.this, LoginActivity.class));
                finish();
            }
        });

        setupBottomNavigationView();
    }



    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    private void setupBottomNavigationView() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(AimActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        Handler handler = new Handler();

        Toast.makeText(this, "Click once more to exit", Toast.LENGTH_LONG).show();
        //   if(bool)
        // {
        System.exit(1);
        //}
       /* bool=true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
bool=false;
            }
        },2000);
    }*/
    }
}
