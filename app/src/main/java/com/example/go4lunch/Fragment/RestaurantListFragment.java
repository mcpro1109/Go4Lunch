package com.example.go4lunch.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.RestaurantListViewModel;
import com.example.go4lunch.Viewmodel.HomeFragmentsViewModel;

public class RestaurantListFragment extends Fragment {

    private RestaurantListViewModel restaurantListViewModel;
    private RecyclerView recyclerView;
    HomeFragmentsViewModel homeFragmentsViewModel;
    TextView textView;
    Button button;
    RestaurantMapFragment restaurantMapFragment;

    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        textView = result.findViewById(R.id.textList);
        button = result.findViewById(R.id.buttonEssaiRestoList);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeFragmentsViewModel = new ViewModelProvider(getActivity()).get(HomeFragmentsViewModel.class);

        configureButton();
        observeText();
    }

    private void observeText() {
        homeFragmentsViewModel.getTextLiveData().observe(getViewLifecycleOwner(), s -> textView.setText(s));
    }

    private void configureButton() {
        button.setOnClickListener(view ->
                homeFragmentsViewModel.updateTextRestaurant()
        );
    }
}