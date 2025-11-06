package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void teardown() {
        Intents.release();
    }

    @Test
    public void testEmptyFieldsShowsToast() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            // Click Sign In without filling fields
            onView(withId(R.id.btnSignIn)).perform(click());

            // TODO: To verify Toast, use ToastMatcher if needed
            // Not built-in in Espresso
        }
    }

    @Test
    public void testInvalidCredentialsShowsToast() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            onView(withId(R.id.editTextEmail)).perform(replaceText("wrong@gmail.com"));
            onView(withId(R.id.editTextPassword)).perform(replaceText("wrongpass"));
            onView(withId(R.id.btnSignIn)).perform(click());

            // TODO: To verify Toast
        }
    }

    @Test
    public void testValidLoginStartsHomeActivity() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            onView(withId(R.id.editTextEmail)).perform(replaceText("test@gmail.com"));
            onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
            onView(withId(R.id.btnSignIn)).perform(click());

            Intents.intended(IntentMatchers.hasComponent(HomeActivity.class.getName()));
        }
    }

    @Test
    public void testSignUpButtonStartsSignUpActivity() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            onView(withId(R.id.btnSignUpToggle)).perform(click());
            Intents.intended(IntentMatchers.hasComponent(SignUpActivity.class.getName()));
        }
    }
}
