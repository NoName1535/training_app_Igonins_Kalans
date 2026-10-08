package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.User;

public class SettingsActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        Button btnSaveSettings = findViewById(R.id.btnSaveSettings);

        currentUsername = getIntent().getStringExtra("username");
        if (currentUsername == null) {
            currentUsername = "admin";
        }

        etNewUsername.setText(currentUsername);

        btnSaveSettings.setOnClickListener(v -> {
            String newUsername = etNewUsername.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();

            if (newUsername.isEmpty()) {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentUsername.equals("admin")) {
                Toast.makeText(this, "Admin credentials cannot be changed here", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = AppDatabase.getInstance(this).userDao().getUserByUsername(currentUsername);
            if (user != null) {
                user.username = newUsername;
                if (!newPassword.isEmpty()) {
                    user.password = newPassword;
                }
                AppDatabase.getInstance(this).userDao().updateUser(user);
                Toast.makeText(this, "Settings updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
