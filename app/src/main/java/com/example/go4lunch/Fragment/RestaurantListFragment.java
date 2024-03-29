package com.example.go4lunch.Fragment;

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

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.R;
import com.example.go4lunch.RestaurantProfileActivity;
import com.example.go4lunch.Viewmodel.HomeActivityViewModel;
import com.example.go4lunch.Viewmodel.RestaurantProfileActivityViewModel;
import com.example.go4lunch.adapter.RestaurantListFragmentRecyclerViewAdapter;

import java.util.ArrayList;

public class RestaurantListFragment extends Fragment{

    private RecyclerView recyclerView;
    HomeActivityViewModel homeActivityViewModel;
    RestaurantProfileActivityViewModel restaurantProfileActivityViewModel;

    ArrayList<Restaurant> restaurants = new ArrayList<>();

    RestaurantListFragmentRecyclerViewAdapter adapter = new RestaurantListFragmentRecyclerViewAdapter(restaurants,
            restaurant -> RestaurantProfileActivity.navigate(getActivity(), restaurant));

    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        recyclerView = result.findViewById(R.id.restaurantRecyclerView);

        setupRecyclerView();
        return result;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeActivityViewModel = new ViewModelProvider(getActivity()).get(HomeActivityViewModel.class);

        restaurantProfileActivityViewModel = new ViewModelProvider(getActivity()).get(RestaurantProfileActivityViewModel.class);

        observeList();

        homeActivityViewModel.start();
    }

    private void observeList() {
        homeActivityViewModel.getRestaurantData().observe(getViewLifecycleOwner(), restaurants -> {
            adapter.update(restaurants);
        });
        restaurantProfileActivityViewModel.getRestaurantData().observe(getViewLifecycleOwner(), restaurantDetails -> {
            restaurantProfileActivityViewModel.getRating();
        });
    }
}