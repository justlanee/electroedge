package com.example.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizListAdapter extends ArrayAdapter<String> {

    private final boolean[] unlocked;

    public QuizListAdapter(Context context, String[] quizzes, boolean[] unlocked) {
        super(context, R.layout.list_item_quiz, quizzes);
        this.unlocked = unlocked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_quiz, parent, false);
        }

        TextView title = v.findViewById(R.id.quizItemText);
        TextView arrow = v.findViewById(R.id.arrowIcon);
        ImageView lockIcon = v.findViewById(R.id.lockIcon);

        title.setText(getItem(position));

        if (unlocked[position]) {
            v.setAlpha(1f);
            arrow.setVisibility(View.VISIBLE);
            lockIcon.setVisibility(View.GONE);
        } else {
            v.setAlpha(0.5f);
            arrow.setVisibility(View.GONE);
            lockIcon.setVisibility(View.VISIBLE);
        }

        return v;
    }
}
