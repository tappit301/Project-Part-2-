package com.example.eventapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.eventapp.utils.FirebaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Displays detailed information about a selected event.
 * Allows users to join the waiting list and view a generated QR code.
 *
 * Retrieves event data passed through a Bundle, checks if the user
 * has already joined, and updates Firestore accordingly.
 */
public class EventDetailsFragment extends Fragment {

    /** Log tag for this class. */
    private static final String TAG = "EventDetailsFragment";

    /** Button that allows the user to join the waiting list. */
    private MaterialButton joinBtn;

    /** Button that navigates to the event QR code view. */
    private MaterialButton btnViewQr;

    /** The Firestore document ID for the current event. */
    private String eventId;

    /** Firestore database reference. */
    private FirebaseFirestore firestore;

    /** Currently authenticated Firebase user. */
    private FirebaseUser currentUser;

    /**
     * Inflates the event details layout for this fragment.
     *
     * @param inflater LayoutInflater used to inflate the view
     * @param container Parent view group
     * @param savedInstanceState Saved state if available
     * @return The inflated event details layout
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_details, container, false);
    }

    /**
     * Initializes the fragment, populates UI with event data,
     * and sets up listeners for joining the waiting list and viewing QR codes.
     *
     * @param view The root view for this fragment
     * @param savedInstanceState Saved instance state, if any
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestore = FirebaseHelper.getFirestore();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        joinBtn = view.findViewById(R.id.btnJoinWaitingList);
        btnViewQr = view.findViewById(R.id.btnViewQr);

        Bundle args = getArguments();
        if (args != null) {
            eventId = args.getString("eventId");

            ((TextView) view.findViewById(R.id.tvEventTitle))
                    .setText(args.getString("title", ""));
            ((TextView) view.findViewById(R.id.tvEventDate))
                    .setText(args.getString("date", ""));
            ((TextView) view.findViewById(R.id.tvEventTime))
                    .setText(args.getString("time", ""));
            ((TextView) view.findViewById(R.id.tvEventLocation))
                    .setText(args.getString("location", ""));
            ((TextView) view.findViewById(R.id.tvEventDescription))
                    .setText(args.getString("desc", ""));
        }

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v ->
                NavHostFragment.findNavController(this).popBackStack());

        checkIfAlreadyJoined();

        joinBtn.setOnClickListener(this::addUserToWaitingList);

        btnViewQr.setOnClickListener(v -> {
            String qrPayload = "Event: " +
                    ((TextView) view.findViewById(R.id.tvEventTitle)).getText() +
                    "\nDate: " +
                    ((TextView) view.findViewById(R.id.tvEventDate)).getText() +
                    "\nTime: " +
                    ((TextView) view.findViewById(R.id.tvEventTime)).getText() +
                    "\nLocation: " +
                    ((TextView) view.findViewById(R.id.tvEventLocation)).getText();

            Bundle bundle = new Bundle();
            bundle.putString("qrData", qrPayload);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_eventDetailsFragment_to_qrCodeFragment, bundle);
        });
    }

    /**
     * Adds the current user to the waiting list for this event in Firestore.
     * Updates UI after a successful join.
     *
     * @param v The view that triggered this action
     */
    private void addUserToWaitingList(View v) {
        if (currentUser == null) {
            Snackbar.make(v, "Please log in first.", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (eventId == null || eventId.isEmpty()) {
            Log.e(TAG, "Missing eventId — cannot join waiting list");
            Snackbar.make(v, "Error: Event not found.", Snackbar.LENGTH_LONG).show();
            return;
        }

        DocumentReference attendeeRef = firestore.collection("eventAttendees")
                .document(eventId)
                .collection("attendees")
                .document(currentUser.getUid());

        attendeeRef.set(new AttendeeData(currentUser))
                .addOnSuccessListener(aVoid -> {
                    joinBtn.setEnabled(false);
                    joinBtn.setText("Added ✅");
                    Snackbar.make(v, "Joined waiting list.", Snackbar.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Error joining waiting list", e));
    }

    /**
     * Checks if the current user has already joined this event’s waiting list.
     * If so, disables the join button and updates the text.
     */
    private void checkIfAlreadyJoined() {
        if (currentUser == null || eventId == null) return;

        firestore.collection("eventAttendees")
                .document(eventId)
                .collection("attendees")
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        joinBtn.setEnabled(false);
                        joinBtn.setText("Added ✅");
                    }
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Error checking existing waiting list entry", e));
    }

    /**
     * Inner class representing attendee data to be uploaded to Firestore.
     */
    private static class AttendeeData {
        private final String userId;
        private final String email;
        private final Timestamp joinedAt;

        public AttendeeData(FirebaseUser user) {
            this.userId = user.getUid();
            this.email = user.getEmail();
            this.joinedAt = Timestamp.now();
        }

        public String getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public Timestamp getJoinedAt() {
            return joinedAt;
        }
    }
}
