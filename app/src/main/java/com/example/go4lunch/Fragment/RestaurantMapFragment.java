package com.example.go4lunch.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.RestaurantMapViewModel;
import com.example.go4lunch.Viewmodel.HomeFragmentsViewModel;

public class RestaurantMapFragment extends Fragment {

    private RestaurantMapViewModel restaurantMapViewModel;
    HomeFragmentsViewModel homeFragmentsViewModel;
    Button button;
    TextView textView;

    public static RestaurantMapFragment newInstance() {
        return new RestaurantMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_restaurant_map, container, false);

        textView = result.findViewById(R.id.textMap);
        button = result.findViewById(R.id.buttonEssaiRestoMap);

        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantMapViewModel = new ViewModelProvider(this).get(RestaurantMapViewModel.class);
        homeFragmentsViewModel = new ViewModelProvider(getActivity()).get(HomeFragmentsViewModel.class);

        configureButton();
        observeView();
    }

    private void observeView() {
        homeFragmentsViewModel.getTextLiveData().observe(getViewLifecycleOwner(), text ->
                textView.setText(text)
        );
    }

    private void configureButton() {
        button.setOnClickListener(view ->
                homeFragmentsViewModel.updateTextRestaurant()
        );
    }
}