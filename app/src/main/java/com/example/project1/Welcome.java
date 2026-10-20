package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    TextView welcomeText;
    Button startDashboardButton;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        welcomeText = findViewById(R.id.welcomeMessage);
        startDashboardButton = findViewById(R.id.startReviewButton);

        //  Get name from startup screen
        name = getIntent().getStringExtra("USERNAME");

        if (name != null) {
            welcomeText.setText("Welcome, " + name + "!");
        }

        //  Save name permanently and move to Dashboard
        startDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Save the name to SharedPreferences
                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("USERNAME", name);
                editor.apply();

                //  Move to Dashboard
                Intent intent = new Intent(Welcome.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
