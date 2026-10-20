package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FlashcardList extends BaseActivity {

    ListView flashcardListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_list);
        setupBottomNav(R.id.nav_flashcards);

        flashcardListView = findViewById(R.id.flashcardListView);

        String[] sets = {
                "Basic Components",
                "Semiconductors",
                "Amplifiers"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_flashcard,
                R.id.flashcardTitle,
                sets
        );

        flashcardListView.setAdapter(adapter);

        flashcardListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(FlashcardList.this, FlashcardsActivity.class);
            intent.putExtra("SET_NUMBER", position);
            startActivity(intent);
        });
    }
}
