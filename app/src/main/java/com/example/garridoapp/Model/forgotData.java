package com.example.garridoapp.Model;

import com.example.garridoapp.Response.Forgotpassword_Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class forgotData {
    @SerializedName("user")
    @Expose
    private Forgotpassword_Response user;

    public Forgotpassword_Response getUser() {
        return user;
    }

    public void setUser(Forgotpassword_Response user) {
        this.user = user;
    }

}
