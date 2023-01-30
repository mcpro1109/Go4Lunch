package com.example.go4lunch.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.RestaurantProfileActivityViewModel;
import com.example.go4lunch.Viewmodel.WorkmateViewModel;
import com.example.go4lunch.adapter.WorkmateFragmentRecyclerViewAdapter;

import java.util.ArrayList;

public class WorkmateFragment extends Fragment implements View.OnClickListener {

    private WorkmateViewModel workmateViewModel;

    RecyclerView recyclerView;
    ArrayList<Workmate> workmates = new ArrayList<>();
    ArrayList<EatingWorkmate> eatingWorkmates = new ArrayList<>();
    WorkmateFragmentRecyclerViewAdapter adapter = new WorkmateFragmentRecyclerViewAdapter(workmates, eatingWorkmates);
    RestaurantProfileActivityViewModel restaurantProfileActivityViewModel;

    public static WorkmateFragment newInstance() {
        return new WorkmateFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_workmate, container, false);
        restaurantProfileActivityViewModel = new ViewModelProvider(this).get(RestaurantProfileActivityViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerViewWorkmate);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);

        observeText();

        workmateViewModel.loadWorkmates();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void observeText() {

        workmateViewModel.getWorkmatesData().observe(getViewLifecycleOwner(), workmates -> {
            adapter.update(workmates);
        });
        workmateViewModel.getEatingWorkmatesData().observe(getViewLifecycleOwner(), eatingWorkmates1 -> {
                    workmateViewModel.eatingWorkmate(eatingWorkmates1, workmates);

                });


    }


    @Override
    public void onClick(View view) {
    }
}


