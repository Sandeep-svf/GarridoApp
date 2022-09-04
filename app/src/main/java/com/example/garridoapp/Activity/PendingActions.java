package com.example.garridoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.garridoapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PendingActions extends AppCompatActivity {
    TextView  text_pickerset ;
    LinearLayout pending_action;
    ImageView edittxt_datepiker, back_arrow_pp;
    DatePickerDialog datePickerDialog;
    EditText pending_comments, pending_actiontxt;
    String userPendingAction,userDatepickertxt,userpendingComments;
    Spinner spinner;
    String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_actions);

        pending_action = findViewById(R.id.pending_action);
        edittxt_datepiker = findViewById(R.id.edittxt_datepiker);
        text_pickerset = findViewById(R.id.text_pickerset);
        pending_actiontxt = findViewById(R.id.pending_actiontxt);
        pending_comments = findViewById(R.id.pending_comments);
        spinner = findViewById(R.id.spinner);

        back_arrow_pp = findViewById(R.id.back_arrow_pp);
        back_arrow_pp.setOnClickListener(v -> finish());

/*
        back_arrow_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PendingActions.this, ProductionInfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //progressDialog.dismiss();
            }
        });
*/
        final List<String> groupstatus = new ArrayList<>();
        groupstatus.add("EDIT TICKET INFO");
        groupstatus.add("DISCRIPTION");
        groupstatus.add("PRODUCT INFO");
        groupstatus.add("MACHIN INFO");
        groupstatus.add("ADD MEDIA");
        groupstatus.add("FINALIZE/SUBMIT TICKET");

        PendingActions.spinnerAdapter group_status_adapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner);
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
                if (status.equalsIgnoreCase("EDIT TICKET INFO")) {
                    status = "";
                    //MyServiceList_Api();
                    Intent intent = new Intent(PendingActions.this, EditTicketInfoActivity.class);
                    startActivity(intent);
                    Log.e("log",status);
                    //finish();
                } else if (status.equalsIgnoreCase("DISCRIPTION")) {
                    status = "0";
                    Intent intent = new Intent(PendingActions.this,DESCRIPTION.class);
                    startActivity(intent);
                    Log.e("log",status);

                    //finish();
                } else if (status.equalsIgnoreCase("PRODUCT INFO")) {
                    status = "1";
                    Intent intent = new Intent(PendingActions.this,ProductionInfo.class);
                    startActivity(intent);
                    //finish();
                    Log.e("log",status);

                } else if (status.equalsIgnoreCase("MACHIN INFO")) {
                    status = "2";
                    Intent intent = new Intent(PendingActions.this,MachineInfo.class);
                    startActivity(intent);
                    //finish();
                    Log.e("log",status);

                } else if (status.equalsIgnoreCase("ADD MEDIA")) {
                    status = "3";
                    Intent intent = new Intent(PendingActions.this,AddMediaActivity.class);
                    startActivity(intent);
                    //finish();
                    Log.e("log",status);

                } else if (status.equalsIgnoreCase("FINALIZE/SUBMIT TICKET")) {
                    status = "3";
                    Intent intent = new Intent(PendingActions.this, Reviewfinalize.class);
                    startActivity(intent);
                    // finish();
                    Log.e("log",status);

                }
                if (spinner.getSelectedView() != null) {
                    ((TextView) spinner.getSelectedView()).setTextColor(0xFFFFFFFF);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pending_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userPendingAction = pending_actiontxt.getText().toString().trim();
                userDatepickertxt = text_pickerset.getText().toString().trim();
                userpendingComments = pending_comments.getText().toString().trim();

                if (userPendingAction.equals("")){
                    pending_actiontxt.setError("This field can not be blank");
                    pending_actiontxt.requestFocus();
                    return;
                } else  if(userDatepickertxt.equals("")){
                    text_pickerset.setError("This field can not be blank");
                    text_pickerset.requestFocus();
                    return;
                }else  if(userpendingComments.equals("")){
                    pending_comments.setError("This field can not be blank");
                    pending_comments.requestFocus();
                    return;
                }else {

                    //Pending_Action_Api();
                    Intent intent = new Intent(PendingActions.this, AddMediaActivity.class);
                    startActivity(intent);
                   // finish();
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
                datePickerDialog = new DatePickerDialog(PendingActions.this,R.style.DialogTheme,
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
    }
}