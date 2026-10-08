package com.example.myapplication.ui.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.CalCalculatorBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalculatorFragment extends Fragment {

    private CalCalculatorBinding binding;
    private String selectedType = "Meat";
    private double accumulatedCalories = 0;
    private double accumulatedProtein = 0;
    private double accumulatedCarbs = 0;
    private double accumulatedFat = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = CalCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Current date and time in GMT+3
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        binding.tvDateTime.setText(sdf.format(new Date()));

        // Food type selection
        updateTypeSelection();
        binding.typeFish.setOnClickListener(v -> {
            selectedType = "Fish";
            updateTypeSelection();
        });
        binding.typeVeggie.setOnClickListener(v -> {
            selectedType = "Veggie";
            updateTypeSelection();
        });
        binding.typeMeat.setOnClickListener(v -> {
            selectedType = "Meat";
            updateTypeSelection();
        });

        // Add Food & Accumulate
        binding.btnAddFood.setOnClickListener(v -> {
            String foodName = binding.etFoodItem.getText().toString().trim();
            String weightStr = binding.etWeight.getText().toString().trim();

            if (weightStr.isEmpty()) {
                Toast.makeText(getContext(), "Please enter weight in grams", Toast.LENGTH_SHORT).show();
                return;
            }

            double weight = Double.parseDouble(weightStr);
            double calPer100 = 250;
            double pPer100 = 26;
            double cPer100 = 0;
            double fPer100 = 15;

            switch (selectedType) {
                case "Fish":
                    calPer100 = 200; pPer100 = 22; cPer100 = 0; fPer100 = 12;
                    break;
                case "Veggie":
                    calPer100 = 50; pPer100 = 3; cPer100 = 10; fPer100 = 1;
                    break;
                case "Meat":
                default:
                    calPer100 = 250; pPer100 = 26; cPer100 = 0; fPer100 = 15;
                    break;
            }

            double totalCalories = (weight / 100.0) * calPer100;
            double totalProtein = (weight / 100.0) * pPer100;
            double totalCarbs = (weight / 100.0) * cPer100;
            double totalFat = (weight / 100.0) * fPer100;

            accumulatedCalories += totalCalories;
            accumulatedProtein += totalProtein;
            accumulatedCarbs += totalCarbs;
            accumulatedFat += totalFat;

            binding.tvMealTotal.setText(String.format(Locale.getDefault(), "MEAL TOTAL: %.0f kcal", accumulatedCalories));
            binding.tvMacros.setText(String.format(Locale.getDefault(), "(P: %.1fg, C: %.1fg, F: %.1fg)", accumulatedProtein, accumulatedCarbs, accumulatedFat));

            Toast.makeText(getContext(), "Added " + (foodName.isEmpty() ? "Food" : foodName), Toast.LENGTH_SHORT).show();
            binding.etFoodItem.setText("");
            binding.etWeight.setText("");
            binding.etNotes.setText("");
        });

        return root;
    }

    private void updateTypeSelection() {
        binding.typeFish.setBackgroundColor(selectedType.equals("Fish") ? Color.parseColor("#FF8499") : Color.parseColor("#FFFFFF"));
        binding.typeVeggie.setBackgroundColor(selectedType.equals("Veggie") ? Color.parseColor("#FF8499") : Color.parseColor("#FFFFFF"));
        binding.typeMeat.setBackgroundColor(selectedType.equals("Meat") ? Color.parseColor("#FF8499") : Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
