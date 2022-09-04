package com.example.garridoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.garridoapp.R;

import java.util.ArrayList;
import java.util.List;

public class MachineInfo extends AppCompatActivity {
    TextView  text_pickerset;
    LinearLayout machine_info;
    Spinner spinner;
    ImageView back_arrow_pp, edittxt_datepiker;
    DatePickerDialog datePickerDialog;
    EditText machine_model, serial_number, employee_trained, comments;
    String userMachineNumber, userSerialNumber, userComments,userDatePicker, userEmployeeTrained;
    String status="";
    int selectedItemText = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_info);
        machine_info = findViewById(R.id.machine_info);
        machine_model = findViewById(R.id.machine_model);
        serial_number = findViewById(R.id.serial_number);
        comments = findViewById(R.id.comments);
        spinner = findViewById(R.id.spinner);

      //  edittxt_datepiker = findViewById(R.id.edittxt_datepiker);
      //  text_pickerset = findViewById(R.id.text_pickerset);


        final List<String> groupstatus = new ArrayList<>();
        groupstatus.add("EDIT TICKET INFO");
        groupstatus.add("DISCRIPTION");
        groupstatus.add("PRODUCT INFO");
        groupstatus.add("PENDING ACTIONS");
        groupstatus.add("ADD MEDIA");
        groupstatus.add("FINALIZE/SUBMIT TICKET");

        //spinnerAdapter group_status_adapter = new spinnerAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter group_status_adapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner);
        group_status_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_edit);
        //group_status_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //group_status_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
                    Intent intent = new Intent(MachineInfo.this, EditTicketInfoActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("DISCRIPTION")) {
                    status = "0";
                    Intent intent = new Intent(MachineInfo.this,DESCRIPTION.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("PRODUCT INFO")) {
                    status = "1";
                    Intent intent = new Intent(MachineInfo.this,ProductionInfo.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("PENDING ACTIONS")) {
                    status = "2";
                    Intent intent = new Intent(MachineInfo.this,PendingActions.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("ADD MEDIA")) {
                    status = "3";
                    Intent intent = new Intent(MachineInfo.this,AddMediaActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("FINALIZE/SUBMIT TICKET")) {
                    status = "3";
                    Intent intent = new Intent(MachineInfo.this,Reviewfinalize.class);
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

/*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
               int selectedItemText = spinner.getSelectedItemPosition() + 1;

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {
              Intent intent ;
                switch(position){
                    case 0:
                        intent = new Intent(MachineInfo.this, MachineInfo.class);
                        break;
                    case 1:
                        intent = new Intent(MachineInfo.this, DESCRIPTION.class);
                        break;
                    case 2:
                        intent = new Intent(MachineInfo.this, ProductionInfo.class);
                        break;
                    case 3:
                        intent = new Intent(MachineInfo.this, PendingActions.class);
                        break;
                    case 4:
                        intent = new Intent(MachineInfo.this, AddMediaActivity.class);
                        break;
                    case 5:
                        intent = new Intent(MachineInfo.this, Reviewfinalize.class);
                        break;

                    default:
                        throw new IllegalStateException(String.valueOf(selectedItemText + position));
                }
                startActivity(intent);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
*/


        back_arrow_pp = findViewById(R.id.back_arrow_pp);
        back_arrow_pp.setOnClickListener(view -> finish());
/*
        back_arrow_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MachineInfo.this, NewTicketInfo.class);
                startActivity(intent);
                //finish();
            }
        });
*/


        machine_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userMachineNumber = machine_model.getText().toString().trim();
                userSerialNumber = serial_number.getText().toString().trim();
              //  userDatePicker = text_pickerset.getText().toString().trim();
                userComments = comments.getText().toString().trim();

                if (userMachineNumber.equals("")){
                    machine_model.setError("This field can not be blank");
                    machine_model.requestFocus();
                    return;
                } else  if(userSerialNumber.equals("")){
                    serial_number.setError("This field can not be blank");
                    serial_number.requestFocus();
                    return;
                }
              /*  else  if(userDatePicker.equals("")){
                    text_pickerset.setError("This field can not be blank");
                    text_pickerset.requestFocus();
                    return;
                }*/
                else  if(userComments.equals("")){
                    comments.setError("This field can not be blank");
                    comments.requestFocus();
                    return;
                }else {
                    //Call Api()
                    Intent intent = new Intent(MachineInfo.this, DESCRIPTION.class);
                    startActivity(intent);
                }
            }
        });

/*
        edittxt_datepiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MachineInfo.this,R.style.DialogTheme,
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
*/

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