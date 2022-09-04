package com.example.garridoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garridoapp.Activity.LoginPage;
import com.example.garridoapp.Activity.MachineInfo;
import com.example.garridoapp.Activity.NewTicketInfo;
import com.example.garridoapp.Fragments.ExitingTicketsFragment;
import com.example.garridoapp.Fragments.HomeFragment;
import com.example.garridoapp.Fragments.SettingFragment;
import com.example.garridoapp.Model.CreateTicket_Model;
import com.example.garridoapp.Model.Logout_Model;
import com.example.garridoapp.Retrofit.Api_Client;
import com.jiangjiesheng.slidingmenu.SlidingMenu;
import com.jiangjiesheng.slidingmenu.app.SlidingFragmentActivity;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends SlidingFragmentActivity {
    TextView headerName;
    FrameLayout home_body_framelayout;
    ImageView setting_img, menu;
    RelativeLayout relative_logout, relative_dashboard, relative_newticket, relative_setting,relative_exiting_ticket, relative_help,relative_machineInfo;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private static int navItemIndex= 0;
    private boolean doubleBackToExitPressedOnce = false;
    private static String TAG_DASH_BOARD = "Garrido";
    private static String CURRENT_TAG= TAG_DASH_BOARD;
    private String TAG_DASHBOARD= "garrido";
     TextView appTitleName;
     AlertDialog dialogs;
    private String userId="",authorization;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         setBehindContentView(R.layout.activity_menu);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new HomeFragment()).commit();

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        userId = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        setting_img = (ImageView) findViewById(R.id.setting_img);
        headerName = (TextView) findViewById(R.id.headerName);
        menu = (ImageView) findViewById(R.id.menu);
        relative_dashboard = (RelativeLayout) findViewById(R.id.relative_dashboard);
        relative_newticket = (RelativeLayout) findViewById(R.id.relative_newticket);
        relative_exiting_ticket = (RelativeLayout) findViewById(R.id.relative_exiting_ticket);
        relative_help = (RelativeLayout) findViewById(R.id.relative_help);
        relative_setting = (RelativeLayout) findViewById(R.id.relative_setting);
        relative_logout = (RelativeLayout) findViewById(R.id.relative_logout);
        home_body_framelayout = (FrameLayout) findViewById(R.id.home_body_framelayout);

        setting_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingFragment settingFragment = new SettingFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, settingFragment,"");
                fragmentTransaction.commit();
                headerName.setText("Settings");
            }
        });

        try {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

            final Display display = getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();
            final int slidingmenuWidth = (int) (screenWidth - (screenWidth / 3.7) + 23);
            final int offset = Math.max(0, screenWidth - slidingmenuWidth);
            getSlidingMenu().setBehindOffset(offset);
            getSlidingMenu().toggle();
            getSlidingMenu().isVerticalFadingEdgeEnabled();
            getSlidingMenu().isHorizontalFadingEdgeEnabled();
            getSlidingMenu().setMode(SlidingMenu.LEFT);
            getSlidingMenu().setFadeEnabled(true);
            getSlidingMenu().setFadeDegree(0.8f);
            getSlidingMenu().setFadingEdgeLength(13);
            getSlidingMenu().setEnabled(false);
            //int width = display.getWidth();
            //int height = display.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("slidingexception", e.toString());
        }

        menu.setOnClickListener(v -> {
            try {
                showMenu();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        setupLeftNavigationView();


    }

    private void setupLeftNavigationView() {
        relative_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASHBOARD;
               // appTitleName.setText(getString(R.string.garrido));
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, homeFragment,CURRENT_TAG);
                fragmentTransaction.commit();
                getSlidingMenu().toggle();
                headerName.setText("Home");
                menu.setImageResource(R.drawable.menu);
            }
        });
        relative_newticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 1;
              /*  CURRENT_TAG = TAG_DASHBOARD;
                // appTitleName.setText(getString(R.string.garrido));
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, homeFragment,CURRENT_TAG);
                fragmentTransaction.commit();
                getSlidingMenu().toggle();*/

                Intent intent= new Intent(MainActivity.this, NewTicketInfo.class);
                startActivity(intent);
            }
        });

        relative_exiting_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_DASHBOARD;
                // appTitleName.setText(getString(R.string.garrido));
                ExitingTicketsFragment exitingTicketsFragment = new ExitingTicketsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, exitingTicketsFragment,CURRENT_TAG);
                fragmentTransaction.commit();
                getSlidingMenu().toggle();
                headerName.setText("Exiting Tickets");
                menu.setImageResource(R.drawable.back_arroe);

                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
        relative_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_DASHBOARD;
                // appTitleName.setText(getString(R.string.garrido));
                SettingFragment settingFragment = new SettingFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, settingFragment,CURRENT_TAG);
                fragmentTransaction.commit();
                getSlidingMenu().toggle();
                headerName.setText("Settings");

            }
        });
        relative_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_DASHBOARD;
                // appTitleName.setText(getString(R.string.garrido));
                /*SettingFragment settingFragment = new SettingFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, settingFragment,CURRENT_TAG);
                fragmentTransaction.commit();
                getSlidingMenu().toggle();*/

            }
        });

        relative_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 1;
                Logout_AlertDialog();
                relative_logout.setEnabled(false);
            }
        });

    }

    public void Logout_AlertDialog() {
        final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_alert_dialog, null);
        final TextView tvMessage = alertLayout.findViewById(R.id.textViewMessage);
        final TextView btnYes = alertLayout.findViewById(R.id.btnd_delete);
        final TextView btnNo = alertLayout.findViewById(R.id.btn_cancel);

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(MainActivity.this.getString(R.string.confirm_logout));
        tvMessage.setText(MainActivity.this.getString(R.string.are_you_sure_to_logout));
        alert.setView(alertLayout);
        alert.setCancelable(false);

        btnYes.setOnClickListener(v -> {
            dialogs.dismiss();
           relative_logout.setEnabled(true);

           Logout_Api();
         /*  SharedPreferences preferences = MainActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
            preferences.edit().remove("UserID").apply();
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
            finish();*/
        });

        btnNo.setOnClickListener(v -> {
            dialogs.dismiss();
            relative_logout.setEnabled(true);
        });

        dialogs = alert.create();
        dialogs.show();
    }
    private void Logout_Api() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Logout_Model> call = Api_Client.getClient().getLogout(userId,authorization);

        call.enqueue(new Callback<Logout_Model>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<Logout_Model> call, Response<Logout_Model> response) {
                pd.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Logout_Model result = response.body();
                        String message = result.getMessage();
                        String success = result.getSuccess();

                        if (success.equals("true") || success.equals("True")) {

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("UserID", "");
                            editor.putString("authorization", "");

                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("finish", true);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+response);

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Logout_Model> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    public void onBackPressed() {

        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASH_BOARD;
                loadHomeFragment();
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getString(R.string.please_back_to_exit), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;

                    }
                }, 2000);
                return;
            }
        }
    }

    private void loadHomeFragment() {
        navItemIndex = 0;
        CURRENT_TAG = TAG_DASHBOARD;
        HomeFragment homeFragment = new HomeFragment();
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_contaner, homeFragment, CURRENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        UiModeManager uiModeManager=(UiModeManager) getSystemService(UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }
}