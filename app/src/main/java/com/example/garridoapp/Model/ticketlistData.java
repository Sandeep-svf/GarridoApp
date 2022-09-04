package com.example.garridoapp.Model;

import com.example.garridoapp.Response.TicketList_Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ticketlistData {
    @SerializedName("ticket")
    @Expose
    private List<TicketList_Response> ticketlist_responseList = null;

    public List<TicketList_Response> getTicket() {
        return ticketlist_responseList;
    }

    public void setTicket(List<TicketList_Response> ticket) {
        this.ticketlist_responseList = ticket;
    }
}
