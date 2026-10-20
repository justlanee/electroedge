package com.example.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TopicListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] topicTitles;
    private final List<String> realFileNames;
    private final boolean[] unlocked;

    public TopicListAdapter(Context context, String[] topicTitles, List<String> realFileNames, boolean[] unlocked) {
        super(context, R.layout.list_item_topic, topicTitles);
        this.context = context;
        this.topicTitles = topicTitles;
        this.realFileNames = realFileNames;
        this.unlocked = unlocked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemView = inflater.inflate(R.layout.list_item_topic, parent, false);
        }

        TextView chapterBadge = itemView.findViewById(R.id.chapterBadge);
        TextView topicTitleText = itemView.findViewById(R.id.topicItemText);
        ImageView lockIcon = itemView.findViewById(R.id.lockIcon);

        //  Extract chapter number from PDF filename if possible
        String fileName = realFileNames.get(position); // e.g., "Chapter 2 - Addition.pdf"
        String chapterNumber = String.valueOf(position + 1); // default

        if (fileName.toLowerCase().startsWith("chapter")) {
            String[] parts = fileName.split(" ");
            if (parts.length >= 2) {
                chapterNumber = parts[1]; // gets the number after "Chapter"
            }
        }
        chapterBadge.setText("Chapter " + chapterNumber);

        //  Set topic title
        topicTitleText.setText(topicTitles[position]);

        //  Show/hide lock icon
        lockIcon.setVisibility(unlocked[position] ? View.GONE : View.VISIBLE);

        return itemView;
    }
}
