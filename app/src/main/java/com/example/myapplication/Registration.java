package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Button login = findViewById(R.id.btnsignup);
        login.setOnClickListener(this::onClick);

        Button log = findViewById(R.id.btnsignin);
        log.setOnClickListener(this::LogClick);
    }

    private void onClick(View v) {
        Intent intent = new Intent(Registration.this, MainActivity.class);
        startActivity(intent);
    }
    private void LogClick(View v) {
        Intent intent = new Intent(Registration.this, Login.class);
        startActivity(intent);
    }
}
