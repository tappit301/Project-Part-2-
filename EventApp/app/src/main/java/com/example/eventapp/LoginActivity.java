package com.example.eventapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button signUpToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI
        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        Button signInButton = findViewById(R.id.btnSignIn);
        signUpToggleButton = findViewById(R.id.btnSignUpToggle);

        // Mock Sign In
        signInButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (email.equals("test@gmail.com") && password.equals("123456")) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign Up button (placeholder action)
        signUpToggleButton.setOnClickListener(v ->
                Toast.makeText(this, "Sign Up screen coming soon", Toast.LENGTH_SHORT).show());
    }
}

