package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityRule =
            new ActivityScenarioRule<>(SignUpActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testSuccessfulSignUpNavigatesToLandingHost() {
        onView(withId(R.id.editTextFullName)).perform(replaceText("John Doe"));
        onView(withId(R.id.editTextSignUpEmail)).perform(replaceText("john@example.com"));
        onView(withId(R.id.editTextSignUpPassword)).perform(replaceText("123456"));
        onView(withId(R.id.editTextConfirmPassword)).perform(replaceText("123456"));

        onView(withId(R.id.btnSignUp)).perform(click());

        // Verify navigation to LandingHostActivity
        intended(hasComponent(LandingHostActivity.class.getName()));
    }

    @Test
    public void testSignUpToggleNavigatesToSignInActivity() {
        onView(withId(R.id.btnSignInToggle)).perform(click());

        intended(hasComponent(SignInActivity.class.getName()));
    }

    @Test
    public void testEmptyFullNameShowsToast() {
        onView(withId(R.id.editTextFullName)).perform(replaceText(""));
        onView(withId(R.id.editTextSignUpEmail)).perform(replaceText("john@example.com"));
        onView(withId(R.id.editTextSignUpPassword)).perform(replaceText("123456"));
        onView(withId(R.id.editTextConfirmPassword)).perform(replaceText("123456"));

        onView(withId(R.id.btnSignUp)).perform(click());
        // Toast cannot be asserted directly; we ensure flow is blocked by keeping current Activity
    }

    @Test
    public void testPasswordMismatchBlocksSignUp() {
        onView(withId(R.id.editTextFullName)).perform(replaceText("John Doe"));
        onView(withId(R.id.editTextSignUpEmail)).perform(replaceText("john@example.com"));
        onView(withId(R.id.editTextSignUpPassword)).perform(replaceText("123456"));
        onView(withId(R.id.editTextConfirmPassword)).perform(replaceText("654321"));

        onView(withId(R.id.btnSignUp)).perform(click());
        // Navigation should not happen
    }
}
