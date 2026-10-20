package com.example.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_startup extends AppCompatActivity {

    EditText nameInput;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Check if there's already a saved username
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("USERNAME", null);

        if (savedName != null && !savedName.isEmpty()) {
            //  Skip name entry but go to WELCOME screen instead of Dashboard
            Intent intent = new Intent(User_startup.this, Welcome.class);
            intent.putExtra("USERNAME", savedName);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.user_startup);

        nameInput = findViewById(R.id.nameInput);
        startButton = findViewById(R.id.submitButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(User_startup.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    //  Save name to SharedPreferences
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("USERNAME", name);
                    editor.apply();

                    //  Go to Welcome activity after entering name
                    Intent intent = new Intent(User_startup.this, Welcome.class);
                    intent.putExtra("USERNAME", name);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
