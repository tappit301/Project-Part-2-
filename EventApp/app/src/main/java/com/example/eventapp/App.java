package com.example.eventapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        Log.d("FirebaseInit", "Firebase initialized");

        // Enable Firestore offline persistence
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(
                new FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(true)
                        .build()
        );

        //Detect if the app was started after a full termination (true cold start)
        SharedPreferences prefs = getSharedPreferences("app_state", MODE_PRIVATE);
        boolean wasRunning = prefs.getBoolean("was_running", false);

        if (!wasRunning) {
            Log.d("FirebaseInit", "Cold start detected — clearing FirebaseAuth session");
            try {
                FirebaseAuth.getInstance().signOut();
            } catch (Exception e) {
                Log.e("FirebaseInit", "Error clearing session: " + e.getMessage());
            }
        }

        // Mark app as running
        prefs.edit().putBoolean("was_running", true).apply();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // This runs only on emulator or when process is killed in dev (not always on real Android)
        SharedPreferences prefs = getSharedPreferences("app_state", MODE_PRIVATE);
        prefs.edit().putBoolean("was_running", false).apply();
        Log.d("FirebaseInit", "App terminated — will require login next launch");
    }
}
