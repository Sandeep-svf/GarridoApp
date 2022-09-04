package com.example.garridoapp.Fragments;

import android.os.Bundle;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.garridoapp.R;

public class SettingFragment extends Fragment {

    EditText device_tech_name, device_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_setting, container, false);

        device_tech_name = view.findViewById(R.id.device_tech_name);
        device_id = view.findViewById(R.id.device_id);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}