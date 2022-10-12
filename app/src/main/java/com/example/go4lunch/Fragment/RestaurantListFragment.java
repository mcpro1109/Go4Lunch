package com.example.go4lunch.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.R;
import com.example.go4lunch.RestaurantProfilActivity;
import com.example.go4lunch.Viewmodel.RestaurantListViewModel;
import com.example.go4lunch.Viewmodel.HomeFragmentsViewModel;
import com.example.go4lunch.adapter.RestaurantListFragmentRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantListFragment extends Fragment {

    private RestaurantListViewModel restaurantListViewModel;
    private RecyclerView recyclerView;
    HomeFragmentsViewModel homeFragmentsViewModel;

    ArrayList<Restaurant> restaurants=new ArrayList<>();

    RestaurantListFragmentRecyclerViewAdapter adapter=new RestaurantListFragmentRecyclerViewAdapter(restaurants,
            (v, position) -> RestaurantProfilActivity.navigate(getActivity(), restaurants.get(position)));

    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        recyclerView=result.findViewById(R.id.restaurantRecyclerView);

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
        homeFragmentsViewModel = new ViewModelProvider(getActivity()).get(HomeFragmentsViewModel.class);


        observeText();
       // homeFragmentsViewModel.loadRestaurantsList();

            homeFragmentsViewModel.start();

       /* recyclerView.setAdapter(new RestaurantListFragmentRecyclerViewAdapter(
                restaurants, new RestaurantListFragmentRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                RestaurantProfilActivity.navigate(getActivity(),restaurants.get(position));

            }

        }));*/
    }

    @SuppressLint("NotifyDataSetChanged")
    private void observeText() {
               homeFragmentsViewModel.getRestaurantData().observe(getViewLifecycleOwner(), restaurants ->{
            adapter.update(restaurants);

    });

    }

}