package com.example.project1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ScoreDao {

    @Insert
    void insert(Score score);

    @Update
    void update(Score score);

    @Query("SELECT * FROM scores WHERE quizNumber = :quizNumber LIMIT 1")
    Score getScoreByQuizNumber(int quizNumber);

    @Query("DELETE FROM scores")
    void clearAll();
}
