package com.example.garridoapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garridoapp.Activity.EditTicketInfoActivity;
import com.example.garridoapp.R;
import com.example.garridoapp.Response.TicketList_Response;

import java.util.List;

public class DeviceTicketsAdapter extends RecyclerView.Adapter<DeviceTicketsAdapter.ViewHolder> {
    public Context context;
    List<TicketList_Response> ticketlistResponseList;

    public DeviceTicketsAdapter(Context context, List<TicketList_Response> ticketListResponseList) {
        this.context=context;
        this.ticketlistResponseList = ticketListResponseList;
    }

    @NonNull
    @Override
    public DeviceTicketsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_tickets_layout,parent,false);
        return new DeviceTicketsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceTicketsAdapter.ViewHolder holder, int position) {

        holder.device_tickets_companyname.setText(ticketlistResponseList.get(position).getClientName());
        holder.device_tickets_date.setText(ticketlistResponseList.get(position).getDate());
        holder.device_tickets_number.setText(String.valueOf(ticketlistResponseList.get(position).getTicketId()));

        holder.device_editImg.setOnClickListener(v -> {
            String strTicketId = String.valueOf(ticketlistResponseList.get(position).getTicketId());
            Log.e("strTicketId",strTicketId);

            SharedPreferences getUserIdData = context.getSharedPreferences("TEMP", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = getUserIdData.edit();
            editor.putString("strTicketId", String.valueOf(strTicketId));
            ///editor.putString("authorization", String.valueOf(authorization));
            editor.apply();



            Intent intent = new Intent(context, EditTicketInfoActivity.class);
            intent.putExtra("sjfkdk", strTicketId);
           /* Bundle bundle = new Bundle();
            bundle.putString("index", strTicketId);
            intent.putExtras(bundle);*/
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ticketlistResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView device_tickets_date,device_tickets_number,device_tickets_companyname;
        ImageView device_eyeImg, device_editImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            device_tickets_companyname = itemView.findViewById(R.id.device_tickets_companyname);
            device_tickets_date = itemView.findViewById(R.id.device_tickets_date);
            device_tickets_number = itemView.findViewById(R.id.device_tickets_number);
            device_eyeImg = itemView.findViewById(R.id.device_eyeImg);
            device_editImg = itemView.findViewById(R.id.device_editImg);
        }
    }
}
