package com.example.myapplication.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.WorkoutEntity;
import com.example.myapplication.databinding.WorkoutCreateBinding;

public class WorkoutCreateFragment extends Fragment {

    private WorkoutCreateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = WorkoutCreateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnSaveWorkout.setOnClickListener(v -> {
            String title = binding.etWorkoutName.getText().toString().trim();
            String time = binding.etWorkoutTime.getText().toString().trim();
            String exercises = binding.etExerciseTypes.getText().toString().trim();
            String videoUrl = binding.etVideoLink.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(getContext(), "Please enter workout name", Toast.LENGTH_SHORT).show();
                return;
            }

            WorkoutEntity workout = new WorkoutEntity();
            workout.title = title;
            workout.time = time.isEmpty() ? "30 min" : time;
            workout.exercises = exercises.isEmpty() ? "General" : exercises;
            workout.videoUrl = videoUrl.isEmpty() ? "https://youtube.com" : videoUrl;
            workout.likesCount = 0;

            AppDatabase.getInstance(requireContext()).workoutDao().insertWorkout(workout);

            Toast.makeText(getContext(), "Workout saved successfully!", Toast.LENGTH_SHORT).show();
            binding.etWorkoutName.setText("");
            binding.etWorkoutTime.setText("");
            binding.etExerciseTypes.setText("");
            binding.etVideoLink.setText("");
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
