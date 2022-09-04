package com.example.garridoapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketDetails_Model {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public ticketsDetailData data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ticketsDetailData getData() {
        return data;
    }
}
