package com.example.garridoapp.Model;

import com.example.garridoapp.Response.TicketsDetails_Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ticketsDetailData {



    @SerializedName("ticket")
    @Expose
    public TicketsDetails_Response ticket;


    public TicketsDetails_Response getTicket() {
        return ticket;
    }
}
