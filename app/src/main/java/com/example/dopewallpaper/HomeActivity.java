package com.example.dopewallpaper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dopewallpaper.Adapter.MyFragmentAdapter;
import com.example.dopewallpaper.Common.Common;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    TabLayout tabLayout;

    DrawerLayout drawer;

    NavigationView navigationView;
    MenuItem menuItem;

    BottomNavigationView menu_bottom;
    URL url;
    Bitmap image;
    ImageView user;


    private FirebaseAuth mAuth;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case Common.PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast. LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "You need to accept this permission request to download images", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    //Override method onActivityResult


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Common.SIGN_IN_REQUEST_CODE){
            if(resultCode == RESULT_OK){

                Snackbar.make(drawer, new StringBuilder("Welcome ")
                        .append(FirebaseAuth.getInstance().getCurrentUser().getEmail()
                                .toString()), Snackbar.LENGTH_LONG).show();




                //Request Runtime permission
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISSION_REQUEST_CODE);
                }

                viewPager = (ViewPager)findViewById(R.id.viewPager);
                MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), this);
                viewPager.setAdapter(adapter);

                tabLayout = (TabLayout)findViewById(R.id.tabLayout);
                tabLayout.setupWithViewPager(viewPager);

                loadUserInformation();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DopeWallpaper");
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);


        menu_bottom = (BottomNavigationView)findViewById(R.id.navigation);
        menu_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_upload){
                     startActivity(new Intent(HomeActivity.this, UploadWallpaper.class));
                }
                return false;
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Request Runtime permission
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISSION_REQUEST_CODE);
        }

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        loadUserInformation();

    }

    private void loadUserInformation() {
        if(mAuth.getInstance().getCurrentUser() != null){
            View headerLayout = navigationView.getHeaderView(0);
            user = (ImageView)headerLayout.findViewById(R.id.userImage);
            if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null){
                image = getBitmapFromURL(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                user.setImageBitmap(image);
            }
            TextView txt_usr = (TextView)headerLayout.findViewById(R.id.username);
            txt_usr.setText(mAuth.getInstance().getCurrentUser().getDisplayName());
            TextView txt_email = (TextView)headerLayout.findViewById(R.id.txt_email);
            txt_email.setText(mAuth.getInstance().getCurrentUser().getEmail());
        }
    }


    public static Bitmap getBitmapFromURL(String src) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.signout) {

            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                mAuth.signOut();
                Intent i = new Intent(HomeActivity.this, SignIn.class);
                startActivity(i);
                finish();

            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
