package com.example.project1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scores")
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int quizNumber;
    public int score;
    public int total;

    public Score(int quizNumber, int score, int total) {
        this.quizNumber = quizNumber;
        this.score = score;
        this.total = total;
    }

    public boolean isPassed() {
        if (total == 0) return false;
        return ((double) score / total) * 100 >= 80;
    }
}
