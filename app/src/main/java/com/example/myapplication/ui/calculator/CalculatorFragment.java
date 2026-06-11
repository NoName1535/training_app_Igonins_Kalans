package com.example.myapplication.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.CalCalculatorBinding;

public class CalculatorFragment extends Fragment {

    private CalCalculatorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = CalCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnBack.setOnClickListener(v -> 
            NavHostFragment.findNavController(this).navigate(R.id.navigation_home)
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}