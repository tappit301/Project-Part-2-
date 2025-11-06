package com.example.eventapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

@RunWith(RobolectricTestRunner.class)
public class HomeActivityTest {

    private HomeActivity activity;
    private ShadowActivity shadowActivity;

    @Before
    public void setUp() {
        ActivityController<HomeActivity> controller = Robolectric.buildActivity(HomeActivity.class)
                .create()
                .start()
                .resume()
                .visible();
        activity = controller.get();
        shadowActivity = org.robolectric.Shadows.shadowOf(activity);
    }

    // ===========================
    // Normal Behavior Tests
    // ===========================

    @Test
    public void testToolbarTitle() {
        Toolbar toolbar = activity.findViewById(R.id.topAppBar);
        assertNotNull(toolbar);
        assertEquals("Tappit", toolbar.getTitle().toString());
    }

    @Test
    public void testGettingStartedButton_launchesSignInActivity() {
        Button btnGettingStarted = activity.findViewById(R.id.btnGettingStarted);
        btnGettingStarted.performClick();

        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertNotNull(startedIntent);
        assertEquals(SignInActivity.class.getCanonicalName(),
                startedIntent.getComponent().getClassName());
    }

    @Test
    public void testCreateAccountButton_launchesSignUpActivity() {
        Button btnCreateAccount = activity.findViewById(R.id.btnCreateAccount);
        btnCreateAccount.performClick();

        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertNotNull(startedIntent);
        assertEquals(SignUpActivity.class.getCanonicalName(),
                startedIntent.getComponent().getClassName());
    }

    @Test
    public void testViewEventsButton_showsToast() {
        Button btnViewEvents = activity.findViewById(R.id.button_view_events);
        btnViewEvents.performClick();

        String toastText = ShadowToast.getTextOfLatestToast();
        assertEquals("Guest view coming soon!", toastText);
    }

    @Test
    public void testOptionsMenu_profileClick_showsToast() {
        Menu menu = shadowActivity.getOptionsMenu();
        MenuItem profileItem = menu.findItem(R.id.action_profile);
        activity.onOptionsItemSelected(profileItem);

        String toastText = ShadowToast.getTextOfLatestToast();
        assertEquals("Profile clicked", toastText);
    }

    @Test
    public void testOptionsMenu_signInClick_launchesSignInActivity() {
        Menu menu = shadowActivity.getOptionsMenu();
        MenuItem signInItem = menu.findItem(R.id.action_sign_in);
        activity.onOptionsItemSelected(signInItem);

        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertNotNull(startedIntent);
        assertEquals(SignInActivity.class.getCanonicalName(),
                startedIntent.getComponent().getClassName());
    }

    // ===========================
    // Edge Case Tests
    // ===========================

    @Test
    public void testNullButtons_doNotCrash() {
        activity.setContentView(new Button(activity).getRootView()); // Only a dummy view

        try {
            Button btn = activity.findViewById(R.id.btnGettingStarted);
            if (btn != null) btn.performClick();
        } catch (Exception e) {
            throw new AssertionError("Clicking null button crashed", e);
        }
    }

    @Test
    public void testNullMenuItems_doNotCrash() {
        Menu emptyMenu = shadowActivity.getOptionsMenu();
        try {
            MenuItem item = emptyMenu.findItem(R.id.action_profile);
            if (item != null) activity.onOptionsItemSelected(item);
        } catch (Exception e) {
            throw new AssertionError("Clicking null menu item crashed", e);
        }
    }

    @Test
    public void testMultipleClicks_onButtons_workCorrectly() {
        Button btnViewEvents = activity.findViewById(R.id.button_view_events);
        btnViewEvents.performClick();
        btnViewEvents.performClick();

        String toastText = ShadowToast.getTextOfLatestToast();
        assertEquals("Guest view coming soon!", toastText);
    }

    @Test
    public void testNullToolbar_doesNotCrash() {
        activity.setSupportActionBar(null);

        try {
            activity.onCreateOptionsMenu(null);
        } catch (Exception e) {
            throw new AssertionError("Creating menu with null toolbar crashed", e);
        }
    }

    @Test
    public void testMenuInflationFailure_doesNotCrash() {
        try {
            activity.onCreateOptionsMenu(null);
        } catch (Exception e) {
            throw new AssertionError("Menu inflation failure crashed activity", e);
        }
    }

    @Test
    public void testOptionsMenuItemNotPresent_doesNotCrash() {
        Menu menu = shadowActivity.getOptionsMenu();
        MenuItem missingItem = menu.findItem(-1); // invalid ID
        try {
            activity.onOptionsItemSelected(missingItem);
        } catch (Exception e) {
            throw new AssertionError("Clicking non-existent menu item crashed", e);
        }
    }
}
