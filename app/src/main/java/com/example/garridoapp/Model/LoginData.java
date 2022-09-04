package com.example.garridoapp.Model;

import com.example.garridoapp.Response.Login_Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("user")
    @Expose
    private Login_Response user;

    public Login_Response getUser() {
        return user;
    }

    public void setUser(Login_Response user) {
        this.user = user;
    }
}
