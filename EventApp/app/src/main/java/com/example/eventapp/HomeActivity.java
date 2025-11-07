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

/**
 * This is the  first screen of the app that lets users choose between
 * signing in, creating an account, or viewing events as a guest.
 *
 * If a user is already signed in, they are automatically redirected
 * to the landing page without seeing this screen.
 *
 * Author: tappit
 */
public class HomeActivity extends AppCompatActivity {

    /** Firebase authentication instance used to check user login state. */
    private FirebaseAuth mAuth;

    /**
     * Called when the activity is first created.
     * Checks if a user is already signed in and redirects if needed,
     * otherwise sets up the home screen buttons and toolbar.
     *
     * @param savedInstanceState saved state of the activity, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d("AuthCheck", currentUser != null
                ? "Signed in as: " + currentUser.getEmail()
                : "No user signed in");

        if (currentUser != null) {
            Log.d("AuthCheck", "User already signed in, redirecting to LandingHostActivity...");
            Intent intent = new Intent(HomeActivity.this, LandingHostActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setupHomeUI();
    }

    /**
     * Sets up the toolbar and the main buttons for the home screen:
     * "Getting Started", "Create Account", and "View Events".
     */
    private void setupHomeUI() {
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tappit");
        }

        Button btnGettingStarted = findViewById(R.id.btnGettingStarted);
        btnGettingStarted.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        Button btnViewEvents = findViewById(R.id.button_view_events);
        btnViewEvents.setOnClickListener(v ->
                Toast.makeText(this, "Guest view coming soon!", Toast.LENGTH_SHORT).show());
    }

    /**
     * Inflates the top-right menu with options like profile and sign-in.
     *
     * @param menu the Menu object to inflate items into
     * @return true once the menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Handles actions when menu items are clicked.
     *
     * @param item the selected menu item
     * @return true if the item click was handled
     */
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
