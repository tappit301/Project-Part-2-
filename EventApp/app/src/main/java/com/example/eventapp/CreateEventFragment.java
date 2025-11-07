package com.example.eventapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.eventapp.utils.FirebaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a fragment whichh allows an organizer to create a new event and publish it to Firestore.
 * Once an event is created, it also generates a QR payload with the event info.
 *
 *This screen collets details like title, date, time and location.
 * After submitting, the event data is stored under the "events" collection.
 *
 * @author tappit
 */
public class CreateEventFragment extends Fragment {

    /** Used for logging errors and event information. */
    private static final String TAG = "CreateEventFragment";

    /** Date input field. */
    private EditText etDate;

    /** Time input field. */
    private EditText etTime;

    /** Firebase authentication instance. */
    private FirebaseAuth auth;

    /** Firestore database instance. */
    private FirebaseFirestore firestore;

    /**
     * Inflates the layout for creating a new event.
     *
     * @param inflater The LayoutInflater used to inflate the fragment's view.
     * @param container The container that holds this fragment.
     * @param savedInstanceState Saved instance state, if available.
     * @return The inflated view for this fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event, container, false);
    }

    /**
     * Called when the view is created. Sets up click listeners for date, time,
     * publish, and cancel buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState Previously saved state, if any.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseHelper.getAuth();
        firestore = FirebaseHelper.getFirestore();

        etDate = view.findViewById(R.id.etEventDate);
        etTime = view.findViewById(R.id.etEventTime);
        MaterialButton btnPublish = view.findViewById(R.id.btnPublishEvent);
        MaterialButton btnCancel = view.findViewById(R.id.btnCancel);

        etDate.setOnClickListener(v -> showDatePicker());
        etTime.setOnClickListener(v -> showTimePicker());

        btnPublish.setOnClickListener(v -> publishEvent(view));
        btnCancel.setOnClickListener(v -> Navigation.findNavController(view).popBackStack());
    }

    /**
     * Saves the event to Firestore after validating user input.
     * Also navigates to the QR code screen if the event is published successfully.
     *
     * @param view The current fragment view, used for finding navigation controller.
     */
    private void publishEvent(View view) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getContext(), "Please sign in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = ((EditText) view.findViewById(R.id.etEventTitle)).getText().toString().trim();
        String desc = ((EditText) view.findViewById(R.id.etEventDescription)).getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String location = ((EditText) view.findViewById(R.id.etEventLocation)).getText().toString().trim();

        if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> event = new HashMap<>();
        event.put("title", title);
        event.put("description", desc);
        event.put("date", date);
        event.put("time", time);
        event.put("location", location);
        event.put("createdAt", Timestamp.now());
        event.put("organizerId", user.getUid());
        event.put("organizerEmail", user.getEmail());

        firestore.collection("events")
                .add(event)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(getContext(), "Event published successfully!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Event saved by " + user.getEmail());

                    String qrPayload = "Event: " + title +
                            "\nDate: " + date +
                            "\nTime: " + time +
                            "\nLocation: " + location +
                            "\nOrganizer: " + user.getEmail();

                    Bundle bundle = new Bundle();
                    bundle.putString("qrData", qrPayload);
                    Navigation.findNavController(view).navigate(R.id.qrCodeFragment, bundle);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding event", e);
                    Toast.makeText(getContext(), "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Opens a DatePicker dialog so the user can select a date for the event.
     */
    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) ->
                        etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Opens a TimePicker dialog so the user can select a time for the event.
     */
    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(requireContext(),
                (view, hourOfDay, minute) ->
                        etTime.setText(String.format("%02d:%02d", hourOfDay, minute)),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true).show();
    }
}
