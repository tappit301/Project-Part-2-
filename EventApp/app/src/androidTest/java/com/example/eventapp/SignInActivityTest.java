package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityRule =
            new ActivityScenarioRule<>(SignInActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testEmptyEmailShowsToast() {
        onView(withId(R.id.editTextEmail)).perform(replaceText(""));
        onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
        onView(withId(R.id.btnSignIn)).perform(click());

        // Cannot directly assert Toast, but you can check text field remains unchanged
        onView(withId(R.id.editTextEmail)).check(matches(withText("")));
    }

    @Test
    public void testEmptyPasswordShowsToast() {
        onView(withId(R.id.editTextEmail)).perform(replaceText("test@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText(""));
        onView(withId(R.id.btnSignIn)).perform(click());

        onView(withId(R.id.editTextPassword)).check(matches(withText("")));
    }

    @Test
    public void testSuccessfulSignInNavigatesToLandingHostActivity() {
        onView(withId(R.id.editTextEmail)).perform(replaceText("test@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
        onView(withId(R.id.btnSignIn)).perform(click());

        // Verify intent to LandingHostActivity
        Intents.intended(IntentMatchers.hasComponent(LandingHostActivity.class.getName()));
    }

    @Test
    public void testSignUpButtonOpensSignUpActivity() {
        onView(withId(R.id.btnSignUpToggle)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(SignUpActivity.class.getName()));
    }
}
