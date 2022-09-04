package com.example.garridoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.garridoapp.R;

public class Reviewfinalize extends AppCompatActivity {
    TextView review_relized;
     Dialog dialogs;
     ImageView back_arrow_pp;
     LinearLayout review_relized_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewfinalize);

        back_arrow_pp= findViewById(R.id.back_arrow_pp);
        review_relized= findViewById(R.id.review_relized);
        review_relized_btn= findViewById(R.id.review_relized_btn);

        back_arrow_pp.setOnClickListener(v -> finish());


        review_relized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reviewfinalize_AlertDialog();
            }
        });
    }

    private void Reviewfinalize_AlertDialog() {
        final LayoutInflater inflater = Reviewfinalize.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_review_alert, null);
        final TextView tvMessage = alertLayout.findViewById(R.id.textViewMessage);
        final TextView btnYes = alertLayout.findViewById(R.id.btnd_closs);

        final AlertDialog.Builder alert = new AlertDialog.Builder(Reviewfinalize.this);
        tvMessage.setText(Reviewfinalize.this.getString(R.string.are_you_sure_to_review_closs));
        alert.setView(alertLayout);
        alert.setCancelable(false);

        btnYes.setOnClickListener(v -> {
            dialogs.dismiss();
          /*  SharedPreferences preferences = MainActivity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
            preferences.edit().remove("userId").apply();*/
            Intent intent = new Intent(Reviewfinalize.this, Reviewfinalize.class);
            startActivity(intent);
            finish();
        });

        dialogs = alert.create();
        dialogs.show();
    }
}