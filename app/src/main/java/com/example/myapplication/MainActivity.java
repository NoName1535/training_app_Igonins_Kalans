package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        Button btnAdminUsers = binding.getRoot().findViewById(R.id.btnAdminUsers);
        if (isAdmin && btnAdminUsers != null) {
            btnAdminUsers.setVisibility(View.VISIBLE);
            btnAdminUsers.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
                startActivity(intent);
            });
        }

        new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.leaderboard, R.id.workoutCreate, R.id.calculator, R.id.profilenav)
                .build();
        
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
    }
}
