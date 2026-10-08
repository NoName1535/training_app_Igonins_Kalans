package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.CommentEntity;
import com.example.myapplication.data.WorkoutEntity;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailActivity extends AppCompatActivity {

    private WorkoutEntity workout;
    private TextView tvWorkoutNameDetail, tvWorkoutTimeDetail, tvWorkoutExercisesDetail, tvLikesCount;
    private ListView listViewComments;
    private EditText etCommentInput;
    private String currentUsername = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        tvWorkoutNameDetail = findViewById(R.id.tvWorkoutNameDetail);
        tvWorkoutTimeDetail = findViewById(R.id.tvWorkoutTimeDetail);
        tvWorkoutExercisesDetail = findViewById(R.id.tvWorkoutExercisesDetail);
        tvLikesCount = findViewById(R.id.tvLikesCount);
        listViewComments = findViewById(R.id.listViewComments);
        etCommentInput = findViewById(R.id.etCommentInput);
        Button btnLike = findViewById(R.id.btnLike);
        Button btnPostComment = findViewById(R.id.btnPostComment);

        int workoutId = getIntent().getIntExtra("workoutId", -1);
        currentUsername = getIntent().getStringExtra("username");
        if (currentUsername == null || currentUsername.isEmpty()) {
            currentUsername = "User";
        }

        workout = AppDatabase.getInstance(this).workoutDao().getWorkoutById(workoutId);
        if (workout == null) {
            Toast.makeText(this, "Workout not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        displayWorkoutInfo();

        btnLike.setOnClickListener(v -> {
            workout.likesCount++;
            AppDatabase.getInstance(this).workoutDao().updateWorkout(workout);
            tvLikesCount.setText(workout.likesCount + " likes");
            Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();
        });

        btnPostComment.setOnClickListener(v -> {
            String text = etCommentInput.getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            CommentEntity comment = new CommentEntity();
            comment.workoutId = workout.id;
            comment.username = currentUsername;
            comment.text = text;

            AppDatabase.getInstance(this).commentDao().insertComment(comment);
            etCommentInput.setText("");
            loadComments();
            Toast.makeText(this, "Comment posted", Toast.LENGTH_SHORT).show();
        });

        loadComments();
    }

    private void displayWorkoutInfo() {
        tvWorkoutNameDetail.setText(workout.title);
        tvWorkoutTimeDetail.setText("Time: " + workout.time);
        tvWorkoutExercisesDetail.setText("Exercises: " + workout.exercises);
        tvLikesCount.setText(workout.likesCount + " likes");
    }

    private void loadComments() {
        List<CommentEntity> comments = AppDatabase.getInstance(this).commentDao().getCommentsForWorkout(workout.id);
        List<String> commentStrings = new ArrayList<>();
        if (comments.isEmpty()) {
            commentStrings.add("No comments yet. Be the first!");
        } else {
            for (CommentEntity c : comments) {
                commentStrings.add(c.username + ": " + c.text);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, commentStrings);
        listViewComments.setAdapter(adapter);
    }
}
