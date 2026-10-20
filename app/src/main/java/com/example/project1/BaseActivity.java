package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNav(int selectedItemId) {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav == null) return;

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == selectedItemId) return true;

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, Dashboard.class));
            } else if (itemId == R.id.nav_quizzes) {
                startActivity(new Intent(this, QuizList.class));
            } else if (itemId == R.id.nav_topics) {
                startActivity(new Intent(this, TopicList.class));
            } else if (itemId == R.id.nav_performance) {
                startActivity(new Intent(this, PerformanceActivity.class));
            }else if (itemId == R.id.nav_flashcards) {
                    startActivity(new Intent(this, FlashcardList.class));
            } else {
                return false;
            }

            overridePendingTransition(0, 0);
            return true;
        });
    }
}