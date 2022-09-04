package com.example.garridoapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.garridoapp.Adapter.DeviceTicketsAdapter;
import com.example.garridoapp.Model.TicketList_Model;
import com.example.garridoapp.R;
import com.example.garridoapp.Response.TicketList_Response;
import com.example.garridoapp.Retrofit.Api_Client;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExitingTicketsFragment extends Fragment {
    RecyclerView exiting_ticket_recycleview;
    List<TicketList_Response> ticketListResponseList = new ArrayList<>();
    String userId = "", authorization = "";
    DeviceTicketsAdapter adapterTicketList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_exiting_tickets, container, false);
        exiting_ticket_recycleview = view.findViewById(R.id.exiting_ticket_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        exiting_ticket_recycleview.setLayoutManager(linearLayoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        userId = sharedPreferences.getString("UserID", "");
        authorization = sharedPreferences.getString("authorization", "");


      /*  adapterTicketList = new DeviceTicketsAdapter(getActivity(),ticketListResponseList);
        exiting_ticket_recycleview.setAdapter(adapterTicketList);
        */
        ExitingTicketList_Api();
        return view;
    }

    private void ExitingTicketList_Api()  {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.full_screen_dialog);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.getWindow().setGravity(Gravity.CENTER);

        WindowManager.LayoutParams WMLP = progressDialog.getWindow().getAttributes();
        WMLP.x = 20;
        WMLP.y = 30;
        progressDialog.getWindow().setAttributes(WMLP);
        progressDialog.show();
        Call<TicketList_Model> call = Api_Client.getClient().getTicketList(userId,authorization);

        call.enqueue(new Callback<TicketList_Model>() {
            @Override
            public void onResponse(Call<TicketList_Model> call, Response<TicketList_Model> response) {
                Log.e("dfdsfadfs",userId  );
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        TicketList_Model result = response.body();
                        String success = result.getSuccess();
                        String message = result.getMessage();
                        Log.e("dfdsfadfs",success + " " + message );

                        if (success.equalsIgnoreCase("true")) {
                            ticketListResponseList = result.getData().getTicket();
                            adapterTicketList = new DeviceTicketsAdapter(getActivity(),ticketListResponseList);
                            exiting_ticket_recycleview.setAdapter(adapterTicketList);
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } else {
                        try {
                            progressDialog.dismiss();
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException exception) {
                    exception.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TicketList_Model> call, Throwable t) {
                progressDialog.dismiss();

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}