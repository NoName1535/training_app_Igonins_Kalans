package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Insert
    void insertWorkout(WorkoutEntity workout);

    @Query("SELECT * FROM workouts")
    List<WorkoutEntity> getAllWorkouts();

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    WorkoutEntity getWorkoutById(int workoutId);

    @Update
    void updateWorkout(WorkoutEntity workout);
}
