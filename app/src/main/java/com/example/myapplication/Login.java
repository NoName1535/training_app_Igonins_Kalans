package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button login = findViewById(R.id.btnsignin);
        login.setOnClickListener(this::onClick);

        Button reg = findViewById(R.id.btnsignup);
        reg.setOnClickListener(this::RegonClick);
    }

    private void onClick(View v) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    private void RegonClick(View v) {
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
    }
}

