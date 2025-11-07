package com.example.eventapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.eventapp.utils.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingHostActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_host);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        //Initialize FirebaseAuth once for the entire landing host
        auth = FirebaseHelper.getAuth();

        //Add listener to detect user restoration or sign-out
        authListener = firebaseAuth -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            if (currentUser == null) {
                Log.w("LandingHostActivity", "No active user — redirecting to LoginActivity");
                Intent intent = new Intent(LandingHostActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Log.d("LandingHostActivity", "User active: " + currentUser.getEmail());
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth != null && authListener != null) {
            auth.addAuthStateListener(authListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (auth != null && authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
