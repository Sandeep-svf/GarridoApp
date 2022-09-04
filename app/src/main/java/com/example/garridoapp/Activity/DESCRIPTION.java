package com.example.garridoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class DESCRIPTION extends AppCompatActivity {

    TextView  text_pickerset,text_pickersett;
    LinearLayout descriptions;
    ImageView back_arrow_pp, edittxt_datepiker, edittxt_datepikerr;
    DatePickerDialog datePickerDialog;
    String userDatePicker,userDatePicker2, userEmployeeTrained,userEmployeeTrained2, userComments,userComments2;
    EditText employee_Trained, comments;
    Spinner spinner;
    String status="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        descriptions = findViewById(R.id.descriptions);
        employee_Trained = findViewById(R.id.employee_Trained);
        back_arrow_pp = findViewById(R.id.back_arrow_pp);
        comments = findViewById(R.id.comments);
        spinner = findViewById(R.id.spinner);

        edittxt_datepiker = findViewById(R.id.edittxt_datepiker);
        edittxt_datepikerr = findViewById(R.id.edittxt_datepikerr);
        text_pickerset = findViewById(R.id.text_pickerset);
        text_pickersett = findViewById(R.id.text_pickersett);

        back_arrow_pp = findViewById(R.id.back_arrow_pp);
/*
        back_arrow_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DESCRIPTION.this, MachineInfo.class);
                startActivity(intent);
            }
        });

*/

        back_arrow_pp.setOnClickListener(view -> finish());


        final List<String> groupstatus = new ArrayList<>();
        groupstatus.add("EDIT TICKET INFO");
        groupstatus.add("MACHINE INFO");
        groupstatus.add("PRODUCT INFO");
        groupstatus.add("PENDING ACTIONS");
        groupstatus.add("ADD MEDIA");
        groupstatus.add("FINALIZE/SUBMIT TICKET");
        DESCRIPTION.spinnerAdapter group_status_adapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner);
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
                    Intent intent = new Intent(DESCRIPTION.this, EditTicketInfoActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("MACHINE INFO")) {
                    status = "0";
                    Intent intent = new Intent(DESCRIPTION.this,MachineInfo.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("PRODUCT INFO")) {
                    status = "1";
                    Intent intent = new Intent(DESCRIPTION.this,ProductionInfo.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("PENDING ACTIONS")) {
                    status = "2";
                    Intent intent = new Intent(DESCRIPTION.this,PendingActions.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("ADD MEDIA")) {
                    status = "3";
                    Intent intent = new Intent(DESCRIPTION.this,AddMediaActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("FINALIZE/SUBMIT TICKET")) {
                    status = "3";
                    Intent intent = new Intent(DESCRIPTION.this, Reviewfinalize.class);
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
        });
        descriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDatePicker = text_pickerset.getText().toString().trim();
                userDatePicker2 = text_pickersett.getText().toString().trim();
                userEmployeeTrained = employee_Trained.getText().toString().trim();
                userEmployeeTrained2 = employee_Trained.getText().toString().trim();
                userComments = comments.getText().toString().trim();
                userComments2 = comments.getText().toString().trim();

                if (userDatePicker.equals("")){
                    text_pickerset.setError("This field can not be blank");
                    text_pickerset.requestFocus();
                    return;
                } else  if(userEmployeeTrained.equals("")){
                    employee_Trained.setError("This field can not be blank");
                    employee_Trained.requestFocus();
                    return;
                }else  if(userComments.equals("")){
                    comments.setError("This field can not be blank");
                    comments.requestFocus();
                    return;

                }else  if(userEmployeeTrained2.equals("")){
                    employee_Trained.setError("This field can not be blank");
                    employee_Trained.requestFocus();
                    return;
                }else  if(userComments2.equals("")) {
                    comments.setError("This field can not be blank");
                    comments.requestFocus();
                    return;
                }else if (userDatePicker2.equals("")){
                    text_pickersett.setError("This field can not be blank");
                    text_pickersett.requestFocus();
                    return;
                }
                else {
                    //Description_Api();
                    Intent intent = new Intent(DESCRIPTION.this, ProductionInfo.class);
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
                datePickerDialog = new DatePickerDialog(DESCRIPTION.this,R.style.DialogTheme,
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

        edittxt_datepikerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(DESCRIPTION.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                text_pickersett.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

            }
        });


    }

    private void Description_Api() {

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