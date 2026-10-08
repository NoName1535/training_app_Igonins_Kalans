package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.WorkoutDetailActivity;
import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.WorkoutEntity;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<WorkoutEntity> workoutList;
    private int currentIndex = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.textHome.setVisibility(View.GONE);

        // Configure WebView for video / web url
        binding.webViewVideo.getSettings().setJavaScriptEnabled(true);
        binding.webViewVideo.setWebViewClient(new WebViewClient());

        loadWorkouts();

        binding.btnPrevWorkout.setOnClickListener(v -> {
            if (workoutList != null && !workoutList.isEmpty()) {
                currentIndex = (currentIndex - 1 + workoutList.size()) % workoutList.size();
                showCurrentWorkout();
            } else {
                Toast.makeText(getContext(), "No workouts available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnNextWorkout.setOnClickListener(v -> {
            if (workoutList != null && !workoutList.isEmpty()) {
                currentIndex = (currentIndex + 1) % workoutList.size();
                showCurrentWorkout();
            } else {
                Toast.makeText(getContext(), "No workouts available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.cardWorkoutVideo.setOnClickListener(v -> {
            if (workoutList != null && !workoutList.isEmpty()) {
                WorkoutEntity current = workoutList.get(currentIndex);
                Intent intent = new Intent(getContext(), WorkoutDetailActivity.class);
                intent.putExtra("workoutId", current.id);
                String username = getActivity() != null ? getActivity().getIntent().getStringExtra("username") : "User";
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "No workout to view", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkouts();
    }

    private void loadWorkouts() {
        workoutList = AppDatabase.getInstance(requireContext()).workoutDao().getAllWorkouts();
        if (currentIndex >= workoutList.size()) {
            currentIndex = 0;
        }
        showCurrentWorkout();
    }

    private void showCurrentWorkout() {
        if (workoutList == null || workoutList.isEmpty()) {
            binding.tvWorkoutVideoTitle.setText("No Workouts Yet");
            binding.tvWorkoutVideoDetails.setText("Create a workout using the + button");
            binding.webViewVideo.loadData("<html><body style='background:black;color:white;text-align:center;padding-top:40px;'>No Video</body></html>", "text/html", "UTF-8");
        } else {
            WorkoutEntity w = workoutList.get(currentIndex);
            binding.tvWorkoutVideoTitle.setText(w.title);
            binding.tvWorkoutVideoDetails.setText("Time: " + w.time + " | Exercises: " + w.exercises + " | ❤️ " + w.likesCount);
            if (w.videoUrl != null && !w.videoUrl.isEmpty()) {
                String url = w.videoUrl;
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }
                binding.webViewVideo.loadUrl(url);
            } else {
                binding.webViewVideo.loadData("<html><body style='background:black;color:white;text-align:center;padding-top:40px;'>No Video URL provided</body></html>", "text/html", "UTF-8");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
