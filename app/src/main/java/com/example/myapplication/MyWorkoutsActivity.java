package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.WorkoutEntity;

import java.util.ArrayList;
import java.util.List;

public class MyWorkoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workouts);

        ListView listView = findViewById(R.id.listViewMyWorkouts);
        String username = getIntent().getStringExtra("username");

        List<WorkoutEntity> workouts = AppDatabase.getInstance(this).workoutDao().getAllWorkouts();
        List<String> workoutTitles = new ArrayList<>();
        List<Integer> workoutIds = new ArrayList<>();

        for (WorkoutEntity w : workouts) {
            workoutTitles.add(w.title + " (" + w.time + ")");
            workoutIds.add(w.id);
        }

        if (workoutTitles.isEmpty()) {
            workoutTitles.add("No workouts created yet.");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, workoutTitles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < workoutIds.size()) {
                int workoutId = workoutIds.get(position);
                Intent intent = new Intent(MyWorkoutsActivity.this, WorkoutDetailActivity.class);
                intent.putExtra("workoutId", workoutId);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
