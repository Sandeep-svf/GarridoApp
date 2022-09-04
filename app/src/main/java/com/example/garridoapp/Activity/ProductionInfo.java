package com.example.garridoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.garridoapp.Adapter.AddMediaAdapter;
import com.example.garridoapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductionInfo extends AppCompatActivity {
    LinearLayout product_info;
    ImageView back_arrow_pp;
    RecyclerView pruduct_recycleview;
    AddMediaAdapter productInfoAdapter;
    Spinner spinner;
    String status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_info);
        product_info = findViewById(R.id.product_info);
        back_arrow_pp = findViewById(R.id.back_arrow_pp);
        pruduct_recycleview = findViewById(R.id.pruduct_recycleview);
        spinner = findViewById(R.id.spinner);

/*
        back_arrow_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductionInfo.this, DESCRIPTION.class);
                startActivity(intent);
                //finish();
            }
        });
*/

        back_arrow_pp.setOnClickListener(view -> finish());

        final List<String> groupstatus = new ArrayList<>();
        groupstatus.add("EDIT TICKET INFO");
        groupstatus.add("DISCRIPTION");
        groupstatus.add("MACHINE INFO");
        groupstatus.add("PENDING ACTIONS");
        groupstatus.add("ADD MEDIA");
        groupstatus.add("FINALIZE/SUBMIT TICKET");

        ProductionInfo.spinnerAdapter group_status_adapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner);
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
                    Intent intent = new Intent(ProductionInfo.this, EditTicketInfoActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("DISCRIPTION")) {
                    status = "0";
                    Intent intent = new Intent(ProductionInfo.this,DESCRIPTION.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("MACHINE INFO")) {
                    status = "1";
                    Intent intent = new Intent(ProductionInfo.this,MachineInfo.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("PENDING ACTIONS")) {
                    status = "2";
                    Intent intent = new Intent(ProductionInfo.this,PendingActions.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("ADD MEDIA")) {
                    status = "3";
                    Intent intent = new Intent(ProductionInfo.this,AddMediaActivity.class);
                    startActivity(intent);
                    //finish();
                } else if (status.equalsIgnoreCase("FINALIZE/SUBMIT TICKET")) {
                    status = "3";
                    Intent intent = new Intent(ProductionInfo.this, Reviewfinalize.class);
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

        product_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductionInfo.this, PendingActions.class);
                startActivity(intent);
                //finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        pruduct_recycleview.setLayoutManager(linearLayoutManager);

        productInfoAdapter = new AddMediaAdapter(getApplicationContext());
        pruduct_recycleview.setAdapter(productInfoAdapter);
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