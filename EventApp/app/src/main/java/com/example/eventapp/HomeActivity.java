package com.example.eventapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure the layout name matches your file name exactly
        setContentView(R.layout.home_page);

        // Import the toolbar from XML
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        // Buttons from XML
        Button btnGettingStarted = findViewById(R.id.btnGettingStarted);
        Button btnViewEvents = findViewById(R.id.button_view_events);

        // "Getting Started" goes to LoginActivity
        btnGettingStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // "View Events" is placeholder for now
        btnViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Later navigate to BrowseEventsActivity
            }
        });
    }
}
