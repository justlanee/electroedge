package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.github.barteksc.pdfviewer.PDFView;

public class topicviewer extends AppCompatActivity {

    public static final String EXTRA_FILE = "FILE_URL";
    PDFView pdfView;
    Button btnTakeQuiz, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        pdfView = findViewById(R.id.pdfView);
        btnTakeQuiz = findViewById(R.id.btnTakeQuiz);
        btnBack = findViewById(R.id.btnBack);

        String fileName = getIntent().getStringExtra(EXTRA_FILE);

        if (fileName != null) {
            pdfView.fromAsset(fileName)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .load();
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(topicviewer.this, TopicList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        //  Correct quiz number (now returns 0 for "Chapter1.pdf")
        int quizNumber = getQuizNumberFromFile(fileName);

        //  Open corresponding quiz
        btnTakeQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(topicviewer.this, QuizActivity.class);
            // This will pass 0 for Chapter1.pdf, 1 for Chapter2.pdf, etc.
            intent.putExtra("QUIZ_NUMBER", quizNumber);
            intent.putExtra("QUIZ_TITLE", fileName.replace(".pdf", ""));
            startActivity(intent);
        });
    }

    //  EDIT: Extracts chapter number and subtracts 1 for zero-indexing.
    private int getQuizNumberFromFile(String fileName) {
        if (fileName == null) return 0;

        // Example filenames: "Chapter1.pdf", "Topic2.pdf"
        String numberOnly = fileName.replaceAll("[^0-9]", "");
        try {
            int chapterNumber = Integer.parseInt(numberOnly);

            // Subtract 1 to convert from 1-based file number to 0-based quiz index
            return chapterNumber - 1;

        } catch (Exception e) {
            // Return -1 or 0 in case of error, depending on your error handling logic
            return 0;
        }
    }
}