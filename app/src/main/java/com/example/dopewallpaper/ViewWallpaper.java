package com.example.dopewallpaper;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.share.Sharer;
import com.example.dopewallpaper.Common.Common;
import com.example.dopewallpaper.Database.DataSource.RecentRepository;
import com.example.dopewallpaper.Database.LocalDatabase.LocalDatabase;
import com.example.dopewallpaper.Database.LocalDatabase.RecentsDataSource;
import com.example.dopewallpaper.Database.Recents;
import com.example.dopewallpaper.Helper.SaveImageHelper;
import com.example.dopewallpaper.Model.WallpaperItem;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewWallpaper extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;




    FloatingActionButton floatingActionButton, fabDownload;
    ImageView imageView;
    RelativeLayout rootLayout;

    FloatingActionMenu mainFloating;
    com.github.clans.fab.FloatingActionButton fbShare;

    //Room Database
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;

    //Facebook
    CallbackManager callbackManager;
    ShareDialog shareDialog;






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case Common.PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    AlertDialog dialog = new SpotsDialog(ViewWallpaper.this);
                    dialog.show();
                    dialog.setMessage("Please wait...");

                    String fileName = UUID.randomUUID().toString()+".png";
                    Picasso.with(getApplicationContext())
//                    Picasso.get()
                            .load(Common.select_background.getImageLink())
                            .into(new SaveImageHelper(getBaseContext(),
                                    dialog, getApplicationContext().getContentResolver(),
                                    fileName, "DopeWallpaper Image"));
                }
                else
                    Toast.makeText(this, "You need to accept this permission request", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try{
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(rootLayout, "Wallpaper was set", Snackbar.LENGTH_SHORT).show();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

//        @Override
//        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private Target facebookConvert = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if(ShareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);

            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

//        @Override
//        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        //initialize Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        //before
        setContentView(R.layout.activity_view_wallpaper);



        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Init Facebook
        callbackManager = CallbackManager.Factory.create() ;
        shareDialog = new ShareDialog(this);


        //Init RoomDatabase
        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(this);
        recentRepository = RecentRepository.getInstance(RecentsDataSource.getInstance(database.recentsDAO()));


        //Init
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        collapsingToolbarLayout.setTitle(Common.CATEGORY_SELECTED);

        imageView = (ImageView)findViewById(R.id.imageThumb);
//        Picasso.get()
                Picasso.with(getApplicationContext())
                .load(Common.select_background.getImageLink())
                .into(imageView);

        mainFloating = (FloatingActionMenu)findViewById(R.id.menu);
        fbShare = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fb_share);
        fbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create callback
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(ViewWallpaper.this, "Share successful !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ViewWallpaper.this, "Share cancelled :/", Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(ViewWallpaper.this, "Bruh..., "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //Fetch photo from the link and convert to bitmap.
//                Picasso.get()
                Picasso.with(getApplicationContext())
                        .load(Common.select_background.getImageLink())
                        .into(facebookConvert);
            }
        });

        //add to Recents
         addToRecents();

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fabWallpaper);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext())
//                Picasso.get()
                        .load(Common.select_background.getImageLink())
                        .into(target);
            }
        });

        fabDownload = (FloatingActionButton)findViewById(R.id.fabDownload);
        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission
                //Request Runtime permission
                if(ActivityCompat.checkSelfPermission(ViewWallpaper.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISSION_REQUEST_CODE);
                }
                else{
                    AlertDialog dialog = new SpotsDialog(ViewWallpaper.this);
                    dialog.show();
                    dialog.setMessage("Please wait...");

                    String fileName = UUID.randomUUID().toString()+".png";
                    Picasso.with(getApplicationContext())
//                    Picasso.get()
                            .load(Common.select_background.getImageLink())
                            .into(new SaveImageHelper(getBaseContext(),
                                    dialog, getApplicationContext().getContentResolver(),
                                    fileName, "DopeWallpaper Image"));
                }
            }
        });

        //View Count
        increaseViewCount();

    }

    private void increaseViewCount() {
        FirebaseDatabase.getInstance().getReference(Common.STR_WALLPAPER)
                .child(Common.select_background_key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("ViewCount")){
                            WallpaperItem wallpaperItem = dataSnapshot.getValue(WallpaperItem.class);
                            long count = wallpaperItem.getViewCount();

                            //Update
                            Map<String, Object> update_view = new HashMap<>();
                            update_view.put("viewCount", count);
                            FirebaseDatabase.getInstance()
                                    .getReference(Common.STR_WALLPAPER)
                                    .child(Common.select_background_key)
                                    .updateChildren(update_view)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ViewWallpaper.this, "Cannot set default view count", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else //If view count isn't set (default 1)
                        {
                            //Update
                            Map<String, Object> update_view = new HashMap<>();
                            update_view.put("viewCount", Long.valueOf(1));
                            FirebaseDatabase.getInstance()
                                    .getReference(Common.STR_WALLPAPER)
                                    .child(Common.select_background_key)
                                    .updateChildren(update_view)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ViewWallpaper.this, "Cannot set default view count", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void addToRecents() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Recents recents = new Recents(
                        Common.select_background.getImageLink(),
                        Common.select_background.getCategoryId(),
                        String.valueOf(System.currentTimeMillis()),
                        Common.select_background_key);
                recentRepository.insertRecents(recents);
                e.onComplete();
            }


        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>(){

                    @Override
                    public void accept(Throwable throwable) throws Exception {
                            Log.e("Error", throwable.getMessage());
                    }

                 }, new Action(){

                    @Override
                    public void run() throws Exception {

                    }

        });

        compositeDisposable.add(disposable);

    }

    @Override
    protected void onDestroy() {
//        Picasso.get().cancelRequest(target);
        Picasso.with(this).cancelRequest(target);
        compositeDisposable.clear();
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish(); // Close activity when click back button
        return super.onOptionsItemSelected(item);
    }
}
