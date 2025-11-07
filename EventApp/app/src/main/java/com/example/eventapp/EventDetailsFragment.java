package com.example.eventapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.eventapp.utils.FirebaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EventDetailsFragment extends Fragment {

    private static final String TAG = "EventDetailsFragment";
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestore = FirebaseHelper.getFirestore();
        auth = FirebaseHelper.getAuth();

        // Extract event data
        Bundle args = getArguments();
        String eventId = null;
        if (args != null) {
            ((TextView) view.findViewById(R.id.tvEventTitle))
                    .setText(args.getString("title", "Untitled Event"));
            ((TextView) view.findViewById(R.id.tvEventDate))
                    .setText(args.getString("date", ""));
            ((TextView) view.findViewById(R.id.tvEventTime))
                    .setText(args.getString("time", ""));
            ((TextView) view.findViewById(R.id.tvEventLocation))
                    .setText(args.getString("location", ""));
            ((TextView) view.findViewById(R.id.tvEventDescription))
                    .setText(args.getString("desc", ""));
            eventId = args.getString("eventId", null);
        }

        String finalEventId = eventId; // effectively final for inner class

        // Back button
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        }

        // Join Waiting List button
        MaterialButton joinBtn = view.findViewById(R.id.btnJoinWaitingList);
        if (joinBtn != null) {
            joinBtn.setOnClickListener(v -> {
                FirebaseUser user = auth.getCurrentUser();
                if (user == null) {
                    Snackbar.make(v, "Please log in first.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (finalEventId == null) {
                    Snackbar.make(v, "Event not found.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Build attendee data
                Map<String, Object> attendee = new HashMap<>();
                attendee.put("userId", user.getUid());
                attendee.put("email", user.getEmail());
                attendee.put("name", user.getDisplayName() != null ? user.getDisplayName() : "Unknown User");
                attendee.put("joinedAt", Timestamp.now());

                // Path: eventAttendees/{eventId}/attendees/{userId}
                firestore.collection("eventAttendees")
                        .document(finalEventId)
                        .collection("attendees")
                        .document(user.getUid())
                        .set(attendee)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "User added to waiting list for " + finalEventId);
                            joinBtn.setEnabled(false);
                            joinBtn.setText("Added to Waiting List ✅");
                            Snackbar.make(v, "You’ve joined the waiting list!", Snackbar.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Failed to join waiting list", e);
                            Toast.makeText(getContext(), "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            });
        }
    }
}
