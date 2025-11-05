package com.example.eventapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class EventDetailsFragment extends Fragment {

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

        // Extract event data
        Bundle args = getArguments();
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

            // ✅ Category (sports, tech, etc.)
            TextView chipCategory = view.findViewById(R.id.chipCategory);
            String category = args.getString("category", "");
            if (!category.isEmpty()) {
                chipCategory.setText(category);
                chipCategory.setVisibility(View.VISIBLE);
            } else {
                chipCategory.setVisibility(View.GONE);
            }

            // ✅ Capacity (total people only)
            TextView tvCapacityStatus = view.findViewById(R.id.tvCapacityStatus);
            View layoutCapacity = view.findViewById(R.id.layoutCapacity);

            int capacity = args.getInt("capacity", 0);
            if (capacity > 0) {
                layoutCapacity.setVisibility(View.VISIBLE);
                tvCapacityStatus.setText("Max capacity: " + capacity + " people");
            } else {
                layoutCapacity.setVisibility(View.GONE);
            }
        }

        // Back button click → go back
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        }

        // Join Waiting List button
        MaterialButton joinBtn = view.findViewById(R.id.btnJoinWaitingList);
        if (joinBtn != null) {
            joinBtn.setOnClickListener(v -> {
                joinBtn.setEnabled(false);
                joinBtn.setText("Added to Waiting List ✅");
                Snackbar.make(v, "You’ve joined the waiting list!", Snackbar.LENGTH_SHORT).show();
            });
        }
    }
}

