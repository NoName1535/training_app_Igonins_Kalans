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


public class Login extends AppCompatActivity {

    private EditText editUsername, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editUsername = findViewById(R.id.editTextText);
        editPassword = findViewById(R.id.editPassword);

        Button login = findViewById(R.id.btnsignin);
        login.setOnClickListener(this::onLoginClick);

        Button reg = findViewById(R.id.btnsignup);
        reg.setOnClickListener(this::RegonClick);
    }

    private void onLoginClick(View v) {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check Admin credentials
        if (username.equals("admin") && password.equals("admin")) {
            Toast.makeText(this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("isAdmin", true);
            intent.putExtra("username", "admin");
            startActivity(intent);
            finish();
            return;
        }

        // Check normal user in Room database
        User user = AppDatabase.getInstance(this).userDao().login(username, password);
        if (user != null) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("isAdmin", false);
            intent.putExtra("username", user.username);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void RegonClick(View v) {
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
    }
}
