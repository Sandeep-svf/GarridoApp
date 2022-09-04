package com.example.garridoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.garridoapp.Adapter.AddMediaAdapter;
import com.example.garridoapp.R;

public class AddMediaActivity extends AppCompatActivity {
    RecyclerView add_media_recycleview;
    AddMediaAdapter addMediaAdapter;
    ImageView back_arrow_pp;
    LinearLayout submit_add_media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);
        add_media_recycleview= findViewById(R.id.add_media_recycleview);
        back_arrow_pp= findViewById(R.id.back_arrow_pp);
        submit_add_media= findViewById(R.id.submit_add_media);

        back_arrow_pp.setOnClickListener(view -> finish());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        add_media_recycleview.setLayoutManager(linearLayoutManager);

        addMediaAdapter = new AddMediaAdapter(getApplicationContext());
        add_media_recycleview.setAdapter(addMediaAdapter);


        submit_add_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMediaActivity.this, Reviewfinalize.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}