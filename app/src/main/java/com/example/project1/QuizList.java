package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class QuizList extends BaseActivity {

    ListView quizListView;

    String[] quizzes = {
            "Difference between Electrical and Electronic Circuits",
            "Fundamentals of Electronic Circuits",
            "Resistors",
            "Scientific Notations and Metric System",
            "Electronic Components",
            "Semiconductors",
            "Transistors and Amplification",
            "Power Supplies"
    };

    boolean[] unlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        setupBottomNav(R.id.nav_quizzes);

        quizListView = findViewById(R.id.quizListView);

        //  DB + DAO
        AppDatabase db = AppDatabase.getInstance(this);
        ScoreDao scoreDao = db.scoreDao();

        //  Initialize unlock array
        unlocked = new boolean[quizzes.length];
        unlocked[0] = true; // First quiz always unlocked

        //  Unlock logic: must score ≥80% on previous quiz
        for (int i = 0; i < quizzes.length - 1; i++) {
            Score score = scoreDao.getScoreByQuizNumber(i);

            if (score != null && score.total > 0) {
                double percent = (score.score * 100.0) / score.total;

                if (percent >= 80.0) {
                    unlocked[i + 1] = true;
                }
            }
        }

        //  Attach custom adapter
        QuizListAdapter adapter = new QuizListAdapter(this, quizzes, unlocked);
        quizListView.setAdapter(adapter);

        //  Handle item click
        quizListView.setOnItemClickListener((parent, view, position, id) -> {

            if (unlocked[position]) {

                Intent intent = new Intent(QuizList.this, QuizActivity.class);
                intent.putExtra("QUIZ_NUMBER", position);
                intent.putExtra("QUIZ_TITLE", quizzes[position]);
                startActivity(intent);

            } else {
                Toast.makeText(
                        this,
                        "This quiz is locked. Get at least 80% on the previous quiz to unlock.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
