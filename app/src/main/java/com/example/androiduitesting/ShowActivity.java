package com.example.androiduitesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView tvCityName = findViewById(R.id.tv_city_name);
        Button btnBack = findViewById(R.id.btn_back);

        // Get the city name passed from MainActivity
        String cityName = getIntent().getStringExtra("city_name");

        if (cityName != null) {
            tvCityName.setText(cityName);
        }

        // Handle Back Button click
        btnBack.setOnClickListener(v -> finish());
    }
}