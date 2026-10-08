package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MyWorkoutsActivity;
import com.example.myapplication.SettingsActivity;
import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.User;
import com.example.myapplication.databinding.ProfileBinding;

public class ProfileFragment extends Fragment {

    private ProfileBinding binding;
    private String currentUsername = "User";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getActivity() != null && getActivity().getIntent() != null) {
            currentUsername = getActivity().getIntent().getStringExtra("username");
            if (currentUsername != null && !currentUsername.isEmpty()) {
                binding.tvUsername.setText(currentUsername);
            }
        }

        // Load streak
        if (currentUsername != null && !currentUsername.equals("admin")) {
            User user = AppDatabase.getInstance(requireContext()).userDao().getUserByUsername(currentUsername);
            if (user != null) {
                binding.tvStreakCount.setText(String.valueOf(user.streakCount));
            }
        } else {
            binding.tvStreakCount.setText("7");
        }

        binding.btnMyWorkouts.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MyWorkoutsActivity.class);
            intent.putExtra("username", currentUsername);
            startActivity(intent);
        });

        binding.btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            intent.putExtra("username", currentUsername);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
