package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class Login extends Activity {
//https://www.youtube.com/watch?v=QHc6Kvx7E3A
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //https://www.youtube.com/watch?v=QHc6Kvx7E3A
        Button login = findViewById(R.id.btnsignin);
            login.setOnClickListener(this::onClick);
        }


    private void onClick(View v) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }
}


