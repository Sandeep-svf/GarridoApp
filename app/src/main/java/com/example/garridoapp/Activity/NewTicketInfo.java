package com.example.garridoapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.example.garridoapp.Adapter.DeviceTicketsAdapter;
import com.example.garridoapp.MainActivity;
import com.example.garridoapp.Model.CreateTicket_Model;
import com.example.garridoapp.Model.LoginData;
import com.example.garridoapp.Model.Login_Model;
import com.example.garridoapp.Model.TicketList_Model;
import com.example.garridoapp.Model.UpdateTicket_Model;
import com.example.garridoapp.R;
import com.example.garridoapp.Response.Login_Response;
import com.example.garridoapp.Retrofit.Api_Client;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTicketInfo extends AppCompatActivity {
    TextView  text_pickerset, New_ticket_DistributeInfo, New_ticket_Distribute_Name, New_ticket_EmailId, contactInfo,New_ticket_state,New_ticket_city,New_ticket_techName, New_ticket_clientName;
    ImageView edittxt_datepiker, back_arrow_pp;
    DatePickerDialog datePickerDialog;
    LinearLayout new_ticket_info;
    Spinner spinner;
    String userEmailId="", userTechName="", userDate="", userClientName="", userCity="", userState="",
            userContactInfo="", userDistributionName="", userDistributionInfo="";
    String status="";
    String userId="", authorization="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket_info);
        new_ticket_info = findViewById(R.id.new_ticket_info);
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

        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        userId = sharedPreferences.getString("UserID", "");
        authorization = sharedPreferences.getString("authorization", "");

/*
        final List<String> groupstatus = new ArrayList<>();
        groupstatus.add("MACHINE INFO");
        groupstatus.add("DISCRIPTION");
        groupstatus.add("PRODUCT INFO");
        groupstatus.add("PENDING ACTIONS");
        groupstatus.add("ADD MEDIA");
        groupstatus.add("FINALIZE/SUBMIT TICKET");
        NewTicketInfo.spinnerAdapter group_status_adapter = new spinnerAdapter(NewTicketInfo.this, R.layout.custom_spinner);
        group_status_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_edit);
        group_status_adapter.addAll(groupstatus);
        group_status_adapter.add("");
        spinner.setAdapter(group_status_adapter);

        spinner.setSelection(group_status_adapter.getCount());


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int selectedItemText = spinner.getSelectedItemPosition() + 1;
                status = spinner.getSelectedItem().toString();
                if (status.equalsIgnoreCase("MACHINE INFO")) {
                    status = "";
                    //MyServiceList_Api();
                    Intent intent = new Intent(NewTicketInfo.this,MachineInfo.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("DISCRIPTION")) {
                    status = "0";
                    Intent intent = new Intent(NewTicketInfo.this,DESCRIPTION.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("PRODUCT INFO")) {
                    status = "1";
                    Intent intent = new Intent(NewTicketInfo.this,ProductionInfo.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("PENDING ACTIONS")) {
                    status = "2";
                    Intent intent = new Intent(NewTicketInfo.this,PendingActions.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("ADD MEDIA")) {
                    status = "3";
                    Intent intent = new Intent(NewTicketInfo.this,AddMediaActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("FINALIZE/SUBMIT TICKET")) {
                    status = "3";
                    Intent intent = new Intent(NewTicketInfo.this, Reviewfinalize.class);
                    startActivity(intent);
                    // finish();
                }
                if (spinner.getSelectedView() != null) {
                    ((TextView) spinner.getSelectedView()).setTextColor(0xFFFFFFFF);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        back_arrow_pp.setOnClickListener(view -> finish());

        new_ticket_info.setOnClickListener(new View.OnClickListener() {
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
                  //  Toast.makeText(NewTicketInfo.this,"valid email address",Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(NewTicketInfo.this,"Invalid email address", Toast.LENGTH_SHORT).show();
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
                   //CreateTicketInfo_Api();
                    Intent intent = new Intent(NewTicketInfo.this, MachineInfo.class);
                    startActivity(intent);
                    finish();
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
                datePickerDialog = new DatePickerDialog(NewTicketInfo.this,R.style.DialogTheme,
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

    private void CreateTicketInfo_Api()
    {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        retrofit2.Call<CreateTicket_Model> call = Api_Client.getClient().getCreateTicket(userId,authorization,userTechName,userClientName,userCity,userState,
                userDate,userContactInfo,userEmailId,userDistributionName,userDistributionInfo);

        call.enqueue(new Callback<CreateTicket_Model>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(retrofit2.Call<CreateTicket_Model> call, retrofit2.Response<CreateTicket_Model> response) {
                pd.dismiss();
                try {
                    if (response.isSuccessful()) {
                        CreateTicket_Model result = response.body();
                        String message = result.getMessage();
                        String success = result.getSuccess();

                        if (success.equals("true") || success.equals("True")) {

                            Intent intent = new Intent(NewTicketInfo.this, MachineInfo.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(NewTicketInfo.this, message, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(NewTicketInfo.this, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(NewTicketInfo.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(NewTicketInfo.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(NewTicketInfo.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(NewTicketInfo.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(NewTicketInfo.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(NewTicketInfo.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(NewTicketInfo.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(NewTicketInfo.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(NewTicketInfo.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+response);

                            Toast.makeText(NewTicketInfo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CreateTicket_Model> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(NewTicketInfo.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(NewTicketInfo.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

/*
    public class spinnerAdapter extends ArrayAdapter<String> {
        private spinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }*/
}