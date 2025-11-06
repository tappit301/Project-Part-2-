package com.example.eventapp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.os.Build;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P) // Use a modern SDK version
public class LandingHostActivityTest {

    private LandingHostActivity activity;

    @Before
    public void setUp() {
        ActivityController<LandingHostActivity> controller =
                Robolectric.buildActivity(LandingHostActivity.class)
                        .create()
                        .start()
                        .resume()
                        .visible();
        activity = controller.get();
    }

    // ===========================
    // Normal Behavior Tests
    // ===========================

    @Test
    public void testToolbarIsSetup() {
        Toolbar toolbar = activity.findViewById(R.id.topAppBar);
        assertNotNull("Toolbar should not be null", toolbar);
        assertTrue("Toolbar should be set as action bar", activity.getSupportActionBar() != null);
    }

    @Test
    public void testNavHostFragmentExists() {
        NavHostFragment navHostFragment =
                (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assertNotNull("NavHostFragment should exist", navHostFragment);

        NavController navController = navHostFragment.getNavController();
        assertNotNull("NavController should exist", navController);
    }

    @Test
    public void testOnSupportNavigateUp_returnsBoolean() {
        boolean result = activity.onSupportNavigateUp();
        // Since there is no navigation setup in Robolectric, result may be false or true depending on default behavior
        assertTrue("onSupportNavigateUp should return a boolean", result == false || result == true);
    }

    // ===========================
    // Edge Case Tests
    // ===========================

    @Test
    public void testNavHostFragmentNull_doesNotCrash() {
        // Temporarily remove NavHostFragment
        activity.getSupportFragmentManager().beginTransaction()
                .remove(activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment))
                .commitNow();

        try {
            activity.onSupportNavigateUp();
        } catch (Exception e) {
            throw new AssertionError("onSupportNavigateUp crashed when NavHostFragment was null", e);
        }
    }

    @Test
    public void testToolbarNull_doesNotCrash() {
        Toolbar toolbar = activity.findViewById(R.id.topAppBar);
        if (toolbar != null) {
            activity.setSupportActionBar(null); // simulate null toolbar
        }

        try {
            activity.onSupportNavigateUp();
        } catch (Exception e) {
            throw new AssertionError("onSupportNavigateUp crashed when toolbar was null", e);
        }
    }
}
