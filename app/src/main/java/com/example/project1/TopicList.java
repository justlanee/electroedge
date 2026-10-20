package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopicList extends BaseActivity {

    ListView topicList;
    List<String> realFileNames = new ArrayList<>();
    boolean[] unlocked;
    String[] topicTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        setupBottomNav(R.id.nav_topics);

        topicList = findViewById(R.id.topicList);

        // Load topics (PDFs) from assets
        List<String> topics = getPDFFilesFromAssets();
        if (topics.isEmpty()) {
            Toast.makeText(this, "No topics found in assets folder.", Toast.LENGTH_LONG).show();
            return;
        }

        topicTitles = topics.toArray(new String[0]);
        unlocked = new boolean[topicTitles.length];

        // Initialize topic unlocks
        updateUnlockedTopics();

        // Set adapter
        TopicListAdapter adapter = new TopicListAdapter(this, topicTitles, realFileNames, unlocked);
        topicList.setAdapter(adapter);

        // Handle topic clicks
        topicList.setOnItemClickListener((parent, view, position, id) -> {
            if (unlocked[position]) {
                String realFileName = realFileNames.get(position);
                Intent intent = new Intent(TopicList.this, topicviewer.class);
                intent.putExtra(topicviewer.EXTRA_FILE, realFileName);
                startActivity(intent);
            } else {
                Toast.makeText(
                        this,
                        "This topic is locked. Score at least 80% in the previous quiz to unlock it.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUnlockedTopics();
        ((TopicListAdapter) topicList.getAdapter()).notifyDataSetChanged();
    }

    // Load all PDFs from assets and clean display names
    private List<String> getPDFFilesFromAssets() {
        List<String> displayNames = new ArrayList<>();
        try {
            String[] files = getAssets().list("");
            if (files != null) {
                for (String file : files) {
                    if (file.toLowerCase().endsWith(".pdf")) {
                        realFileNames.add(file); // Keep full filename internally

                        // Remove "Chapter X - " from display name
                        String displayName = file.replace(".pdf", "")
                                .replaceAll("(?i)^chapter\\s\\d+\\s-\\s", "")
                                .replace("(", ""); // optional cleanup
                        displayNames.add(displayName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return displayNames;
    }

    // Unlock topics based on quiz scores
    private void updateUnlockedTopics() {
        AppDatabase db = AppDatabase.getInstance(this);
        ScoreDao scoreDao = db.scoreDao();

        if (unlocked.length > 0) unlocked[0] = true;

        for (int i = 1; i < unlocked.length; i++) {
            try {
                Score previousQuiz = scoreDao.getScoreByQuizNumber(i - 1);
                if (previousQuiz != null && previousQuiz.total > 0) {
                    double percent = (previousQuiz.score * 100.0) / previousQuiz.total;
                    if (percent >= 80) {
                        unlocked[i] = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
