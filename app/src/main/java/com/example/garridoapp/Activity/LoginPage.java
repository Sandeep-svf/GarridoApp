package com.example.garridoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garridoapp.MainActivity;
import com.example.garridoapp.Model.LoginData;
import com.example.garridoapp.Model.Login_Model;
import com.example.garridoapp.R;
import com.example.garridoapp.Response.Login_Response;
import com.example.garridoapp.Retrofit.Api_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    TextView logintxtt, forgot_password;
    EditText getUserEmail, getUserPassword;
    LinearLayout logintxt, loginLayout;
    SharedPreferences sharedPreferences;
    String userEmail;
    String userPassword;
    String device_token,authorization;
    private int UserID;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        logintxt = findViewById(R.id.logintxt);
        loginLayout = findViewById(R.id.loginLayout);
        getUserEmail = findViewById(R.id.getUserEmail);
        getUserPassword = findViewById(R.id.getUserPassword);
        forgot_password = findViewById(R.id.forgot_password);

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            loginLayout.setBackgroundDrawable(ContextCompat.getDrawable(LoginPage.this, R.color.main_background));
        } else {
            loginLayout.setBackground(ContextCompat.getDrawable(LoginPage.this, R.color.main_background));
        }

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userEmail = getUserEmail.getText().toString().trim();
                userPassword = getUserPassword.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (userEmail.equals("")){
                    getUserEmail.setError("This field can not be blank");
                    getUserEmail.requestFocus();
                    return;
                }/*else if(userEmail.matches(emailPattern)) {
                  //  Toast.makeText(getApplicationContext(), "Valid email address" , Toast.LENGTH_LONG).show();
                }else {
                    getUserEmail.setError("Please enter valid email Address");
                    getUserEmail.requestFocus();
                    return;
                }*/

               else if (userPassword.equals("")) {
                    getUserPassword.setError("This field can not be blank");
                    getUserPassword.requestFocus();
                    return;
                } else {
                    login_api();
                   /* Intent intent = new Intent(LoginPage.this, MainActivity.class );
                    startActivity(intent);
                    finish();*/
                }
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, ForgotFassword.class );
                startActivity(intent);
            }
        });
    }
    private void login_api()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Login_Model> call = Api_Client.getClient().Login(userEmail, userPassword,"manticoresoft12345");

        call.enqueue(new Callback<Login_Model>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<Login_Model> call, Response<Login_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {
                            Login_Model login_model = response.body();
                            LoginData data = login_model.getData();
                            Login_Response login_response = data.getUser();
                            UserID = login_response.getUserId();
                            authorization = login_response.getLoginToken();

                            Log.e("user_id", String.valueOf(UserID));
                            Log.e("user_id", String.valueOf(authorization));

                            SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("UserID", String.valueOf(UserID));
                            editor.putString("authorization", String.valueOf(authorization));
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Login_Model> call, Throwable t) {
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

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}