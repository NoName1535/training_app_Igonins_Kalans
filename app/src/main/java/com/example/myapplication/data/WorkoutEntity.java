package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts")
public class WorkoutEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String time;
    public String exercises;
    public String videoUrl;
    public int likesCount;
}
