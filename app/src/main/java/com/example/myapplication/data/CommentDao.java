package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentDao {
    @Insert
    void insertComment(CommentEntity comment);

    @Query("SELECT * FROM comments WHERE workoutId = :workoutId")
    List<CommentEntity> getCommentsForWorkout(int workoutId);
}
