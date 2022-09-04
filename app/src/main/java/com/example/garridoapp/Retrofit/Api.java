package com.example.garridoapp.Retrofit;

import com.example.garridoapp.Model.CreateTicket_Model;
import com.example.garridoapp.Model.ForgotPassword_Model;
import com.example.garridoapp.Model.Login_Model;
import com.example.garridoapp.Model.Logout_Model;
import com.example.garridoapp.Model.TicketDetails_Model;
import com.example.garridoapp.Model.TicketList_Model;
import com.example.garridoapp.Model.UpdateTicket_Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Call<Login_Model> Login(@Field("email") String email,
                            @Field("password") String password,
                            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("forgot")
    Call<ForgotPassword_Model> ForgotPassword(@Field("email") String email,
                                              @Field("token") String token
    );


    @FormUrlEncoded
    @POST("ticket/edit")
    Call<UpdateTicket_Model> getUpdateTicket(@Field("user_id") String user_id,
                                             @Field("token") String token,
                                             @Field("tech_name") String tech_name,
                                             @Field("client_name") String client_name,
                                             @Field("city") String city,
                                             @Field("state") String state,
                                             @Field("date") String date,
                                             @Field("contact_info") String contact_info,
                                             @Field("email_id") String email_id,
                                             @Field("distributor_name") String distributor_name,
                                             @Field("distributor_info") String distributor_info,
                                             @Field("ticket_id") String ticket_id
    );

    @FormUrlEncoded
    @POST("ticket/details")
    Call<TicketDetails_Model> getTicketDetail(@Field("user_id") String user_id,
                                              @Field("token") String token,
                                              @Field("ticket_id") String ticket_id
    );

    @FormUrlEncoded
    @POST("ticket/create")
    Call<CreateTicket_Model> getCreateTicket(@Field("user_id") String user_id,
                                             @Field("token") String token,
                                             @Field("tech_name") String tech_name,
                                             @Field("client_name") String client_name,
                                             @Field("city") String city,
                                             @Field("state") String state,
                                             @Field("date") String date,
                                             @Field("contact_info") String contact_info,
                                             @Field("email_id") String email_id,
                                             @Field("distributor_name") String distributor_name,
                                             @Field("distributor_info") String distributor_info);

    @FormUrlEncoded
    @POST("ticket")
    Call<TicketList_Model> getTicketList(@Field("user_id") String user_id,
                                         @Field("token") String token
    );

    @FormUrlEncoded
    @POST("logout")
    Call<Logout_Model> getLogout(@Field("user_id") String user_id,
                                 @Field("token") String token
    );
}
