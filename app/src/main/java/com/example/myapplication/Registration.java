package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.User;

public class Registration extends AppCompatActivity {

    private EditText editUsername, editPassword, editRepeatPassword, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editRepeatPassword = findViewById(R.id.editRepeatPassword);
        editEmail = findViewById(R.id.editEmail);

        Button signup = findViewById(R.id.btnsignup);
        signup.setOnClickListener(this::onRegisterClick);

        Button log = findViewById(R.id.btnsignin);
        log.setOnClickListener(this::LogClick);
    }

    private void onRegisterClick(View v) {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String repeatPassword = editRepeatPassword.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repeatPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.equalsIgnoreCase("admin")) {
            Toast.makeText(this, "Username 'admin' is reserved", Toast.LENGTH_SHORT).show();
            return;
        }

        User existing = AppDatabase.getInstance(this).userDao().getUserByUsername(username);
        if (existing != null) {
            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User();
        newUser.username = username;
        newUser.password = password;
        newUser.email = email;

        AppDatabase.getInstance(this).userDao().insertUser(newUser);

        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Registration.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void LogClick(View v) {
        Intent intent = new Intent(Registration.this, Login.class);
        startActivity(intent);
        finish();
    }
}
