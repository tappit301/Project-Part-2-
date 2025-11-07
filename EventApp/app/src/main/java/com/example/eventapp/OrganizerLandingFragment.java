package com.example.eventapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.utils.FirebaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrganizerLandingFragment extends Fragment {

    private static final String TAG = "OrganizerLanding";
    private RecyclerView rvEvents;
    private LinearLayout emptyState;
    private EventAdapter adapter;
    private final List<Event> eventList = new ArrayList<>();

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.landing_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseHelper.getAuth();
        firestore = FirebaseHelper.getFirestore();

        // Navigation
        NavController navController = Navigation.findNavController(view);
        View.OnClickListener createClick =
                v -> navController.navigate(R.id.action_organizerLandingFragment_to_createEventFragment);

        MaterialButton btnAddEventEmpty = view.findViewById(R.id.btnAddEventEmpty);
        FloatingActionButton btnAddEvent = view.findViewById(R.id.btnAddEvent);
        MaterialButton btnCreateEventTop = view.findViewById(R.id.btnCreateEventTop);

        if (btnAddEventEmpty != null) btnAddEventEmpty.setOnClickListener(createClick);
        if (btnAddEvent != null) btnAddEvent.setOnClickListener(createClick);
        if (btnCreateEventTop != null) btnCreateEventTop.setOnClickListener(createClick);

        // Recycler setup
        rvEvents = view.findViewById(R.id.rvEvents);
        emptyState = view.findViewById(R.id.emptyStateLayout);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventAdapter(eventList);
        rvEvents.setAdapter(adapter);

        loadOrganizerEvents();
    }

    private void loadOrganizerEvents() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getContext(), "Please sign in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String organizerId = user.getUid();

        firestore.collection("events")
                .whereEqualTo("organizerId", organizerId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener(( snapshots, error) -> {
                    if (error != null) {
                        Log.e(TAG, "Firestore listen failed", error);
                        Toast.makeText(getContext(), "Failed to load events: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (snapshots == null || snapshots.isEmpty()) {
                        eventList.clear();
                        adapter.notifyDataSetChanged();
                        emptyState.setVisibility(View.VISIBLE);
                        rvEvents.setVisibility(View.GONE);
                        return;
                    }

                    //Build the full event list directly from snapshot
                    eventList.clear();
                    snapshots.getDocuments().forEach(doc -> {
                        Event event = doc.toObject(Event.class);
                        if (event != null) {
                            event.setId(doc.getId()); //important: store Firestore document ID
                            eventList.add(event);
                        }
                    });

                    emptyState.setVisibility(View.GONE);
                    rvEvents.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                });
    }
}
