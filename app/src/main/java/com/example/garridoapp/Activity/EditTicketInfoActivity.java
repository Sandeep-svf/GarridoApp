package com.example.garridoapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garridoapp.Model.CreateTicket_Model;
import com.example.garridoapp.Model.TicketDetails_Model;
import com.example.garridoapp.Model.UpdateTicket_Model;
import com.example.garridoapp.R;
import com.example.garridoapp.Response.TicketsDetails_Response;
import com.example.garridoapp.Retrofit.Api_Client;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTicketInfoActivity extends AppCompatActivity {

    TextView text_pickerset, New_ticket_DistributeInfo, New_ticket_Distribute_Name, New_ticket_EmailId, contactInfo,New_ticket_state,New_ticket_city,New_ticket_techName, New_ticket_clientName;
    ImageView edittxt_datepiker, back_arrow_pp;
    DatePickerDialog datePickerDialog;
    LinearLayout update_ticket_info;
    Spinner spinner;
    String userEmailId="", userTechName="", userDate="", userClientName="", userCity="", userState="",userContactInfo="",userDistributionName="",userDistributionInfo="";
    String status="";
    String userId="", authorization="",strTicketId="";
    TicketsDetails_Response ticketsDetails_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket_info);

        update_ticket_info = findViewById(R.id.update_ticket_info);
        edittxt_datepiker = findViewById(R.id.edittxt_datepiker);
        text_pickerset = findViewById(R.id.text_pickerset);
        contactInfo = findViewById(R.id.contactInfo);
        New_ticket_city = findViewById(R.id.New_ticket_city);
        New_ticket_state = findViewById(R.id.New_ticket_state);
        New_ticket_EmailId = findViewById(R.id.New_ticket_EmailId);
        New_ticket_techName = findViewById(R.id.New_ticket_techName);
        New_ticket_clientName = findViewById(R.id.New_ticket_clientName);
        New_ticket_DistributeInfo = findViewById(R.id.New_ticket_DistributeInfo);
        New_ticket_Distribute_Name = findViewById(R.id.New_ticket_Distribute_Name);
        //spinner = findViewById(R.id.spinner);
        back_arrow_pp = findViewById(R.id.back_arrow_pp);
        Log.e("checkTicketId","STARTED>>>>>>>>>>>>>>>>>>");


            strTicketId = getIntent().getStringExtra("sjfkdk");
            Log.e("checkTicketId",strTicketId+"ok");

        Intent intentReceived = getIntent();
        Bundle b = intentReceived.getExtras();
        String index;
        if(b != null){
             index = b.getString("index");
        }else{
            index = "";
        }



       /* SharedPreferences sharedPreferences2 = getSharedPreferences("TEMP", MODE_PRIVATE);
        strTicketId = sharedPreferences2.getString("strTicketId", "");
        Log.e("check_bundel",strTicketId+"ok");*/



        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        userId = sharedPreferences.getString("UserID", "");
        authorization = sharedPreferences.getString("authorization", "");

        back_arrow_pp.setOnClickListener(view -> finish());

        TicketDetails_Api();

        update_ticket_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                userTechName = New_ticket_techName.getText().toString();
                userDate = text_pickerset.getText().toString();
                userClientName = New_ticket_clientName.getText().toString();
                userCity = New_ticket_city.getText().toString();
                userState = New_ticket_state.getText().toString();
                userContactInfo = contactInfo.getText().toString();
                userEmailId = New_ticket_EmailId.getText().toString();
                userDistributionName = New_ticket_Distribute_Name.getText().toString();
                userDistributionInfo = New_ticket_DistributeInfo.getText().toString();
                if (userTechName.equals("")) {
                    New_ticket_techName.setError("This field can not be blank");
                    New_ticket_techName.requestFocus();
                    return;
                }else  if(userDate.equals("")){
                    text_pickerset.setError("This field can not be blank");
                    text_pickerset.requestFocus();
                    return;
                }else  if(userClientName.equals("")){
                    New_ticket_clientName.setError("This field can not be blank");
                    New_ticket_clientName.requestFocus();
                    return;
                }else  if(userCity.equals("")){
                    New_ticket_city.setError("This field can not be blank");
                    New_ticket_city.requestFocus();
                    return;
                }else  if(userState.equals("")){
                    New_ticket_state.setError("This field can not be blank");
                    New_ticket_state.requestFocus();
                    return;
                }else  if(userContactInfo.equals("")){
                    contactInfo.setError("This field can not be blank");
                    contactInfo.requestFocus();
                    return;
                }else  if(userEmailId.equals("")){
                    New_ticket_EmailId.setError("This field can not be blank");
                    New_ticket_EmailId.requestFocus();
                    return;
                }
                if (userEmailId.matches(emailPattern)) {
                    //  Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    New_ticket_EmailId.setError("Please enter valid email Address");
                    New_ticket_EmailId.requestFocus();
                    return;
                } if(userDistributionName.equals("")){
                    New_ticket_Distribute_Name.setError("This field can not be blank");
                    New_ticket_Distribute_Name.requestFocus();
                    return;
                }else  if(userDistributionInfo.equals("")){
                    New_ticket_DistributeInfo.setError("This field can not be blank");
                    New_ticket_DistributeInfo.requestFocus();
                    return;
                }else {
                     TicketUpdateDetails_Api();

                   // Intent intent = new Intent(EditTicketInfoActivity.this, DESCRIPTION.class);
                    //startActivity(intent);
                    //finish();
                }
            }
        });

        edittxt_datepiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(EditTicketInfoActivity.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                text_pickerset.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

            }
        });
    }

    private void TicketUpdateDetails_Api()
    {
        Log.e("checkk","API CALLING>>>>>>>>>>>>>>>>>>>>>>>");

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<UpdateTicket_Model> call = Api_Client.getClient().getUpdateTicket(userId,authorization,userTechName,userClientName,userCity,userState,
                userDate,userContactInfo,userEmailId,userDistributionName,userDistributionInfo,strTicketId);

        call.enqueue(new Callback<UpdateTicket_Model>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<UpdateTicket_Model> call, Response<UpdateTicket_Model> response) {
                pd.dismiss();
                try {
                    if (response.isSuccessful()) {

                        Log.e("checkk","response is successful");

                        UpdateTicket_Model result = response.body();
                        String message = result.getMessage();
                        String success = result.getSuccess();

                        if (success.equalsIgnoreCase("true")) {
                            Log.e("checkk","working fine....");

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
            public void onFailure(Call<UpdateTicket_Model> call, Throwable t) {
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

    private void TicketDetails_Api()
    {

        Log.e("check","API CALLING>>>>>>>>>>>>>>>>>>>>>>>");
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<TicketDetails_Model> call = Api_Client.getClient().getTicketDetail(userId,authorization,strTicketId);

        call.enqueue(new Callback<TicketDetails_Model>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<TicketDetails_Model> call, Response<TicketDetails_Model> response) {
                pd.dismiss();
                try {
                    if (response.isSuccessful()) {

                        Log.e("check","response is successful");

                        TicketDetails_Model result = response.body();
                        String message = result.getMessage();
                        String success = result.getSuccess();

                        if (success.equalsIgnoreCase("true")) {

                            Log.e("check","working fine....");

                            ticketsDetails_response = result.getData().getTicket();
                            New_ticket_techName.setText(ticketsDetails_response.getTechName());
                            text_pickerset.setText(ticketsDetails_response.getDate());
                            New_ticket_clientName.setText(ticketsDetails_response.getClientName());
                            New_ticket_city.setText(ticketsDetails_response.getCity());
                            New_ticket_state.setText(ticketsDetails_response.getState());
                            contactInfo.setText(ticketsDetails_response.getContactInfo());
                            New_ticket_EmailId.setText(ticketsDetails_response.getEmailId());
                            New_ticket_Distribute_Name.setText(ticketsDetails_response.getDistributorName());
                            New_ticket_DistributeInfo.setText(ticketsDetails_response.getDistributorInfo());

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
            public void onFailure(Call<TicketDetails_Model> call, Throwable t) {
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
    protected void onResume() {
        super.onResume();
        TicketDetails_Api();
    }
}