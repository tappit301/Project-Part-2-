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

import java.util.HashMap;
import java.util.Map;

/**
 * This is THE Fragment that displays the details of a selected event and
 * provides options to join the waiting list or view the event’s QR code.
 *
 * This screen retrieves event information passed through a bundle,
 * populates the UI, and lets users interact with Firestore by joining
 * a waiting list for the event.</p>
 *
 * @author tappit
 */
public class EventDetailsFragment extends Fragment {

    /** Tag for logging. */
    private static final String TAG = "EventDetailsFragment";

    /** Button for joining the waiting list. */
    private MaterialButton joinBtn;

    /** Button for viewing the event’s QR code. */
    private MaterialButton btnViewQr;

    /** The Firestore document ID of the selected event. */
    private String eventId;

    /** Firestore database instance. */
    private FirebaseFirestore firestore;

    /** The currently logged-in Firebase user. */
    private FirebaseUser currentUser;

    /**
     * Inflates the event details layout.
     *
     * @param inflater  LayoutInflater used to inflate the view
     * @param container Parent container that holds the fragment
     * @param savedInstanceState Previously saved state, if any
     * @return The inflated view for this fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_details, container, false);
    }

    /**
     * Sets up the event information and initializes button actions
     * for joining the waiting list and viewing the QR code.
     *
     * @param view The fragment view
     * @param savedInstanceState Saved instance state, if available
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestore = FirebaseHelper.getFirestore();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        joinBtn = view.findViewById(R.id.btnJoinWaitingList);
        btnViewQr = view.findViewById(R.id.btnViewQr);

        Bundle args = getArguments();
        String title = args != null ? args.getString("title", "") : "";
        String date = args != null ? args.getString("date", "") : "";
        String time = args != null ? args.getString("time", "") : "";
        String location = args != null ? args.getString("location", "") : "";
        String desc = args != null ? args.getString("desc", "") : "";

        ((TextView) view.findViewById(R.id.tvEventTitle)).setText(title);
        ((TextView) view.findViewById(R.id.tvEventDate)).setText(date);
        ((TextView) view.findViewById(R.id.tvEventTime)).setText(time);
        ((TextView) view.findViewById(R.id.tvEventLocation)).setText(location);
        ((TextView) view.findViewById(R.id.tvEventDescription)).setText(desc);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        joinBtn.setOnClickListener(v -> addUserToWaitingList(v));

        btnViewQr.setOnClickListener(v -> {
            String qrPayload = "Event: " + title +
                    "\nDate: " + date +
                    "\nTime: " + time +
                    "\nLocation: " + location;

            Bundle bundle = new Bundle();
            bundle.putString("qrData", qrPayload);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_eventDetailsFragment_to_qrCodeFragment, bundle);
        });
    }

    /**
     * Adds the current user to the event’s waiting list in Firestore.
     * A confirmation message appears after a successful join.
     *
     * @param v The view that triggered this action
     */
    private void addUserToWaitingList(View v) {
        if (currentUser == null || eventId == null) {
            return;
        }

        DocumentReference attendeeRef = firestore.collection("eventAttendees")
                .document(eventId)
                .collection("attendees")
                .document(currentUser.getUid());

        Map<String, Object> data = new HashMap<>();
        data.put("userId", currentUser.getUid());
        data.put("email", currentUser.getEmail());
        data.put("joinedAt", Timestamp.now());

        attendeeRef.set(data)
                .addOnSuccessListener(aVoid -> {
                    joinBtn.setEnabled(false);
                    joinBtn.setText("Added ✅");
                    Snackbar.make(v, "Joined waiting list!", Snackbar.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error joining list", e));
    }
}
