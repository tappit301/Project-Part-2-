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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EventDetailsFragment extends Fragment {

    private static final String TAG = "EventDetailsFragment";
    private MaterialButton joinBtn;
    private String eventId;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;

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
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        joinBtn = view.findViewById(R.id.btnJoinWaitingList);

        // Get eventId passed from adapter
        Bundle args = getArguments();
        if (args != null) {
            eventId = args.getString("eventId");
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
        }

        // Back button
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null)
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        if (eventId == null || currentUser == null) {
            Toast.makeText(getContext(), "Missing event or user info.", Toast.LENGTH_SHORT).show();
            return;
        }

        checkIfUserAlreadyJoined();

        // Join Waiting List button
        joinBtn.setOnClickListener(v -> addUserToWaitingList(v));
    }

    private void checkIfUserAlreadyJoined() {
        DocumentReference attendeeRef = firestore.collection("eventAttendees")
                .document(eventId)
                .collection("attendees")
                .document(currentUser.getUid());

        attendeeRef.get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                // User already joined — update button state
                joinBtn.setEnabled(false);
                joinBtn.setText("Already Joined ✅");
            } else {
                // User not joined yet
                joinBtn.setEnabled(true);
                joinBtn.setText("Join Waiting List");
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Error checking attendee", e));
    }

    private void addUserToWaitingList(View v) {
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
                    joinBtn.setText("Added to Waiting List ✅");
                    Snackbar.make(v, "You’ve joined the waiting list!", Snackbar.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to join waiting list", e);
                    Toast.makeText(getContext(), "Error joining waiting list.", Toast.LENGTH_SHORT).show();
                });
    }
}
