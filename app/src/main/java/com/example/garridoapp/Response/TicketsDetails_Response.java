package com.example.garridoapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketsDetails_Response {

    @SerializedName("ticket_id")
    @Expose
    public String ticketId;
    @SerializedName("tech_name")
    @Expose
    public String techName;
    @SerializedName("client_name")
    @Expose
    public String clientName;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("contact_info")
    @Expose
    public String contactInfo;
    @SerializedName("email_id")
    @Expose
    public String emailId;
    @SerializedName("distributor_name")
    @Expose
    public String distributorName;
    @SerializedName("distributor_info")
    @Expose
    public String distributorInfo;


    public String getTicketId() {
        return ticketId;
    }

    public String getTechName() {
        return techName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public String getDistributorInfo() {
        return distributorInfo;
    }
}
