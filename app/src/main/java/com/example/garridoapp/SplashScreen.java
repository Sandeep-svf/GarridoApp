package com.example.garridoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.garridoapp.Activity.LoginPage;
import com.example.garridoapp.Class.GPSTracker;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    ImageView splashscreen;
    RelativeLayout rlBaseLayout;
    FragmentActivity context;
    String name = "";
    private Handler handler;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private String strUserId = "", authorization="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashscreen = findViewById(R.id.splashscreen);
        rlBaseLayout = findViewById(R.id.rlBaseLayout);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Glide.with(SplashScreen.this).load(R.drawable.logo).into(splashscreen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              /*  SharedPreferences sharedPreferences = SplashScreen.this.getSharedPreferences("PREFERENCE_NAME", MODE_PRIVATE);
                strUserId = sharedPreferences.getString("UserID", "");

                if (name.equals("")) {
                    Intent intent = new Intent(SplashScreen.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }*/
                internetStatusCheck();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void internetStatusCheck() {
        ConnectivityManager connectivityManager = (ConnectivityManager) SplashScreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            if (connectivityManager.getActiveNetworkInfo().isConnected()) {
                askPermissions();
            } else {
                showNetworkSnackBar();
            }
        } else {
            showNetworkSnackBar();
        }
    }

    private void showNetworkSnackBar() {
        Snackbar snackbar = Snackbar.make(rlBaseLayout, getResources().getString(R.string.please_connect_internet_connection), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(R.string.retry), view -> internetStatusCheck());
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    private final ArrayList<String> requirePermissions = new ArrayList<>();
    public final ArrayList<String> permissionsToRequest = new ArrayList<>();
    private final ArrayList<String> permissionsRejected = new ArrayList<>();

    private void askPermissions() {
        requirePermissions.add(Manifest.permission.CAMERA);
        requirePermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requirePermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        requirePermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        for (String permission : requirePermissions) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[0]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            } else {
                launchLocation();
            }
        } else {
            launchLocation();
        }
    }

    private void launchLocation() {
       /* GPSTracker gps = new GPSTracker(context);
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        } else {*/
            SharedPreferences sharedPreferences = SplashScreen.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
            strUserId = sharedPreferences.getString("UserID", "");
            //authorization = sharedPreferences.getString("authorization", "");

            if (strUserId.equals("")) {
                Intent intent = new Intent(SplashScreen.this, LoginPage.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
       // }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsRejected.clear();
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            if (permissionsToRequest != null) {
                for (String permission : permissionsToRequest) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                            permissionsRejected.add(permission);
                        }
                    }
                }
            }

            if (permissionsRejected.size() > 0) {
                String message = getResources().getString(R.string.these_permissions_are_mandatory_for_this_application_please_allow_access);
                alert(message);
            } else {
                launchLocation();
            }
        }
    }

    private void alert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setPositiveButton(getResources().getString(R.string.ok), (DialogInterface.OnClickListener) this);
        alert.setNegativeButton(getResources().getString(R.string.cancel), (DialogInterface.OnClickListener) this);
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(context, permissionsRejected.toArray(new String[0]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        } else {
            dialog.dismiss();
            finish();
        }
    }

}