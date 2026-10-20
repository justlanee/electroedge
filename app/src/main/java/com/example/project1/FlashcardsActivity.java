package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class FlashcardsActivity extends BaseActivity {

    private CardView flashCard;
    private ImageView frontImage;
    private TextView backText;
    private MaterialButton btnBack;

    private boolean isFront = true;
    private int currentIndex = 0;
    private List<Flashcard> flashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        flashCard = findViewById(R.id.flashCard);
        frontImage = findViewById(R.id.frontImage);
        backText = findViewById(R.id.backText);
        btnBack = findViewById(R.id.btnBack);

        //  Back button behavior
        btnBack.setOnClickListener(v -> onBackPressed());

        //  Enable bottom navigation
        setupBottomNav(R.id.nav_flashcards);

        //  Get which set was clicked from FlashcardListActivity
        int setNumber = getIntent().getIntExtra("SET_NUMBER", 0);

        //  Load flashcards for that specific set
        flashcards = getFlashcardsForSet(setNumber);
        displayCurrentCard();

        // Flip on tap
        flashCard.setOnClickListener(v -> flipCard());

        //  Swipe left/right to navigate
        flashCard.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                nextCard();
            }

            @Override
            public void onSwipeRight() {
                previousCard();
            }
        });
    }

    //  Shows the current card
    private void displayCurrentCard() {
        Flashcard current = flashcards.get(currentIndex);
        frontImage.setImageResource(current.getImageResId());
        backText.setText(current.getDefinition());
        frontImage.setVisibility(View.VISIBLE);
        backText.setVisibility(View.GONE);
        isFront = true;
    }

    // Flip animation
    private void flipCard() {
        Animation flipOut = AnimationUtils.loadAnimation(this, R.anim.flip_out);
        Animation flipIn = AnimationUtils.loadAnimation(this, R.anim.flip_in);

        if (isFront) {
            frontImage.startAnimation(flipOut);
            frontImage.setVisibility(View.GONE);
            backText.setVisibility(View.VISIBLE);
            backText.startAnimation(flipIn);
        } else {
            backText.startAnimation(flipOut);
            backText.setVisibility(View.GONE);
            frontImage.setVisibility(View.VISIBLE);
            frontImage.startAnimation(flipIn);
        }

        isFront = !isFront;
    }

    //  Next / Previous navigation
    private void nextCard() {
        currentIndex = (currentIndex + 1) % flashcards.size();
        displayCurrentCard();
    }

    private void previousCard() {
        currentIndex = (currentIndex - 1 + flashcards.size()) % flashcards.size();
        displayCurrentCard();
    }

    //  Loads flashcards depending on which set was chosen
    private List<Flashcard> getFlashcardsForSet(int setNumber) {
        List<Flashcard> list = new ArrayList<>();

        switch (setNumber) {
            case 0: // Basic Components
                list.add(new Flashcard(R.drawable.resistor, "Resistor — limits the flow of current."));
                list.add(new Flashcard(R.drawable.capacitor, "Capacitor — stores electrical energy."));
                break;

            case 1: // Semiconductors
                 list.add(new Flashcard(R.drawable.diode, "Diode — allows current to flow in one direction."));
                 list.add(new Flashcard(R.drawable.led, "LED — light-emitting diode that glows when forward-biased."));
                break;

            case 2: // Amplifiers
                 list.add(new Flashcard(R.drawable.transistor, "Transistor — used for signal amplification."));
                 list.add(new Flashcard(R.drawable.amplifier, "Amplifier — amplifies voltage difference between inputs."));
                break;

        }

        return list;
    }

    //  Inner model class
    static class Flashcard {
        private final int imageResId;
        private final String definition;

        public Flashcard(int imageResId, String definition) {
            this.imageResId = imageResId;
            this.definition = definition;
        }

        public int getImageResId() { return imageResId; }
        public String getDefinition() { return definition; }
    }
}
