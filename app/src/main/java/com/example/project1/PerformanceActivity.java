package com.example.project1;

import android.os.Bundle;
import android.widget.TextView;

public class PerformanceActivity extends BaseActivity {

    TextView quiz1Score, quiz2Score, quiz3Score, quiz4Score, quiz5Score, quiz6Score, quiz7Score, quiz8Score;
    ProgressCircleView circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        setupBottomNav(R.id.nav_performance);

        // Link text views
        quiz1Score = findViewById(R.id.quiz1Score);
        quiz2Score = findViewById(R.id.quiz2Score);
        quiz3Score = findViewById(R.id.quiz3Score);
        quiz4Score = findViewById(R.id.quiz4Score);
        quiz5Score = findViewById(R.id.quiz5Score);
        quiz6Score = findViewById(R.id.quiz6Score);
        quiz7Score = findViewById(R.id.quiz7Score);
        quiz8Score = findViewById(R.id.quiz8Score);


        // Link progress circles
        circle1 = findViewById(R.id.progressCircle1);
        circle2 = findViewById(R.id.progressCircle2);
        circle3 = findViewById(R.id.progressCircle3);
        circle4 = findViewById(R.id.progressCircle4);
        circle5 = findViewById(R.id.progressCircle5);
        circle6 = findViewById(R.id.progressCircle6);
        circle7 = findViewById(R.id.progressCircle7);
        circle8 = findViewById(R.id.progressCircle8);

        // Get database instance and DAO
        AppDatabase db = AppDatabase.getInstance(this);
        ScoreDao scoreDao = db.scoreDao();

        // Fetch stored quiz scores
        Score score1 = scoreDao.getScoreByQuizNumber(0);
        Score score2 = scoreDao.getScoreByQuizNumber(1);
        Score score3 = scoreDao.getScoreByQuizNumber(2);
        Score score4 = scoreDao.getScoreByQuizNumber(3);
        Score score5 = scoreDao.getScoreByQuizNumber(4);
        Score score6 = scoreDao.getScoreByQuizNumber(5);
        Score score7 = scoreDao.getScoreByQuizNumber(6);
        Score score8 = scoreDao.getScoreByQuizNumber(7);



        // Update display
        updateQuizDisplay(quiz1Score, circle1, "Difference between Electrical and Electronic Circuits", score1);
        updateQuizDisplay(quiz2Score, circle2, "Fundamentals of Electronic Circuits", score2);
        updateQuizDisplay(quiz3Score, circle3, "Resistors", score3);
        updateQuizDisplay(quiz4Score, circle4, "Scientific Notations and Metric System", score4);
        updateQuizDisplay(quiz5Score, circle5, "Electronic Components", score5);
        updateQuizDisplay(quiz6Score, circle6, "Semiconductors", score6);
        updateQuizDisplay(quiz7Score, circle7, "Transistors and Amplification", score7);
        updateQuizDisplay(quiz8Score, circle8, "Power Supplies", score8);

    }

    private void updateQuizDisplay(TextView textView, ProgressCircleView circleView, String title, Score score) {

        if (score == null) {
            textView.setText(title + ": N/A");
            circleView.setProgress(0);
            return;
        }

        int percent = (int) ((score.score / (float) score.total) * 100);

        textView.setText(title + ": " + score.score + " / " + score.total);

        if (percent == 100) {
            circleView.showCheckmark();
        } else {
            circleView.setProgress(percent);
        }
    }
}
