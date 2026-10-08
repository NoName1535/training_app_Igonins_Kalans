package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        ListView listView = findViewById(R.id.listViewUsers);

        List<User> users = AppDatabase.getInstance(this).userDao().getAllUsers();
        List<String> userStrings = new ArrayList<>();

        if (users.isEmpty()) {
            userStrings.add("No registered users found.");
        } else {
            for (User u : users) {
                userStrings.add("Username: " + u.username + "\nEmail: " + u.email);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, userStrings);
        listView.setAdapter(adapter);
    }
}
