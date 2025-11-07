package com.example.eventapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // 🔍 Check if Firebase restored the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d("AuthCheck", currentUser != null
                ? "Signed in as: " + currentUser.getEmail()
                : "No user signed in");

        //If user is already logged in, skip the Home page
        if (currentUser != null) {
            Log.d("AuthCheck", "User already signed in, redirecting to LandingHostActivity...");
            Intent intent = new Intent(HomeActivity.this, LandingHostActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        //Only runs if not logged in
        setupHomeUI();
    }

    private void setupHomeUI() {
        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tappit");
        }

        // "Getting Started" → SignInActivity
        Button btnGettingStarted = findViewById(R.id.btnGettingStarted);
        btnGettingStarted.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        // "Create Account" → SignUpActivity
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Optional: "View Events" (guest mode)
        Button btnViewEvents = findViewById(R.id.button_view_events);
        btnViewEvents.setOnClickListener(v -> {
            Toast.makeText(this, "Guest view coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_sign_in) {
            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
