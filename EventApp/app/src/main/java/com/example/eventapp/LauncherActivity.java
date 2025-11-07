package com.example.eventapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // User is already signed in
            startActivity(new Intent(this, LandingHostActivity.class));
        } else {
            // User not signed in
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish(); // Close launcher so it doesn't stay on back stack
    }
}
