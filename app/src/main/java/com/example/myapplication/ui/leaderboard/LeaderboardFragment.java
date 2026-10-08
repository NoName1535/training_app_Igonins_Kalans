package com.example.myapplication.ui.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.User;
import com.example.myapplication.databinding.LeaderboardBinding;

import java.util.List;

public class LeaderboardFragment extends Fragment {

    private LeaderboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = LeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadLeaderboardData();

        return root;
    }

    private void loadLeaderboardData() {
        List<User> users = AppDatabase.getInstance(requireContext()).userDao().getAllUsers();
        // Sort by score/likes descending
        users.sort((u1, u2) -> Integer.compare(u2.score, u1.score));

        binding.leaderboardItemsContainer.removeAllViews();

        int rank = 1;
        for (User u : users) {
            View row = LayoutInflater.from(getContext()).inflate(R.layout.item_leaderboard_row, binding.leaderboardItemsContainer, false);
            TextView tvRank = row.findViewById(R.id.rowRank);
            TextView tvUser = row.findViewById(R.id.rowUser);
            TextView tvScore = row.findViewById(R.id.rowScore);

            tvRank.setText(String.valueOf(rank));
            tvUser.setText(u.username);
            tvScore.setText(String.valueOf(u.score));

            binding.leaderboardItemsContainer.addView(row);
            rank++;
        }

        if (users.isEmpty()) {
            TextView empty = new TextView(getContext());
            empty.setText("No users in leaderboard yet.");
            empty.setTextSize(16f);
            binding.leaderboardItemsContainer.addView(empty);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
