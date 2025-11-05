package com.example.eventapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class OrganizerLandingFragment extends Fragment {

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

        // Initialize Navigation
        NavController navController = Navigation.findNavController(view);

        // Buttons from layout
        MaterialButton btnAddEventEmpty = view.findViewById(R.id.btnAddEventEmpty);
        FloatingActionButton btnAddEvent = view.findViewById(R.id.btnAddEvent);
        MaterialButton btnCreateEventTop = view.findViewById(R.id.btnCreateEventTop);

        // Common click listener for all Create Event buttons
        View.OnClickListener navigateToCreate = v ->
                navController.navigate(R.id.action_organizerLandingFragment_to_createEventFragment);

        if (btnAddEventEmpty != null) btnAddEventEmpty.setOnClickListener(navigateToCreate);
        if (btnAddEvent != null) btnAddEvent.setOnClickListener(navigateToCreate);
        if (btnCreateEventTop != null) btnCreateEventTop.setOnClickListener(navigateToCreate);

        // RecyclerView + Empty State setup
        RecyclerView rvEvents = view.findViewById(R.id.rvEvents);
        LinearLayout emptyState = view.findViewById(R.id.emptyStateLayout);

        // Fetch all events from repository
        List<Event> eventList = EventRepository.getInstance().getEvents();

        if (eventList.isEmpty()) {
            // Show empty state if no events
            emptyState.setVisibility(View.VISIBLE);
            rvEvents.setVisibility(View.GONE);
        } else {
            // Show event list
            emptyState.setVisibility(View.GONE);
            rvEvents.setVisibility(View.VISIBLE);
            rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEvents.setAdapter(new EventAdapter(eventList));
        }
    }
}
