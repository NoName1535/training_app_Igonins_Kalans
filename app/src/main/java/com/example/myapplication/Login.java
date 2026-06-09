package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;


import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.LoginBinding;

public class Login extends Activity {
    private LoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
