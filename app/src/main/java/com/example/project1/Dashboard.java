package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends BaseActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TextView dashboardText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        // Drawer setup
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        dashboardText = findViewById(R.id.navHeaderUserName);
        setupBottomNav(R.id.nav_home);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Get name from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = prefs.getString("USERNAME", null);

        if (userName != null && !userName.isEmpty()) {
            dashboardText.setText(userName);
        } else {
            dashboardText.setText("User");
        }

        //  Navigation Menu Actions
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            // Change Nickname
            if (id == R.id.nav_logout) {
                new AlertDialog.Builder(Dashboard.this)
                        .setTitle("Change Nickname")
                        .setMessage("Are you sure you want to Change Your Nickname?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.clear();
                            editor.apply();

                            // Redirect to startup
                            Intent intent = new Intent(Dashboard.this, User_startup.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();

            } else if (id == R.id.nav_exit) {
                //  Show confirmation dialog
                new AlertDialog.Builder(Dashboard.this)
                        .setTitle("Exit App")
                        .setMessage("Are you sure you want to exit the App?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            finishAffinity();
                            System.exit(0);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }
}
